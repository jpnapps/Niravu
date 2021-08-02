package com.jpndev.niravu.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jpndev.niravu.*
import com.jpndev.niravu.base.WebActivitykt
import com.jpndev.niravu.interfaces.OnApiResponseListner
import com.jpndev.niravu.prefs.stringLiveData
import com.jpndev.niravu.utility.ComingSoonDialog
import com.jpndev.niravu.utility.Constants
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel



import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.json_req_dimv
import kotlinx.android.synthetic.main.activity_register.login
import kotlinx.android.synthetic.main.activity_register.pwd_edt
import kotlinx.android.synthetic.main.activity_register.top_clay
import kotlinx.android.synthetic.main.activity_register.uname_edt


class RegisterActivity : NiravuActivity() {
    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }
    private lateinit var home_url: String
    private lateinit var dummy_first_url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        home_url=intent.getStringExtra(Intent.EXTRA_TEXT)
        dummy_first_url=intent.getStringExtra(Intent.EXTRA_COMPONENT_NAME)
        initAppViewModel()
        viewModelObserVer()


    }

    private fun viewModelObserVer() {
        uname_edt.afterTextChanged {
            appViewModel.registerDataChanged(
                uname_edt.text.toString(),
                email_edt.text.toString(),
                pwd_edt.text.toString()
            )
        }
        email_edt.afterTextChanged {
            appViewModel.registerDataChanged(
                uname_edt.text.toString(),
                email_edt.text.toString(),
                pwd_edt.text.toString()
            )
        }
        pwd_edt.apply {
            afterTextChanged {
                appViewModel.registerDataChanged(
                    uname_edt.text.toString(),
                    email_edt.text.toString(),
                    pwd_edt.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                       refreshRegister()

                }
                false
            }
            login.setOnClickListener {
                refreshRegister()

            }

        }
        appViewModel.loginFormState.observe(this@RegisterActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both uname_edt / pwd_edt is valid
            login.isEnabled = loginState.isDataValid
            if (loginState.emailError != null) {
                email_edt.error = getString(loginState.emailError)
            }
            if (loginState.usernameError != null) {
                uname_edt.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                pwd_edt.error = getString(loginState.passwordError)
            }
        })


        appViewModel.setBackGrounds(cly= top_clay)
        appViewModel.appRepository.s_pref.stringLiveData("app_object", "").observe(this,Observer { json ->

            LogUtils.LOGD("niravu", "HomeActivity   app_object json=  "+json)
            if(isValid(json)){
                var m_app: MApp? =getAppObject(json)

                appViewModel.setBackGrounds(getAppObject(json),top_clay)

            }

        })
    }


    fun refreshRegister() {
            //   appViewModel.callLogin(uname_edt.text.toString(), pwd_edt.text.toString())

            appViewModel.callRegister(uname_edt.text.toString(),email_edt.text.toString(), pwd_edt.text.toString(), object:
                OnApiResponseListner {
                override fun onApiInit(obj: Any?) {
                    setLoading()
                    showProgress()
                }

                override fun onApiPaginateSucess(obj: Any) {
                }

                override fun onApiSucess(obj: Any) {
                    if(obj is RespRegister)
                    {
                        if(obj.in_app)
                        {

                            obj.data?.let {
                               loginUpdate(uname_edt.text.toString(), pwd_edt.text.toString())
                            }?:let{
                                showErrorAlertDelay(message = "Server down , Please try later", message2 = "Server down , Please try later")
                        }


                        }
                        else
                        {
                            obj.web_url?.let {
                                WebActivitykt.startWebActivity2(this@RegisterActivity,obj.web_url,obj?.web_properties)

                            }?:let {
                                obj.browser_url?.let {

                                    openBrowser( it)

                                }?:let {
                                    ComingSoonDialog.showDialog(this@RegisterActivity, MBaseDialog( Constants.COMING_SOON))

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


    fun loginUpdate(uname: String,pwd: String ) {


        appViewModel.callLogin(uname, pwd, object: OnApiResponseListner {
            override fun onApiInit(obj: Any?) {
                // swipe_lay.isRefreshing = true
                setLoading()
                showProgress()
            }

            override fun onApiPaginateSucess(obj: Any) {
            }

            override fun onApiSucess(obj: Any) {
                if(obj is RespLogin)
                {


                    LogUtils.LOGD("niravu", "RA RespLogin    authorization_code=  "+obj.data?.authorization_code)
                    if(obj.in_app)
                    {

                        obj.data?.authorization_code?.let {
                            appViewModel.appRepository.prefManager.putSharedString( "auth_code",it)
                            refreshAccessToken(it)

                        }

                    }
                    else
                    {
                        obj.web_url?.let {
                            WebActivitykt.startWebActivity2(this@RegisterActivity,obj.web_url,obj?.web_properties)

                        }?:let {
                            obj.browser_url?.let {

                                openBrowser( it)

                            }?:let {
                                ComingSoonDialog.showDialog(this@RegisterActivity, MBaseDialog( Constants.COMING_SOON))

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
                // swipe_lay.isRefreshing = false
                hideProgress()
                showRequest(json_req_dimv,obj)
            }

            override fun onApiView(obj: Any?) {
            }
        },dummy_url = "https://api.beeone.co.uk/mock-tests/error-2",is_dummy = false)
    }

    private fun refreshAccessToken(code:String) {
        appViewModel.callAccessToken(code,object: OnApiResponseListner {
            override fun onApiInit(obj: Any?) {
                setLoading()
                showProgress()
            }

            override fun onApiPaginateSucess(obj: Any) {
            }

            override fun onApiSucess(obj: Any) {
                if (obj is RespAccessToekn) {


                    LogUtils.LOGD(
                        "niravu",
                        "RA    refreshAccessToken  code =  " + obj.data?.access_token
                    )
                    if (obj.in_app) {

                        obj.data?.access_token?.let {
                            appViewModel.appRepository.prefManager.putSharedString("access_token", it)
                            showActivity(appViewModel,this@RegisterActivity,1000,home_url,dummy_first_url)

                        }

                    } else {
                        obj.web_url?.let {
                            WebActivitykt.startWebActivity2(this@RegisterActivity, obj.web_url,obj?.web_properties)

                        } ?: let {
                            obj.browser_url?.let {

                                openBrowser(it)

                            } ?: let {
                                ComingSoonDialog.showDialog(
                                    this@RegisterActivity,
                                    MBaseDialog(Constants.COMING_SOON)
                                )

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
                            showErrorAlertDelay(
                                message = apifailure.message,
                                message2 = apifailure.message2
                            )
                        }


                    }
                }

            }

            override fun onApiResponse(obj: Any?) {
                // swipe_lay.isRefreshing = false
                hideProgress()
                showRequest(json_req_dimv, obj)
            }

            override fun onApiView(obj: Any?) {
            }

        })


    }





    override fun onDismiss(obj: Any?) {
        }

    }
