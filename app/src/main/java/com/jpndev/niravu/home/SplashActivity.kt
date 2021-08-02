package com.jpndev.niravu.home


import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.GsonBuilder
import com.jpndev.niravu.*
import com.jpndev.niravu.base.WebActivitykt
import com.jpndev.niravu.interfaces.OnApiResponseListner
import com.jpndev.niravu.interfaces.OnDismissListner
import com.jpndev.niravu.prefs.PrefManager
import com.jpndev.niravu.prefs.booleanLiveData
import com.jpndev.niravu.prefs.stringLiveData
import com.jpndev.niravu.utility.ComingSoonDialog
import com.jpndev.niravu.utility.Constants
import com.jpndev.niravu.utility.ImageZoomDialogFragment
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_home.*

import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_splash.json_req_dimv
import kotlinx.android.synthetic.main.activity_splash.top_clay
import java.util.ArrayList

class SplashActivity : NiravuActivity(),OnDismissListner {

    private lateinit var appViewModel: AppViewModel
  //  private lateinit var liveSharedPreferences: LiveSharedPreferences
    private lateinit var prefs: SharedPreferences
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

/*        appViewModel.appRepository.mld_mode_data.value=appViewModel.appRepository.prefManager.getModes(
            mutableListOf(MModeData(text1 = "Light",type = 1,isSelected = false,bg1_color = "#ffffff",text1_color = "#000000"), MModeData(text1 = "Dark",type = 2,isSelected = false,bg1_color = "#000000",text1_color = "#ffffff"), MModeData(text1 = "System",type = 0,isSelected = true,bg1_color = "#ffffff",text1_color = "#008B8B")  ) as ArrayList<MModeData>?)*/

        appViewModel.appRepository.mld_mode_data.value=appViewModel.appRepository.prefManager.getModes(
            mutableListOf(MModeData(text1 = "Light",type = 1,isSelected = false,bg1_color = "#ffffff",text1_color = "#000000"), MModeData(text1 = "Dark",type = 2,isSelected = false,bg1_color = "#ffffff",text1_color = "#000000"), MModeData(text1 = "System",type = 0,isSelected = true,bg1_color = "#ffffff",text1_color = "#000000")  ) as ArrayList<MModeData>?)
       // liveSharedPreferences = LiveSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this))
       // prefs= PrefManager.getInstance(this@SplashActivity).getSPref()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        onBackFinishAffinity()
        //finishAffinity()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initAppViewModel()


        //if(appViewModel.getMode()!==0)




        setBackGrounds(PrefManager.getInstance(this).getAPPObject())



        appViewModel.appRepository.s_pref.stringLiveData("app_object", "").observe(this,Observer { json ->

            LogUtils.LOGD("niravu", "livedata splash app_object json=  "+json)
             if(isValid(json)){
                 var m_app: MApp? =getAppObject(json)

                 setBackGrounds(m_app)

             }

        })



    }

