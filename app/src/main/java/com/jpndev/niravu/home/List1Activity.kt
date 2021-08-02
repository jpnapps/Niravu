package com.jpndev.niravu.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jpndev.niravu.*
import com.jpndev.niravu.base.WebActivitykt
import com.jpndev.niravu.interfaces.OnApiResponseListner

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
import kotlinx.android.synthetic.main.activity_list1.*
import kotlinx.android.synthetic.main.activity_list1.close_dimv
import kotlinx.android.synthetic.main.activity_list1.gradientChart
import kotlinx.android.synthetic.main.activity_quiz_result.*

import xyz.sangcomz.chameleon.Chameleon

class List1Activity  : NiravuActivity() {



    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }
    private lateinit var home_url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list1)
        gradientChart.chartValues = arrayOf(
            10f, 30f, 25f, 32f, 13f, 5f, 18f, 36f, 20f, 30f, 28f, 27f, 29f
        )
        home_url=intent.getStringExtra(Intent.EXTRA_TEXT)
        LogUtils.LOGD("list1", "LA home_url  "+home_url)
        initAppViewModel()


        setValues()
        setClicks()
    }

    private fun setClicks() {
        LogUtils.LOGD("list1", "LA setClicks home_url  "+home_url)
        swipe_lay.setColorSchemeResources(R.color.colorPrimary, R.color.ccrbBrownDark, R.color.colorPrimaryDark, R.color.ccrbGreen)
        swipe_lay.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            swipe_lay.isRefreshing = true
            refreshScreen(home_url )


        })
        close_dimv.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }

    override fun onBackPressed() {
        ///  super.onBackPressed()
        onBackFinish()
        //onBackFinishAffinity()
    }

    override fun onDismiss(obj: Any?) {

    }
    private fun setValues() {
        setRCvManager()

        refreshScreen(home_url )

        appViewModel.appRepository.mld_data.observe(this, Observer { items ->
            items?.let {
                LogUtils.LOGD("list1", home_url+"  mld_data size  = "+items.size)
                chameleon.showState(Chameleon.STATE.CONTENT)
                onSetAdapter(items)
            } ?: let {
                chameleon.showState(Chameleon.STATE.EMPTY)
            }
        })

        appViewModel.appRepository.mld_list_BannerData.observe(this, Observer { items ->
            items?.let {
                banner_slideview.visibility= View.VISIBLE
                setAdsDatas(banner_slideview2,items,this@List1Activity)
            } ?: let {
                // chameleon.showState(Chameleon.STATE.EMPTY)
                banner_slideview .visibility= View.GONE
            }
        })

        setBackGrounds( appViewModel.appRepository.prefManager.getAPPObject())

        appViewModel.appRepository.s_pref.stringLiveData("app_object", "").observe(this,Observer { json ->

            //LogUtils.LOGD("list1", "List1Activity   app_object json=  "+json)
            if(isValid(json)){
                var m_app: MApp? =getAppObject(json)

                setBackGrounds(getAppObject(json))

            }

        })


    }

    private fun setRCvManager() {

        val layoutManager = LinearLayoutManager(this@List1Activity)
        rcv.setLayoutManager(layoutManager)

        val mDivider: Drawable? = ContextCompat.getDrawable(this@List1Activity, R.drawable.divider_1)
        mDivider?.let{
            val hItemDecoration = DividerItemDecoration(
                this@List1Activity,
                DividerItemDecoration.VERTICAL
            )

            hItemDecoration.setDrawable(it)
            rcv.addItemDecoration(hItemDecoration)
        }
        LogUtils.LOGD("list1", "  setRCvManager  ")
    }


    fun refreshScreen(home_url: String ) {

        appViewModel.callData(home_url, object: OnApiResponseListner {
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
                            WebActivitykt.startWebActivity2(this@List1Activity,obj.web_url,obj?.web_properties)

                        }?:let {
                            obj.browser_url?.let {

                                openBrowser( it)

                            }?:let {
                                ComingSoonDialog.showDialog(this@List1Activity, MBaseDialog( Constants.COMING_SOON))

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
       // setRCvManager()
        LogUtils.LOGD("list1", "  onSetAdapter size  = "+items?.size)
        adapter = SummaryAdapter(items, this@List1Activity,appViewModel, { mgame : MHomeData?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        rcv.setAdapter(adapter)
    }



    private fun partItemClicked(item:  MHomeData?, postion:Int, purpose:Int=0) {
        LogUtils.LOGD("list1", "Home partItemClicked  ")
        item?.let {

            if(it.in_app)
            {
                when(it.type)

                {
                    1,2,3,4->{


                        val intent = Intent(this@List1Activity, Detail1Activity::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                    }
                    5->{


                        val intent = Intent(this@List1Activity, Detail5Activity::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                    }
                    40->{


                        val intent = Intent(this@List1Activity, QuizActivity::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item.detail_url)
                        intent.putExtra(Intent.EXTRA_REFERRER, item.time)
                        startActivity(intent)



                    }
                    6->{


                        val intent = Intent(this@List1Activity, Detail1Activity::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                    }
                    else->{
                        ComingSoonDialog.showDialog(this, MBaseDialog(title = "Maximum Reached",from = Constants.COMING_SOON))
                    }
                }

            }else
            {
                it.web_url?.let {
                    com.jpndev.niravu.base.WebActivitykt.startWebActivity2(this@List1Activity,it,item?.web_properties)

                }?:let {
                    item.browser_url?.let {

                        openBrowser( it)

                    }?:let {
                        com.jpndev.niravu.utility.ComingSoonDialog.showDialog(this@List1Activity,
                            com.jpndev.niravu.MBaseDialog(com.jpndev.niravu.utility.Constants.COMING_SOON)
                        )

                    }

                }
            }

        }



    }



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


            it.bg_list_1?.let {

                LogUtils.LOGD("niravu", "List1Activity app_object bg_splash_1=  "+it)
                Picasso.with(this@List1Activity).load(Html.fromHtml(it!!).toString()).placeholder(R.drawable.icn_niravu_bg6_2).error(
                    R.drawable.icn_niravu_bg6_2).into(object :
                    Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {

                        top_clay.setBackground( BitmapDrawable(resources,bitmap))

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable) {
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {

                    }
                })
                //com.squareup.picasso.Picasso.with(this@List1Activity).load(android.text.Html.fromHtml(it).toString()).into(top_clay)  ;
            }


            it.icon_developer_1?.let {
                // LogUtils.LOGD("niravu", "List1Activity app_object icon_developer_1 =  "+it)
                //com.squareup.picasso.Picasso.with(this@List1Activity).load(android.text.Html.fromHtml(it).toString()).into(developer_dimv)  ;
            }
            it.icon_app_1?.let {
                //LogUtils.LOGD("niravu", "List1Activity app_object icon_app_1 =  "+it)
                // com.squareup.picasso.Picasso.with(this@List1Activity).load(android.text.Html.fromHtml(it).toString()).into(niravu_dimv)  ;
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
}

