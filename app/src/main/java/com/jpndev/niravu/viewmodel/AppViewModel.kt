package com.jpndev.niravu.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Patterns
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.niravu.MApp
import com.jpndev.niravu.R
import com.jpndev.niravu.ThemeMode
import com.jpndev.niravu.ThemeType
import com.jpndev.niravu.interfaces.OnApiResponseListner
import com.jpndev.niravu.register.LoginFormState
import com.jpndev.niravu.utility.LogUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


const val MODE = "mode_int"
const val MODE_LIST= "mode_list"
const val DARK_THEME = "mint"
class AppViewModel (application: Application) : AndroidViewModel(application) {
    public var appRepository: AppRepository;
    private var applicationContext: Application;

    init {
        applicationContext=application
        appRepository = AppRepository.getInstance(application)
    }

    private val _loading= MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading

    var qs_view_mode: Boolean=false
        //get() = qs_view_mode


    public fun getMode() :Int{

        return appRepository.prefManager.getSharedInt(MODE,0)
    }

    public fun updateMode( k:Int) {

        return appRepository.prefManager.putSharedInt(MODE,k)
    }


    fun callAppUpdateData(item: Any?=null, onApiResponseListner : OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean=false) {
        LogUtils.LOGD("niravu", "callAppUpdateData  is_dummy = "+is_dummy)

        return appRepository.apiUpdateData(item,onApiResponseListner=onApiResponseListner,dummy_url=dummy_url ,is_dummy = is_dummy)

    }

    fun callHomeScreenData(item: String, onApiResponseListner : OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean=false) {
        LogUtils.LOGD("niravu", "callHomeScreenData  url = "+item)

        return appRepository.apiHomeScreenData(item,onApiResponseListner=onApiResponseListner,dummy_url=dummy_url ,is_dummy = is_dummy)

    }

    fun callData(item: String, onApiResponseListner : OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean=false) {
        LogUtils.LOGD("niravu", "callData  url = "+item)

        return appRepository.apiData(item,onApiResponseListner=onApiResponseListner,dummy_url=dummy_url ,is_dummy = is_dummy)

    }
    fun callItemData(item: String, onApiResponseListner : OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean=false) {
        LogUtils.LOGD("niravu", "callItemData  url = "+item)

        return appRepository.apiMItemData(item,onApiResponseListner=onApiResponseListner,dummy_url=dummy_url ,is_dummy = is_dummy)

    }
    fun callQuizData(item: String, onApiResponseListner : OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean=false) {
        LogUtils.LOGD("quiz", "callQuizData  url = "+item)

        return appRepository.apiQuizData(item,onApiResponseListner=onApiResponseListner,dummy_url=dummy_url ,is_dummy = is_dummy)

    }
    public fun getViewThemeType(type:Int,mode: ThemeMode): ThemeType? {
        var theme:ThemeType?=null
      //  LogUtils.LOGD("d_theme", "getViewThemeType  type = "+type)
        try{
            when(type){
                1->theme=mode.type_1
                2->theme=mode.type_2
                3->theme=mode.type_3
                4->theme=mode.type_4
                5->theme=mode.type_5
                6->theme=mode.type_6
                7->theme=mode.type_7
                8->theme=mode.type_8
                9->theme=mode.type_9
                10->theme=mode.type_10
                11->theme=mode.type_11
                12->theme=mode.type_12
                30->theme=mode.type_30
                31->theme=mode.type_31
                32->theme=mode.type_32
                33->theme=mode.type_33
                34->theme=mode.type_34
                40->theme=mode.type_34
                200->theme=mode.type_34
            }
        }catch (e:Exception)
        {

        }
    //    LogUtils.LOGD("d_theme", "getViewThemeType  theme = "+theme?.text_color_1)
          return theme
    }


