package com.jpndev.niravu.home

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jpndev.niravu.*
import com.jpndev.niravu.base.WebActivitykt
import com.jpndev.niravu.interfaces.OnApiResponseListner
import com.jpndev.niravu.prefs.PrefManager
import com.jpndev.niravu.prefs.stringLiveData
import com.jpndev.niravu.quiz.QuizActivity
import com.jpndev.niravu.utility.ComingSoonDialog
import com.jpndev.niravu.utility.Constants
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.json_req_dimv
import kotlinx.android.synthetic.main.activity_home.top_clay
import kotlinx.android.synthetic.main.activity_modes.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.rcv_item_banner_1.view.*


import xyz.sangcomz.chameleon.Chameleon

/*
import xyz.sangcomz.chameleon.Chameleon
import xyz.sangcomz.chameleon.ext.setError
import xyz.sangcomz.chameleon.model.TextSettingBundle
*/


class HomeActivity : NiravuActivity() {

    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }
    private lateinit var home_url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        LogUtils.LOGD("quiz", "HomeActivity onCreate  ")
        home_url=intent.getStringExtra(Intent.EXTRA_TEXT)
        initAppViewModel()


        setValues()
        setClicks()
    }

    private fun setClicks() {
        swipe_lay.setColorSchemeResources(R.color.colorPrimary, R.color.ccrbBrownDark, R.color.colorPrimaryDark, R.color.ccrbGreen)
        swipe_lay.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            swipe_lay.isRefreshing = true
            refreshScreen(home_url )
            refreshUpdateData( )


        })
        modes_dimv.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@HomeActivity, ModesActivity::class.java)
           // intent.putExtra(Intent.EXTRA_TEXT, item)
            startActivity(intent)

        })
        terms_dimv.setOnClickListener(View.OnClickListener {
            ComingSoonDialog.showDialog(this@HomeActivity,
                MBaseDialog(Constants.COMING_SOON)
            )

        })
        app_info_dimv.setOnClickListener(View.OnClickListener {
        ComingSoonDialog.showDialog(this@HomeActivity,
               MBaseDialog(Constants.COMING_SOON)
            )

        })

        logout_dimv.setOnClickListener(View.OnClickListener {
            setLoading()
            showProgress()
            Handler().postDelayed({
                appViewModel?.appRepository.prefManager.putSharedString("auth_code",null)
                appViewModel?.appRepository.prefManager.putSharedString("access_token",null)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finishAffinity()
                   hideProgress()

            }, 1200)

        })
    }

    override fun onBackPressed() {
      ///  super.onBackPressed()
        onBackFinishAffinity()
    }

    override fun onDismiss(obj: Any?) {

    }
    private fun setValues() {
        val layoutManager = LinearLayoutManager(this@HomeActivity)
        rcv.setLayoutManager(layoutManager)

        val mDivider: Drawable? = ContextCompat.getDrawable(this@HomeActivity, R.drawable.divider_1)
        mDivider?.let{
            val hItemDecoration = DividerItemDecoration(
                this@HomeActivity,
                DividerItemDecoration.VERTICAL
            )

            hItemDecoration.setDrawable(it)
            rcv.addItemDecoration(hItemDecoration)
        }


        refreshScreen(home_url )

        appViewModel.appRepository.mld_HomeData.observe(this, Observer { items ->
            items?.let {

                chameleon.showState(Chameleon.STATE.CONTENT)
                onSetAdapter(items)
            } ?: let {
                chameleon.showState(Chameleon.STATE.EMPTY)
            }
        })

        appViewModel.appRepository.mld_BannerData.observe(this, Observer { items ->
            items?.let {
                banner_slideview2.visibility=View.VISIBLE
                setAdsDatas(banner_slideview2,items,this@HomeActivity)
            } ?: let {
               // chameleon.showState(Chameleon.STATE.EMPTY)
                banner_slideview2.visibility=View.GONE
            }
        })

        setBackGrounds( appViewModel.appRepository.prefManager.getAPPObject())

        appViewModel.appRepository.s_pref.stringLiveData("app_object", "").observe(this,Observer { json ->

            LogUtils.LOGD("niravu", "HomeActivity   app_object json=  "+json)
            if(isValid(json)){
                var m_app: MApp? =getAppObject(json)

                setBackGrounds(getAppObject(json))

            }

        })


    }



    fun refreshScreen(home_url: String ) {

        appViewModel.callHomeScreenData(home_url, object: OnApiResponseListner {
            override fun onApiInit(obj: Any?) {
                swipe_lay.isRefreshing = true
                setLoading()
                showProgress()
            }

            override fun onApiPaginateSucess(obj: Any) {
            }

            override fun onApiSucess(obj: Any) {
                if(obj is MHomeScreenRoot)
                {
                    if(obj.in_app)
                    {
                       // checkUpdate(obj)


                    }
                    else
                    {
                        obj.web_url?.let {
                            WebActivitykt.startWebActivity2(this@HomeActivity,obj.web_url,obj?.web_properties)

                        }?:let {
                            obj.browser_url?.let {

                                openBrowser( it)

                            }?:let {
                                ComingSoonDialog.showDialog(this@HomeActivity, MBaseDialog( Constants.COMING_SOON))

                            }

                        }
                    }

                }

            }

            override fun onApiFailure(obj: Any) {

                obj?.let {


                    if (obj is ApiFailure2) {

                        val apifailure: ApiFailure2 = obj as ApiFailure2

                        if (apifailure.isSessionExpired) {
                            //redirectLogin(this@SendCryptoSummaryActivity)
                        } else {
                            showErrorAlertDelay(message = apifailure.message, message2 = apifailure.message2)
                        }


                    } }

            }

            override fun onApiResponse(obj: Any?) {
                swipe_lay.isRefreshing = false
                hideProgress()
                showRequest(json_req_dimv,obj)
            }

            override fun onApiView(obj: Any?) {
            }
        },dummy_url = "https://api.beeone.co.uk/mock-tests/error-2",is_dummy = false)

    }


    var adapter : SummaryAdapter? =null
    fun onSetAdapter(items: List<MHomeData>?=null) {
        adapter = SummaryAdapter(items, this@HomeActivity,appViewModel, { mgame : MHomeData?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        rcv.setAdapter(adapter)
    }



    private fun partItemClicked(item:  MHomeData?, postion:Int, purpose:Int=0) {


        item?.let {
            LogUtils.LOGD("niravu", "HomeActivity partItemClicked  manual  "+it.is_manual_json+" in_app "+it.in_app)
            if(it.in_app)
            {


            when(it.type)

            {
                1,2,3,4->{


                        val intent = Intent(this@HomeActivity, Detail1Activity::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                }
                5->{


                    val intent = Intent(this@HomeActivity, Detail5Activity::class.java)
                    intent.putExtra(Intent.EXTRA_TEXT, item)
                    startActivity(intent)



                }
                200->{


                    val intent = Intent(this@HomeActivity, QuizActivity::class.java)
                    intent.putExtra(Intent.EXTRA_TEXT, item.detail_url)
                    intent.putExtra(Intent.EXTRA_REFERRER, item.time)
                    startActivity(intent)



                }
               40->{
                   if(it.is_manual_json) {

                       val intent = Intent(this@HomeActivity, List1Activity::class.java)
                       //  intent.putExtra(Intent.EXTRA_TEXT, item)
                       //    intent.putExtra(Intent.EXTRA_COMPONENT_NAME, item.span_count)
                       intent.putExtra(Intent.EXTRA_TEXT, item.detail_url)
                       startActivity(intent)
                   }
                   else{
                            val intent = android.content.Intent(
                               this@HomeActivity, com.jpndev.niravu.home.List1Activity2::class.java)
                            //  intent.putExtra(Intent.EXTRA_TEXT, item)
                            //    intent.putExtra(Intent.EXTRA_COMPONENT_NAME, item.span_count)
                            intent.putExtra(android.content.Intent.EXTRA_TEXT, item.detail_url)
                       startActivity(intent)
                       }



                }
                30,31,32,33,34->{

                    LogUtils.LOGD("games", "GA home_url  "+it.is_manual_json)
                    if(it.is_manual_json) {
                              val intent = Intent(this@HomeActivity, Grid1Activity::class.java)
                              intent.putExtra(Intent.EXTRA_COMPONENT_NAME, item.span_count)
                               intent.putExtra(Intent.EXTRA_TEXT, item.detail_url)
                              startActivity(intent)
                    }
                    else {
                       // val intent = Intent(this@HomeActivity, Grid1Activity2::class.java)
                        val intent = Intent(this@HomeActivity, List1Activity2::class.java)
                        //  intent.putExtra(Intent.EXTRA_TEXT, item)
                        intent.putExtra(Intent.EXTRA_COMPONENT_NAME, item.span_count)
                        intent.putExtra(Intent.EXTRA_TEXT, item.detail_url)
                        startActivity(intent)
                    }


                }
                else->{
                    ComingSoonDialog.showDialog(this, MBaseDialog(title = "Maximum Reached",from = Constants.COMING_SOON))
                }
            }


            }else
                {
                    it.web_url?.let {
                        com.jpndev.niravu.base.WebActivitykt.startWebActivity2(this@HomeActivity,it,item?.web_properties)

                    }?:let {
                        item.browser_url?.let {

                            openBrowser( it)

                        }?:let {
                            com.jpndev.niravu.utility.ComingSoonDialog.showDialog(this@HomeActivity,
                                com.jpndev.niravu.MBaseDialog(com.jpndev.niravu.utility.Constants.COMING_SOON)
                            )

                        }

                    }
                }

        }



    }

 /*   private fun partItemClicked(item:  MCardMenu?, postion:Int, purpose:Int=0) {
        LogUtils.LOGD("niravu", "Home MCardMenu partItemClicked  ")
        item?.let {

            if(it.in_app)
            {
                when(it.type)

                {
                    1,2,3,4->{


                        val intent = Intent(this@HomeActivity, Detail1Activity::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                    }
                    5->{


                        val intent = Intent(this@HomeActivity, Detail5Activity::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                    }
                    30,31,32,33,34->{


                        val intent = Intent(this@HomeActivity, Grid1Activity::class.java)
                        //  intent.putExtra(Intent.EXTRA_TEXT, item)
                        intent.putExtra(Intent.EXTRA_TEXT, item.detail_url)
                      startActivity(intent)



                    }
                    else->{
                        ComingSoonDialog.showDialog(this, MBaseDialog(title = "Maximum Reached",from = Constants.COMING_SOON))
                    }
                }

            }else
            {
                it.web_url?.let {
                    com.jpndev.niravu.base.WebActivitykt.startWebActivity2(this@HomeActivity,item.web_url)

                }?:let {
                    item.browser_url?.let {

                        openBrowser( it)

                    }?:let {
                        com.jpndev.niravu.utility.ComingSoonDialog.showDialog(this@HomeActivity,
                            com.jpndev.niravu.MBaseDialog(com.jpndev.niravu.utility.Constants.COMING_SOON)
                        )

                    }

                }
            }

        }



    }*/



    private fun setBackGrounds(   app: MApp?) {

        app?.let {

            when(appViewModel.getMode())
            {
                0->it.system_mode?.let {

                         setBackGrounds(it)
                     }
                1->it.light_mode?.let {
                    setBackGrounds(it)
                }
                2->it.dark_mode?.let {
                    setBackGrounds(it)
                }
                else->it.system_mode?.let {

                    setBackGrounds(it)
                }
            }

        }
    }


    private fun setBackGrounds(   themeMode: ThemeMode?) {

        themeMode?.let {


            it.bg_home_1?.let {

                LogUtils.LOGD("niravu", "HomeActivity app_object bg_splash_1=  "+it)
                Picasso.with(this@HomeActivity).load(Html.fromHtml(it!!).toString()).placeholder(R.drawable.icn_niravu_bg6_2).error(R.drawable.icn_niravu_bg6_2).into(object :
                    Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                        var bitmap2=  appViewModel.resize(bitmap,Resources.getSystem().getDisplayMetrics()
                            .widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels)
                   /*      var bitmap2=  appViewModel.scaleBitmapAndKeepRation(bitmap,Resources.getSystem().getDisplayMetrics()
                            .heightPixels, Resources.getSystem().getDisplayMetrics().widthPixels)*/
               /*        var bitmap2 = Bitmap.createScaledBitmap(bitmap, Resources.getSystem().getDisplayMetrics()
                            .widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels,
                            true);*/


                       var bg:BitmapDrawable= BitmapDrawable(resources,bitmap2)
               bg.setGravity(Gravity.FILL);
                      //  bg.
                        //top_clay.setBi
                        top_clay.setBackground( bg)


                    }

                    override fun onBitmapFailed(errorDrawable: Drawable) {
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {

                    }
                })
                //com.squareup.picasso.Picasso.with(this@HomeActivity).load(android.text.Html.fromHtml(it).toString()).into(top_clay)  ;
            }


            it.icon_developer_1?.let {
                // LogUtils.LOGD("niravu", "HomeActivity app_object icon_developer_1 =  "+it)
                //com.squareup.picasso.Picasso.with(this@HomeActivity).load(android.text.Html.fromHtml(it).toString()).into(developer_dimv)  ;
            }
            it.icon_app_1?.let {
                //LogUtils.LOGD("niravu", "HomeActivity app_object icon_app_1 =  "+it)
                // com.squareup.picasso.Picasso.with(this@HomeActivity).load(android.text.Html.fromHtml(it).toString()).into(niravu_dimv)  ;
            }
        }
    }



    private fun setAdsDatas(img_slideview: SliderView, imagelist :List<MBanner>, context: Context) {
        try {


            val adapter: BannerAdapter = BannerAdapter(context, appViewModel,imagelist,{ maction : MBanner?, postion:Int-> partItemClicked(maction,postion) })
            img_slideview.setSliderAdapter(adapter)
            //  img_slideview.layout_constraintDimensionRatio
            img_slideview.setIndicatorAnimation(IndicatorAnimations.SWAP) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            //img_slideview.setSliderTransformAnimation(SliderAnimations.CUBEOUTDEPTHTRANSFORMATION)
            img_slideview.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            img_slideview.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
            //img_slideview.setIndicatorSelectedColor(context.resources.getColor(R.color.colorPrimaryDark))
          //  img_slideview.setIndicatorUnselectedColor(Color.WHITE)
            img_slideview.setScrollTimeInSec(6) //set scroll delay in seconds :
            img_slideview.setOnIndicatorClickListener(DrawController.ClickListener { position -> img_slideview.setCurrentPagePosition(position) })
            // img_slideview.
            img_slideview.startAutoCycle();
        }catch (e:Exception)
        {
            e.message
        }
    }

    private fun partItemClicked(data: MBanner?, postion:Int) {

    }



    fun refreshUpdateData( ) {

        appViewModel.callAppUpdateData(null, object: OnApiResponseListner {
            override fun onApiInit(obj: Any?) {
                setLoading()
                showProgress()
            }

            override fun onApiPaginateSucess(obj: Any) {
            }

            override fun onApiSucess(obj: Any) {
                if(obj is MUpdateData)
                {
                    obj.app?.let {
                        //  PrefManager.getInstance(this@HomeActivity).saveObject(obj.app,"app_object")
                        //    PrefManager.getInstance(this@HomeActivity).saveAPPObject(it,"app_object")
                        appViewModel.appRepository.prefManager.saveAPPObject(it,"app_object")
                    }//

                    if(obj.in_app)
                    {
                        //checkUpdate(obj)


                    }
                    else
                    {
                        obj.web_url?.let {
                            WebActivitykt.startWebActivity2(this@HomeActivity,obj.web_url,obj?.web_properties)

                        }?:let {
                            obj.browser_url?.let {

                                openBrowser( it)

                            }?:let {
                                ComingSoonDialog.showDialog(this@HomeActivity, MBaseDialog( Constants.COMING_SOON))

                            }

                        }
                    }

                }

            }

            override fun onApiFailure(obj: Any) {

                obj?.let {


                    if (obj is ApiFailure2) {

                        val apifailure: ApiFailure2 = obj as ApiFailure2

                        if (apifailure.isSessionExpired) {
                            //redirectLogin(this@SendCryptoSummaryActivity)
                        } else {
                            showErrorAlertDelay(message = apifailure.message, message2 = apifailure.message2)
                        }


                    } }

            }

            override fun onApiResponse(obj: Any?) {
                hideProgress()
                showRequest(json_req_dimv,obj)
            }

            override fun onApiView(obj: Any?) {
            }
        },dummy_url = "https://api.beeone.co.uk/mock-tests/error-2",is_dummy = false)

    }
    


}
