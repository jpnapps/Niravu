package com.jpndev.niravu.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jpndev.niravu.*
import com.jpndev.niravu.prefs.stringLiveData
import com.jpndev.niravu.utility.ImageZoomDialogFragment
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_detail5.*
import kotlinx.android.synthetic.main.activity_detail5.top_clay
import kotlinx.android.synthetic.main.activity_home.*


import xyz.sangcomz.chameleon.Chameleon

class Detail1Activity: NiravuActivity() {

    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }

    var isAddNewItem :Boolean = false
    var item: MHomeData? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail1)
        initAppViewModel()
        item=intent.getParcelableExtra(Intent.EXTRA_TEXT)
        setClicks()
        setValues()
        item?.let {
            //text2_txv.gravity=Gravity.RIGHT

            var text1:String?= it.detail_text1 ?:let{item?.text1}?:let { "ASsSas" }
            LogUtils.LOGD("niravu", " Detail1 text1 = "+text1)
           // it.detail_text1?.let { defSetHtmlText(text1_txv,it) } ?:let{ (it.text1)?.let { defSetHtmlText(text1_txv,it)}?:let { defSetHtmlText(text1_txv,"") } }


            defSetHtmlText(text1_txv, text1)
            defSetHtmlText(text2_txv, it.detail_text2?.let { it } ?:it.text2?.let { it }?: let { "" })
            defSetHtmlText(text1_head_txv, it.detail_text1?.let { it } ?:it.text1?.let { it }?:  let { "" })

            if(it.design_override) {
                it.detail_text1_color?.let {

                    text1_txv.setTextColor(Color.parseColor(it))
                    text1_head_txv.setTextColor(Color.parseColor(it))
                }
                it.detail_text2_color?.let {

                    text2_txv.setTextColor(Color.parseColor(it))
                }
                it.bg_color?.let {
                    //cardView.setCardBackgroundColor(Color.parseColor(it))
                }
                it.trans_bg_color?.let {

                    //trans_llay.setBackgroundColor(Color.parseColor(it))
                }
            }

        }
    }


    private fun setValues() {
        

        setBackGrounds( appViewModel.appRepository.prefManager.getAPPObject())

        appViewModel.appRepository.s_pref.stringLiveData("app_object", "").observe(this,
            Observer { json ->

            LogUtils.LOGD("niravu", "Detail1Activity   app_object json=  "+json)
            if(isValid(json)){
                var m_app: MApp? =getAppObject(json)

                setBackGrounds(m_app)

            }

        })
    }
/*    private fun setBackGrounds(   app: MApp?) {

        app?.let {

            it.bg_detail_1?.let {

                LogUtils.LOGD("niravu", "Detail1Activity app_object bg_splash_1=  "+it)
                Picasso.with(this@Detail1Activity).load(Html.fromHtml(it!!).toString()).placeholder(R.drawable.icn_niravu_bg6_2).error(R.drawable.icn_niravu_bg6_2).into(object :
                    Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {

                        top_clay.setBackground( BitmapDrawable(resources,bitmap))

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable) {
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {

                    }
                })
                //com.squareup.picasso.Picasso.with(this@Detail1Activity).load(android.text.Html.fromHtml(it).toString()).into(top_clay)  ;
            }


            it.icon_developer_1?.let {
                // LogUtils.LOGD("niravu", "Detail1Activity app_object icon_developer_1 =  "+it)
                //com.squareup.picasso.Picasso.with(this@Detail1Activity).load(android.text.Html.fromHtml(it).toString()).into(developer_dimv)  ;
            }
            it.icon_app_1?.let {
                //LogUtils.LOGD("niravu", "Detail1Activity app_object icon_app_1 =  "+it)
                // com.squareup.picasso.Picasso.with(this@Detail1Activity).load(android.text.Html.fromHtml(it).toString()).into(niravu_dimv)  ;
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


            it.bg_detail_1?.let {

                LogUtils.LOGD("niravu", "Detail1Activity app_object bg_splash_1=  "+it)
                Picasso.with(this@Detail1Activity).load(Html.fromHtml(it!!).toString()).placeholder(R.drawable.icn_niravu_bg6_2).error(R.drawable.icn_niravu_bg6_2).into(object :
                    Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {

                        top_clay.setBackground( BitmapDrawable(resources,bitmap))

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable) {
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {

                    }
                })
            }



        }
    }


    private fun setClicks() {
        close_dimv.setOnClickListener(View.OnClickListener {
            onBackFinish()
        })
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        onBackFinish()
    }
    override fun onDismiss(obj: Any?) {

    }
}