    fun callLogin(uname: String,pwd: String, onApiResponseListner : OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean=false) {
        LogUtils.LOGD("niravu", "callLogin  uname = "+uname)

        return appRepository.apiLogin(uname,pwd,onApiResponseListner=onApiResponseListner,dummy_url=dummy_url ,is_dummy = is_dummy)

    }
    fun callRegister(uname: String,email: String,pwd: String, onApiResponseListner : OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean=false) {
        LogUtils.LOGD("niravu", "callRegister  uname = "+uname)

        return appRepository.apiRegister(uname,email,pwd,onApiResponseListner=onApiResponseListner,dummy_url=dummy_url ,is_dummy = is_dummy)

    }

    fun callAccessToken(code: String, onApiResponseListner : OnApiResponseListner) {
        LogUtils.LOGD("niravu", "callAccessToken  code = "+code)

        return appRepository.apiGetAccessToken(code,onApiResponseListner=onApiResponseListner)

    }




    public fun setBackGrounds(   app: MApp?=appRepository.prefManager.getAPPObject(),cly:ConstraintLayout) {

        app?.let {

            when(getMode())
            {
                0->it.system_mode?.let {

                    setBackGrounds(it,cly)
                }
                1->it.light_mode?.let {
                    setBackGrounds(it,cly)
                }
                2->it.dark_mode?.let {
                    setBackGrounds(it,cly)
                }
                else->it.system_mode?.let {

                    setBackGrounds(it,cly)
                }
            }

        }
    }


    private fun setBackGrounds(   themeMode: ThemeMode?,cly:ConstraintLayout) {

        themeMode?.let {


            it.bg_home_1?.let {

                LogUtils.LOGD("niravu", "HomeActivity app_object bg_splash_1=  "+it)
                Picasso.with(applicationContext).load(Html.fromHtml(it!!).toString()).placeholder(R.drawable.icn_niravu_bg6_2).error(
                    R.drawable.icn_niravu_bg6_2).into(object :
                    Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {

                        cly.setBackground( BitmapDrawable(applicationContext.resources,bitmap))

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


    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        }
        else  if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }
    fun registerDataChanged(username: String,mail: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        }
        else  if (!isValidEmail(mail)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        }
        else  if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }
    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()

    }
    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
    fun rcvScroll(rcv:RecyclerView,pos:Int=0,time:Long=1000) {
        rcv.postDelayed(Runnable {
            // or use other API
            rcv.smoothScrollToPosition(pos)

            // give a delay of one second
        }, time)
    }

    fun scaleBitmapAndKeepRation(
        targetBmp: Bitmap,
        reqHeightInPixels: Int,
        reqWidthInPixels: Int
    ): Bitmap? {
        val matrix = Matrix()
        matrix.setRectToRect(
            RectF(0f, 0f, targetBmp.width.toFloat(), targetBmp.height.toFloat()),
            RectF(0f, 0f, reqWidthInPixels.toFloat(), reqHeightInPixels.toFloat()),
            Matrix.ScaleToFit.CENTER
        )
        LogUtils.LOGD("imageutil", "width =  "+targetBmp.width.toFloat()+" height =  "+ targetBmp.height.toFloat())
        LogUtils.LOGD("imageutil", "maxWidth =  "+reqWidthInPixels+" maxHeight =  "+reqHeightInPixels)
        return Bitmap.createBitmap(
            targetBmp,
            0,
            0,
            targetBmp.width,
            targetBmp.height,
            matrix,
            true
        )
    }

     fun resize(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
        var image = image
        return if (maxHeight > 0 && maxWidth > 0) {
            val width = image.width
            val height = image.height
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
            var finalWidth = maxWidth
            var finalHeight = maxHeight
            LogUtils.LOGD("imageutil", "ratioMax =  "+ratioMax+" ratioBitmap =  "+ratioBitmap)
            LogUtils.LOGD("imageutil", "maxWidth =  "+maxWidth+" maxHeight =  "+maxHeight)
            LogUtils.LOGD("imageutil", "width =  "+width+" height =  "+height)
            if (ratioMax>ratioBitmap) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            LogUtils.LOGD("imageutil", "finalWidth =  "+finalWidth+" finalHeight =  "+finalHeight)
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
            image
        } else {
            LogUtils.LOGD("imageutil", "else image")
            image
        }
    }
}