/*    private fun setBackGrounds(   app: MApp?) {

        app?.let {

            it.bg_splash_1?.let {

                LogUtils.LOGD("niravu", "splash app_object bg_splash_1=  "+it)
                Picasso.with(this@SplashActivity).load(Html.fromHtml(it!!).toString()).placeholder(R.drawable.icn_niravu_bg6_2).error(R.drawable.icn_niravu_bg6_2).into(object :
                    Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {

                        top_clay.setBackground( BitmapDrawable(resources,bitmap))

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable) {
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {

                    }
                })
                //com.squareup.picasso.Picasso.with(this@SplashActivity).load(android.text.Html.fromHtml(it).toString()).into(top_clay)  ;
            }


            it.icon_developer_1?.let {
                LogUtils.LOGD("niravu", "splash app_object icon_developer_1 =  "+it)
                com.squareup.picasso.Picasso.with(this@SplashActivity).load(android.text.Html.fromHtml(it).toString()).into(developer_dimv)  ;
            }
            it.icon_app_1?.let {
                LogUtils.LOGD("niravu", "splash app_object icon_app_1 =  "+it)
                com.squareup.picasso.Picasso.with(this@SplashActivity).load(android.text.Html.fromHtml(it).toString()).into(niravu_dimv)  ;
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


            it.bg_splash_1?.let {

                LogUtils.LOGD("niravu", "SplashActivity app_object bg_splash_1=  "+it)
                Picasso.with(this@SplashActivity).load(Html.fromHtml(it!!).toString()).placeholder(R.drawable.icn_niravu_bg6_2).error(R.drawable.icn_niravu_bg6_2).into(object :
                    Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {

                        top_clay.setBackground( BitmapDrawable(resources,bitmap))

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable) {
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {

                    }
                })
                //com.squareup.picasso.Picasso.with(this@HomeActivity).load(android.text.Html.fromHtml(it).toString()).into(top_clay)  ;
            }


            it.icon_developer_1?.let {
                // LogUtils.LOGD("niravu", "SplashActivity app_object icon_developer_1 =  "+it)
                com.squareup.picasso.Picasso.with(this@SplashActivity).load(android.text.Html.fromHtml(it).toString()).into(developer_dimv)  ;
            }
            it.icon_app_1?.let {
                //LogUtils.LOGD("niravu", "SplashActivity app_object icon_app_1 =  "+it)
                com.squareup.picasso.Picasso.with(this@SplashActivity).load(android.text.Html.fromHtml(it).toString()).into(niravu_dimv)  ;
            }
        }
    }


    override fun onResume() {
        super.onResume()
        refreshUpdateData()
    }
    override fun onDismiss(obj: Any?) {

    }
    fun getObject(jsonString: String): Any? {
        var loginRoot: Any? = null
        try {

            val gson =
                GsonBuilder().setPrettyPrinting().create()
            loginRoot = gson.fromJson(jsonString, Any::class.java)
        } catch (e: Exception) {
        }
        return loginRoot
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
                         //  PrefManager.getInstance(this@SplashActivity).saveObject(obj.app,"app_object")
                           //    PrefManager.getInstance(this@SplashActivity).saveAPPObject(it,"app_object")
                               appViewModel.appRepository.prefManager.saveAPPObject(it,"app_object")
                           }

                           if(obj.in_app)
                           {
                               checkUpdate(obj)


                           }
                           else
                           {
                               obj.web_url?.let {
                                   WebActivitykt.startWebActivity2(this@SplashActivity,obj.web_url,obj?.web_properties)

                               }?:let {
                                   obj.browser_url?.let {

                                       openBrowser( it)

                                   }?:let {
                                       ComingSoonDialog.showDialog(this@SplashActivity, MBaseDialog( Constants.COMING_SOON))

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

    private fun checkUpdate(obj: MUpdateData) {

        if(getCurrentVersionCode()>0)
        {
            if(obj.version_code>getCurrentVersionCode())
            {
                showForceUpdateDialog(obj)
            }
            else
            {
                showActivity(appViewModel,this@SplashActivity,1000,obj)


            }


        }else{
            showActivity(appViewModel,this@SplashActivity,1000,obj)
           /* Handler().postDelayed({
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                intent.putExtra(Intent.EXTRA_TEXT, obj.first_url)
                startActivity(intent)

            }, 1000)*/
        }
    }

    private fun showHomeActivity(duration: Long,obj: MUpdateData) {
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            if(BuildConfig.isLive)
              intent.putExtra(Intent.EXTRA_TEXT, obj.first_url)
            else
              intent.putExtra(Intent.EXTRA_TEXT, obj.dummy_first_url)
            startActivity(intent)

        }, duration)
    }


/*
    private fun showActivity(duration: Long,obj: MUpdateData) {
        Handler().postDelayed({
            var intent = Intent(this@SplashActivity, LoginActivity::class.java)
            var access_toekn:String?= appViewModel.appRepository.prefManager.getSharedString("access_token",null)
            var auth_code:String?= appViewModel.appRepository.prefManager.getSharedString("auth_code",null)
            if(isValid(access_toekn))
                intent = Intent(this@SplashActivity, HomeActivity::class.java)
            else {
                     if (!isValid(auth_code))
                     {
                         showErrorAlertDelay(message = "Session Expired", message2 ="Session Expired")
                     }
                    intent = Intent(this@SplashActivity, LoginActivity::class.java)
            }
            if(BuildConfig.isLive)
                intent.putExtra(Intent.EXTRA_TEXT, obj.first_url)
            else
                intent.putExtra(Intent.EXTRA_TEXT, obj.dummy_first_url)
            startActivity(intent)

        }, duration)
    }
*/



    private fun getCurrentVersionCode() :Int{
        // val currentVersion = packageInfo!!.versionName
        var currentVersion_code: Int=-1
        val packageManager = this.packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0)
            currentVersion_code = packageInfo!!.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
       // val currentVersion = packageInfo!!.versionName
        return currentVersion_code
    }


    fun showForceUpdateDialog(obj: MUpdateData) {
        if (!isFinishing && !isDestroyed) {
            //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper());
            val alertDialogBuilder =
                AlertDialog.Builder(this)
            //  alertDialogBuilder.setTitle("A New Update is Available");
            //  alertDialogBuilder.setMessage(getString(R.string.youAreNotUpdatedMessage) + " " + latestVersion + getString(R.string.youAreNotUpdatedMessage1));
            val title:String=obj.update_title?:resources.getString(R.string.youAreNotUpdatedTitle)
            val message:String=obj.update_message?:resources.getString(R.string.youAreNotUpdatedMessage) + " " + obj.version_name + getString(R.string.youAreNotUpdatedMessage1)

                    alertDialogBuilder.setTitle( Html.fromHtml(title))
            alertDialogBuilder.setMessage(
                Html.fromHtml(message)

                )


            //final String packagename = "com.beeone.ccrbx";
            val packagename = "com.jpndev.niravu"
            alertDialogBuilder.setCancelable(!obj.is_force_update)
            if(!obj.is_force_update)
            alertDialogBuilder.setNegativeButton("cancel") { dialog, id -> //getPackageName()
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                dialog.cancel()
            }
            alertDialogBuilder.setPositiveButton(
                R.string.update
            ) { dialog, id -> //getPackageName()
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packagename")
                    )
                )
                dialog.cancel()
            }
            //   alertDialogBuilder.show();
            val alert = alertDialogBuilder.create()
            alert.show()
        }
    }





}
