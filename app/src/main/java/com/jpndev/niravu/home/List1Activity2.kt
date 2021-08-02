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
import kotlinx.android.synthetic.main.activity_grid1.*
import kotlinx.android.synthetic.main.activity_grid1.banner_slideview2
import kotlinx.android.synthetic.main.activity_grid1.chameleon
import kotlinx.android.synthetic.main.activity_grid1.close_dimv
import kotlinx.android.synthetic.main.activity_grid1.json_req_dimv
import kotlinx.android.synthetic.main.activity_grid1.rcv
import kotlinx.android.synthetic.main.activity_grid1.swipe_lay
import kotlinx.android.synthetic.main.activity_grid1.top_clay
import kotlinx.android.synthetic.main.activity_list1.*
import xyz.sangcomz.chameleon.Chameleon

class List1Activity2 : NiravuActivity() {



    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }
    private lateinit var home_url: String
    var span_count: Int =2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list1)
        home_url=intent.getStringExtra(Intent.EXTRA_TEXT)
        span_count=intent.getIntExtra(Intent.EXTRA_COMPONENT_NAME,2)
        LogUtils.LOGD("games", "GA home_url  "+home_url)
        initAppViewModel()


        setValues()
        setClicks()
    }

    private fun setClicks() {
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
        //this@List1Activity2
        // StaggeredGridLayoutManager.VERTICAL)
        setRCvManager()

        refreshScreen(home_url )

        appViewModel.appRepository.mld_item_data.observe(this, Observer { items ->
            items?.let {
                LogUtils.LOGD("prefs", home_url+"  mld_data size  = "+items.size)
                chameleon.showState(Chameleon.STATE.CONTENT)
                onSetAdapter(items)
            } ?: let {
                chameleon.showState(Chameleon.STATE.EMPTY)
            }
        })

        appViewModel.appRepository.mld_list_BannerData.observe(this, Observer { items ->
            items?.let {
                banner_slideview2.visibility= View.VISIBLE
                setAdsDatas(banner_slideview2,items,this@List1Activity2)
            } ?: let {
                // chameleon.showState(Chameleon.STATE.EMPTY)
                banner_slideview2.visibility= View.GONE
            }
        })

        setBackGrounds( appViewModel.appRepository.prefManager.getAPPObject())

        appViewModel.appRepository.s_pref.stringLiveData("app_object", "").observe(this,Observer { json ->

            LogUtils.LOGD("niravu", "List1Activity2   app_object json=  "+json)
            if(isValid(json)){
                var m_app: MApp? =getAppObject(json)

                setBackGrounds(getAppObject(json))

            }

        })


    }

    private fun setRCvManager() {

        val layoutManager = LinearLayoutManager(this@List1Activity2)
        rcv.setLayoutManager(layoutManager)

        val mDivider: Drawable? = ContextCompat.getDrawable(this@List1Activity2, R.drawable.divider_1)
        mDivider?.let{
            val hItemDecoration = DividerItemDecoration(
                this@List1Activity2,
                DividerItemDecoration.VERTICAL
            )

            hItemDecoration.setDrawable(it)
            rcv.addItemDecoration(hItemDecoration)
        }
        LogUtils.LOGD("list1", "  setRCvManager  ")
    }


    fun refreshScreen(home_url: String ) {

        appViewModel.callItemData(home_url, object: OnApiResponseListner {
            override fun onApiInit(obj: Any?) {
                // swipe_lay.isRefreshing = true
                adapter?.clear()
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
                            WebActivitykt.startWebActivity2(this@List1Activity2,obj.web_url,obj?.web_properties)

                        }?:let {
                            obj.browser_url?.let {

                                openBrowser( it)

                            }?:let {
                                ComingSoonDialog.showDialog(this@List1Activity2, MBaseDialog( Constants.COMING_SOON))

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
        },dummy_url = home_url,is_dummy = true)
        //},dummy_url = "http://sarintechy.xyz/backend/web/item?project_id=5",is_dummy = true)

    }


    var adapter : ItemAdapter? =null
    fun onSetAdapter(items: List<MItemData>?=null) {
        setRCvManager()
        LogUtils.LOGD("prefs", "  onSetAdapter size  = "+items?.size)
        //  LogUtils.LOGD("prefs", "  onSetAdapter size  = "+items?.get(2)?.text1)
        adapter = ItemAdapter(items, this@List1Activity2,appViewModel, { mgame : MItemData?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        rcv.setAdapter(adapter)
    }



    private fun partItemClicked(item:  MItemData?, postion:Int, purpose:Int=0) {
        LogUtils.LOGD("list1", "list 2  partItemClicked  ")
        item?.let {

            if(it.custom_field?.in_app?:false)
            {
                when(it.template_id)

                {
                    1,2,3,4->{


                        val intent = Intent(this@List1Activity2, Detail1Activity2::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                    }
                    5->{


                        val intent = Intent(this@List1Activity2, Detail5Activity2::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                    }
                    6->{


                        val intent = Intent(this@List1Activity2, Detail1Activity2::class.java)
                        intent.putExtra(Intent.EXTRA_TEXT, item)
                        startActivity(intent)



                    }
                    40->{


                        val intent = Intent(this@List1Activity2, QuizActivity::class.java)
                        /* intent.putExtra(Intent.EXTRA_TEXT, item.custom_field?.detail_url)
                         intent.putExtra(Intent.EXTRA_REFERRER, item.time)*/
                        startActivity(intent)



                    }
                    else->{
                        ComingSoonDialog.showDialog(this, MBaseDialog(title = "Maximum Reached",from = Constants.COMING_SOON))
                    }
                }

            }else
            {
                it.custom_field?.web_url?.let {
                    com.jpndev.niravu.base.WebActivitykt.startWebActivity2(this@List1Activity2,it,item?.custom_field?.web_properties)

                }?:let {
                    item?.custom_field?.browser_url?.let {

                        openBrowser( it)

                    }?:let {
                        com.jpndev.niravu.utility.ComingSoonDialog.showDialog(this@List1Activity2,
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

            var bg:String?=it.bg_home_2?:it.bg_home_1
            bg?.let {

                LogUtils.LOGD("niravu", "List1Activity2 app_object bg_splash_1=  "+it)
                Picasso.with(this@List1Activity2).load(Html.fromHtml(it!!).toString()).placeholder(R.drawable.icn_niravu_bg6_2).error(
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
                //com.squareup.picasso.Picasso.with(this@List1Activity2).load(android.text.Html.fromHtml(it).toString()).into(top_clay)  ;
            }


            it.icon_developer_1?.let {
                // LogUtils.LOGD("niravu", "List1Activity2 app_object icon_developer_1 =  "+it)
                //com.squareup.picasso.Picasso.with(this@List1Activity2).load(android.text.Html.fromHtml(it).toString()).into(developer_dimv)  ;
            }
            it.icon_app_1?.let {
                //LogUtils.LOGD("niravu", "List1Activity2 app_object icon_app_1 =  "+it)
                // com.squareup.picasso.Picasso.with(this@List1Activity2).load(android.text.Html.fromHtml(it).toString()).into(niravu_dimv)  ;
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
