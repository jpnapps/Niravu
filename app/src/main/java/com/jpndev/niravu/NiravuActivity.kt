package com.jpndev.niravu

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.jpndev.niravu.interfaces.OnDismissListner
import com.jpndev.niravu.prefs.intLiveData
import com.jpndev.niravu.prefs.stringLiveData
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import com.jpndev.niravu.viewmodel.MODE
import com.jpndev.utilitylibrary.DeviceFitImageView
import com.jpndev.utilitylibrary.ToastHandler
import com.jpndev.utilitylibrary.base.BaseAppCompactActivity
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.jpndev.niravu.JpApplication.Companion.context
import com.jpndev.niravu.home.HomeActivity
import com.jpndev.niravu.register.LoginActivity
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_home.*


abstract class NiravuActivity : BaseAppCompactActivity(), OnDismissListner {
    var jsontest:String?=null
/*    fun ProgressButton.doneLoadingAnimation(
            context: Context,
            fillColor: Int ?= defaultColor(context),
            bitmap: Bitmap?= defaultVectorDoneImage(context),
            doneTime: Long ?= 3000,
            revertTime: Long ?= 4000
    ) {
        doneLoadingAnimation(fillColor!!, bitmap!!)
        progressType = ProgressType.INDETERMINATE
        startAnimation()
        Handler().run {
            postDelayed({ doneLoadingAnimation(fillColor!!, bitmap!!) }, doneTime!!)
            //  postDelayed({ revertAnimation() }, revertTime!!)
        }
    }*/


    fun <T1: Any, T2: Any, R: Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2)->R?): R? {
        return if (p1 != null && p2 != null) block(p1, p2) else null
    }
    fun <T1: Any, T2: Any, T3: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3)->R?): R? {
        return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
    }
    fun <T1: Any, T2: Any, T3: Any, T4: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, block: (T1, T2, T3, T4)->R?): R? {
        return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
    }
    fun <T1: Any, T2: Any, T3: Any, T4: Any, T5: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, p5: T5?, block: (T1, T2, T3, T4, T5)->R?): R? {
        return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(p1, p2, p3, p4, p5) else null
    }
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
    fun get2F(addtocard_total_pound: String?): String? {
        var text: String? = null
        try {
            text = String.format("%.2f", addtocard_total_pound?.toDouble())
            if (isValid(text))
                text = "" + text
        } catch (e: Exception) {
            text = null
            e.message
        }

        return text
    }
    fun getDateTimeString(textdate: String?): String {
        var dates : String =""
        try{
            //"2018-11-30 10:45:20"
            val fmtIn = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            val date : Date = fmtIn.parse(textdate)
            val fmtOut = SimpleDateFormat("dd MMM yyyy , hh:mm a")

            dates= fmtOut.format(date)

        }catch (e :java.lang.Exception)
        {

        }
        return  dates
    }
    fun getDateString(textdate: String?): String {
        var dates : String =""
        try{
            //"2018-11-30 10:45:20"
            val fmtIn = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            val date : Date = fmtIn.parse(textdate)
            val fmtOut = SimpleDateFormat("dd-MMM-yy")

            dates= fmtOut.format(date)

        }catch (e :java.lang.Exception)
        {

        }
        return  dates
    }
    fun getTimeString(textdate: String?): String {
        var dates : String =""
        try{
            //"2018-11-30 10:45:20"
            val fmtIn = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            val date : Date = fmtIn.parse(textdate)
            val fmtOut = SimpleDateFormat("hh:mm a")

            dates= fmtOut.format(date)

        }catch (e :java.lang.Exception)
        {

        }
        return  dates
    }

    
    

    fun defDoubleSetText(textv: TextView, text:Double) {

        var   decimalFormatter: DecimalFormat =  DecimalFormat("##.############");
        decimalFormatter.setMinimumFractionDigits(2);
        decimalFormatter.setMaximumFractionDigits(9);

        if (isValid(textv))
            textv.text =decimalFormatter.format(text)


    }





    fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }

    public fun showRequest(jsonReqDimv: DeviceFitImageView?, jsontest: String?) {

        if (BuildConfig.isShowApi) {
            jsonReqDimv!!.visibility = View.VISIBLE
            jsonReqDimv!!.setOnClickListener {
                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                val clipData = ClipData.newPlainText("Json Requests  ", jsontest)

                clipboardManager?.setPrimaryClip(clipData)
                // jsontest=""
                showToastMessage("Copied Json data and cleared")
                //ToastHandler.newInstance(this@AutoTopupPackageActivity).mustShowToast("Copied Json data and cleared")
            }
        }
        else
        {
            jsonReqDimv!!.visibility = View.GONE
        }

    }


    private lateinit var appViewModel2: AppViewModel
    private fun initAppViewModel() {
        appViewModel2 = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }

    private  var currentTheme: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppViewModel()

       // currentTheme =1
    currentTheme = appViewModel2.getMode()
        setAppTheme(currentTheme)
    }

    override fun onResume() {
        super.onResume()
    /*    val selectedTheme = sharedPref.getString(KEY_CURRENT_THEME, LILAC_THEME)
        if(currentTheme != selectedTheme)
            recreate()*/

        appViewModel2.appRepository.s_pref.intLiveData(MODE, 0).observe(this,Observer { json ->


            if(currentTheme!=json) {
                LogUtils.LOGD("niravu", "BA    json=  "+json)
                currentTheme=json
                recreate()
            }

        })
    }

    private fun setAppTheme(currentTheme: Int) {
        when (currentTheme) {
            0 -> setTheme(R.style.Theme_App_System)
            1 -> setTheme(R.style.Theme_App_Light)
            2 -> setTheme(R.style.Theme_App_Dark)

            else -> setTheme(R.style.Theme_App_System)
        }
    }

    fun showErrorAlertDelay(message : String ?="Error", colorPrimaryDark: Int =R.color.ccrbRed, duartion: Int=2000, isTest:Boolean =BuildConfig.isShowApi,inLive:Boolean =true,message2 : String? ="") {
        if (isTest) {
            hideProgress()
            setErrorStatus(message, colorPrimaryDark)
            showStatus()
            Handler().postDelayed({ hideStatus() }, duartion.toLong())
        }
        else if(inLive)
        {
            hideProgress()
            setErrorStatus(" "+message2 , colorPrimaryDark)
            showStatus()
            Handler().postDelayed({ hideStatus() }, duartion.toLong())
        }
    }



fun showSuccessAlertDelay(message: String ?="Success", colorPrimaryDark: Int =R.color.cursor1_color, duartion: Int=2000, isTest:Boolean =BuildConfig.isShowApi) {
        hideProgress()
        setErrorStatus(message, colorPrimaryDark)
        showStatus()
        Handler().postDelayed({ hideStatus() }, duartion.toLong())
    }




    fun showSuccessAlertDelayTask(message: String="Sucesss",colorPrimaryDark: Int =R.color.cursor1_color, duartion: Int=2000, isTest:Boolean =BuildConfig.isShowApi,    redirect: (Context, Activity?) -> Unit ) {
        hideProgress()
        setErrorStatus(message, colorPrimaryDark)
        showStatus()

        Handler().postDelayed({

            hideStatus()
            redirect(this@NiravuActivity,this@NiravuActivity)
        }, duartion.toLong())
    }

    fun showControlAlertDelay(message: String ?="", bg_color: Int =R.color.cursor1_color, duartion: Int=2000, isTest:Boolean =BuildConfig.isShowApi) {
        if (isTest) {
            hideProgress()
            setErrorStatus(message, bg_color)
            showStatus()
            Handler().postDelayed({ hideStatus() }, duartion.toLong())
        }
    }
    fun showAlertDelay(message: String ?="", bg_color: Int =R.color.cursor1_color, duartion: Int=2000, isTest:Boolean =BuildConfig.isShowApi) {
        // if (isTest) {
        hideProgress()
        setErrorStatus(message, bg_color)
        showStatus()
        Handler().postDelayed({ hideStatus() }, duartion.toLong())
        // }
    }

    fun showErrorAlert(message: String ?="", bg_color: Int =R.color.ccrbRed, duartion: Int=2000, isTest:Boolean =BuildConfig.isShowApi) {
        // if (isTest) {
        hideProgress()
        setErrorStatus(message, bg_color)
        showStatus()
        Handler().postDelayed({ hideStatus() }, duartion.toLong())
        // }
    }

    fun backfinishErrorAlert(message: String ?="", bg_color: Int =R.color.ccrbRed, duartion: Int=2000, isTest:Boolean =BuildConfig.isShowApi) {
        // if (isTest) {
        hideProgress()
        setErrorStatus(message, bg_color)
        showStatus()
        Handler().postDelayed({
            hideStatus()
            onBackFinish() }, duartion.toLong())
        // }
    }

    public fun showRequest(json_req_dimv: DeviceFitImageView?,obj: Any?,isShow:Boolean=BuildConfig.isShowApi) {
        if (isShow) {
            obj?.let {

                if (obj is MGsonJsonTest) {
                    //  jsontest: String ?=null
                    if (!isValid(jsontest))
                        jsontest = "";
                    jsontest = jsontest + "Url  = " + obj.url
                    obj?.jsonrequest?.let {
                        jsontest= jsontest+"\n     Request obj  = "+ obj.jsonrequest
                    }


                    json_req_dimv?.visibility = View.VISIBLE
                    json_req_dimv?.setOnClickListener {
                        var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                        val clipData = ClipData.newPlainText("Json Requests  ", jsontest)
                        clipboardManager?.setPrimaryClip(clipData)
                        // jsontest=""
                        showToastMessage("Copied Json data and cleared")
                        //ToastHandler.newInstance(this@AutoTopupPackageActivity).mustShowToast("Copied Json data and cleared")
                    }


                }


            }
                ?: let {

                    json_req_dimv?.visibility = View.VISIBLE
                    json_req_dimv?.setOnClickListener {
                        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                        val clipData = ClipData.newPlainText("Json Requests  ", jsontest)
                        clipboardManager?.setPrimaryClip(clipData)
                        // jsontest=""
                        showToastMessage("Copied Json data and cleared")
                        //ToastHandler.newInstance(this@AutoTopupPackageActivity).mustShowToast("Copied Json data and cleared")
                    }

                }
        }
        else {
            json_req_dimv?.visibility = View.GONE
        }
    }


    open fun onBackFinish() {
        // super.onBackPressed();
        hideProgress()
        finish()
    }

    open fun onBackFinishResult() {
        // super.onBackPressed();
        val returnIntent = Intent()
        //returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


    open fun onBackFinishResultReplace(isTrue: Boolean) {
        // super.onBackPressed();
        val returnIntent = Intent()
        returnIntent.putExtra(Intent.EXTRA_REPLACING, isTrue)
        //returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


    open fun onBackFinishActivity(code: Int) {
        // super.onBackPressed();
        finishActivity(code)
        //finishActivity();
    }

    open fun onBackFinishAffinity() {
        // super.onBackPressed();
        finishAffinity()
    }




    public fun defaultColor(context: Context) = ContextCompat.getColor(context, R.color.gradient_1_color)
    public fun defaultRedColor(context: Context) = ContextCompat.getColor(context, R.color.ccrbRed)



    fun Double.roundTo(n : Int) : Double {
        return "%.${n}f".format(Locale.ENGLISH,this).toDouble()
    }

    fun isValid(text: TextView?, error: String, function: () -> (Unit) ): Boolean {
        if (text != null) if (isValid(text.text.toString() + "")!!) return true
        function()
        text!!.requestFocus()
        return false
    }

    fun isValid(text: String?, function: () -> (Unit)): Boolean {
        if (text != null) if (!text.trim { it <= ' ' }.equals("", ignoreCase = true)) return true
        function()
        return false

    }

    fun isValid(text: String, text2: String, text3: String,  function: () -> (Unit)): Boolean? {
        if (isValid(text)!! || isValid(text2)!! || isValid(text3)!!)
            return true
        function()
        return false

    }

    open fun showToastMessage(message: String) {
        if (BuildConfig.isShowApi) ToastHandler.newInstance(this)
            .mustShowToast("Message  $message")
    }

    fun openBrowser(context: Context, link: String?) {
        try {
            link?.let {
                val intent = Intent(Intent. ACTION_VIEW)
                intent.data = Uri.parse(link)

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        } catch (e: Exception) {
            e.message
        }

    }
    fun openBrowser( link: String?) {
        try {
            link?.let {
                val intent = Intent(Intent. ACTION_VIEW)
                intent.data = Uri.parse(link)

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.message
        }

    }

    public fun getAppObject(jsonString: String): MApp? {
        var loginRoot: MApp? = null
        try {

            val gson =
                GsonBuilder().setPrettyPrinting().create()
            loginRoot = gson.fromJson(jsonString, MApp::class.java)
        } catch (e: Exception) {
        }
        return loginRoot
    }

    public fun showActivity(appViewModel: AppViewModel,activity: Activity,duration: Long,obj: MUpdateData) {
        Handler().postDelayed({
            var intent = Intent(activity, LoginActivity::class.java)
            var access_toekn:String?= appViewModel.appRepository.prefManager.getSharedString("access_token",null)
            var auth_code:String?= appViewModel.appRepository.prefManager.getSharedString("auth_code",null)
            if(isValid(access_toekn)) {

                intent = Intent(activity, HomeActivity::class.java)
                if(BuildConfig.isLive)
                    intent.putExtra(Intent.EXTRA_TEXT, obj.first_url)
                else
                    intent.putExtra(Intent.EXTRA_TEXT, obj.dummy_first_url)
            }
            else {
                /*if (!isValid(auth_code))
                {
                    showErrorAlertDelay(message = "Session Expired", message2 ="Session Expired")
                }*/

                    intent = Intent(activity, LoginActivity::class.java)

                    intent.putExtra(Intent.EXTRA_TEXT, obj.first_url)

                    intent.putExtra(Intent.EXTRA_COMPONENT_NAME, obj.dummy_first_url)
            }
          //  intent.putExtra(Intent.EXTRA_COMPONENT_NAME, obj)

            activity. startActivity(intent)

        }, duration)
    }
    public fun showActivity(appViewModel: AppViewModel,activity: Activity,duration: Long,first_url: String,dummy_first_url: String) {
        Handler().postDelayed({
            var intent = Intent(activity, LoginActivity::class.java)
            var access_toekn:String?= appViewModel.appRepository.prefManager.getSharedString("access_token",null)
            var auth_code:String?= appViewModel.appRepository.prefManager.getSharedString("auth_code",null)
            if(isValid(access_toekn))
            {

                    intent = Intent(activity, HomeActivity::class.java)
                if(BuildConfig.isLive)
                    intent.putExtra(Intent.EXTRA_TEXT, first_url)
                else
                    intent.putExtra(Intent.EXTRA_TEXT, dummy_first_url)
                }
            else {
                if (!isValid(auth_code))
                {
                    showErrorAlertDelay(message = "Session Expired", message2 ="Session Expired")
                }

                intent = Intent(activity, LoginActivity::class.java)

                    intent.putExtra(Intent.EXTRA_TEXT, first_url)

                    intent.putExtra(Intent.EXTRA_COMPONENT_NAME, dummy_first_url)
            }
            //  intent.putExtra(Intent.EXTRA_COMPONENT_NAME, obj)

            startActivity(intent)

        }, duration)
    }






}