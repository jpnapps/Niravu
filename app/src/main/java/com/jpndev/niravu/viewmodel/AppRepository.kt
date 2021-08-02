package com.jpndev.niravu.viewmodel

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.jpndev.niravu.*
import com.jpndev.niravu.interfaces.OnApiResponseListner
import com.jpndev.niravu.prefs.PrefManager
import com.jpndev.niravu.register.RespAccessToekn
import com.jpndev.niravu.register.RespLogin
import com.jpndev.niravu.register.RespRegister
import com.jpndev.niravu.retrofit.ApiIClient
import com.jpndev.niravu.retrofit.ApiInterface
import com.jpndev.niravu.utility.ComingSoonDialog
import com.jpndev.niravu.utility.Constants
import com.jpndev.niravu.utility.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository (application: Application) {
    //private val application2: Application? = application
    //val mutableLiveData = MutableLiveData<List<MPaymentGateway>>()
    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: AppRepository? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: AppRepository(application).also { instance = it }
            }
    }
    public var prefManager: PrefManager;
    public var s_pref: SharedPreferences;
    private var applicationContext: Application;
    init {
        applicationContext=application
        prefManager = PrefManager.getInstance(application)
        s_pref = prefManager.getSPref()
    }

    val mld_cap_pos = MutableLiveData<Int>()
    val mld_vc_cap_pos = MutableLiveData<Int>()

    /**ModeData*/
    /** */
    val mld_mode_data = MutableLiveData<ArrayList<MModeData>>()



  /*  private lateinit var prefs: SharedPreferences
    fun getSPref(application2: Application):SharedPreferences
    {
        prefs= PrefManager.getInstance(application2).getSPref()
        return prefs
    }
*/

    /**UpdateData*/
    /** */
    val mld_MUpdateData = MutableLiveData<MUpdateData>()
    fun  apiUpdateData(item:  Any?=null, onApiResponseListner: OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean =false) {
        val endpoint = "apiUpdateData"
        try {
            onApiResponseListner.onApiInit(false)
            val jsonObject = JsonObject()
            jsonObject.addProperty("device_type", "android")
            val apiService = ApiIClient.getClient().create(ApiInterface::class.java)
           var url = BuildConfig.BASE_URL + BuildConfig.MID_URL +  "1"

            LogUtils.LOGD("niravu", endpoint+"  is_dummy = "+is_dummy)
            LogUtils.LOGD("prefs", endpoint+"  url = "+url)
            if (is_dummy) {
                url = dummy_url ?: url
            } else {
                //  val call = apiService.submitApi(url)

            }
            val call = apiService.getMUpdateData(url)
           // val call = apiService.getMUpdateData(url,jsonObject)
            call.enqueue(object : Callback<MUpdateData> {
                override fun onResponse(call: Call<MUpdateData>, response: Response<MUpdateData>) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                    // hideProgress()
                    val statusCode = response.code()
                    LogUtils.LOGD("niravu", endpoint+"  onResponse$statusCode")
                    try {
                     //if (response.isSuccessful) {
                          val root: MUpdateData? = response.body()
                         root?.let {
                            if (root.status) {
                                onApiResponseListner.onApiSucess(root)
                                mld_MUpdateData.setValue(root);
                            } else {
                                var error: String = getErrorMessage2(it.message, it.errors)
                                onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = error))
                            }
                        }?: let {
                            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = "api " + endpoint + " ResponseBody not successful", message2 = " Error : " + endpoint + "  api  ResponseBody "))
                        }
                       /* } else {
                            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = "api " + endpoint + " Response not successful", message2 = " Error : " + endpoint + "  api Response "))
                        }*/
                    } catch (e: Exception) {
                        onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = "api " + endpoint + "  e " + e.message, message2 = " Exception : " + endpoint + "   api response "))
                    }

                }

                override fun onFailure(call: Call<MUpdateData>, t: Throwable) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, t, Constants.isEXCEPTION, false, message = "api " + endpoint + "  t " + t.message, message2 = "Failure : " + endpoint + "  api  "))
                }
            })


        } catch (e: Exception) {
            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = " api " + endpoint + "  e " + e.message, message2 = "  Exception : " + endpoint + "  api  , something went wrong "))

        }

    }

    private fun getErrorMessage2(message: String ? =" Error", errors: Any?): String {
        var error2: String = message?:"Error"
        if(errors is List<*>) {
            if (isValidSize(errors as List<*>)) {
                val sb = StringBuilder()
                for (i in errors!! as List<*>) {

                    // sb.append(i.toString()+"/n")
                    sb.append(i.toString() + System.getProperty("line.separator"))
                }
                error2 = sb.toString()

            }
        }
        else if(errors is String)
        {
            error2=errors as String
        }
        return error2
    }
    fun isValidSize(list: List<*>?): Boolean {
        if (list != null) if (list.size > 0) return true
        return false

    }




    /**HomeScreenData*/
    /** */
    val mld_HomeScreenData = MutableLiveData<MHomeScreenRoot>()
    val mld_HomeData = MutableLiveData<ArrayList<MHomeData>>()
    val mld_BannerData = MutableLiveData<ArrayList<MBanner>>()
    fun  apiHomeScreenData(url_end:  String, onApiResponseListner: OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean =false) {
        val endpoint = "apiHomeScreenData"
        try {
            onApiResponseListner.onApiInit(false)
            val jsonObject = JsonObject()
            jsonObject.addProperty("device_type", "android")
            val apiService = ApiIClient.getClient().create(ApiInterface::class.java)
            var url = BuildConfig.BASE_URL + BuildConfig.MID_URL +  url_end

          //  LogUtils.LOGD("niravu", endpoint+"  is_dummy = "+is_dummy)
            LogUtils.LOGD("niravu", endpoint+"  url = "+url)
            if (is_dummy) {
                url = dummy_url ?: url
            } else {
                //  val call = apiService.submitApi(url)

            }
            val call = apiService.getHomeScreenData(url)
            // val call = apiService.getMUpdateData(url,jsonObject)
            call.enqueue(object : Callback<MHomeScreenRoot> {
                override fun onResponse(call: Call<MHomeScreenRoot>, response: Response<MHomeScreenRoot>) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                   // val statusCode = response.code()
                   // LogUtils.LOGD("niravu", endpoint+"  onResponse$statusCode")
                    try {
                        //if (response.isSuccessful) {
                        val root: MHomeScreenRoot? = response.body()
                        root?.let {
                            if (root.status) {
                                onApiResponseListner.onApiSucess(root)
                                mld_HomeScreenData.setValue(root);
                               // mld_HomeData.setValue(root.data as ArrayList<MHomeData>);
                                root?.data?.let {
                                    mld_HomeData.setValue(it as ArrayList<MHomeData>)
                                }
                                root?.banner_data?.let {
                                    mld_BannerData.setValue(it as ArrayList<MBanner>)
                                }
                            } else {
                                var error: String = getErrorMessage2(it.message, it.errors)
                                onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = error))
                            }
                        }?: let {
                            //onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = "api " + endpoint + " ResponseBody not successful", message2 = " Error : " + endpoint + "  api  ResponseBody "))
                        }
                        /* } else {
                             onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = "api " + endpoint + " Response not successful", message2 = " Error : " + endpoint + "  api Response "))
                         }*/
                    } catch (e: Exception) {
                        onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = "api " + endpoint + "  e " + e.message, message2 = " Exception : " + endpoint + "   api response "))
                    }

                }

                override fun onFailure(call: Call<MHomeScreenRoot>, t: Throwable) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, t, Constants.isEXCEPTION, false, message = "api " + endpoint + "  t " + t.message, message2 = "Failure : " + endpoint + "  api  "))
                }
            })


        } catch (e: Exception) {
            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = " api " + endpoint + "  e " + e.message, message2 = "  Exception : " + endpoint + "  api  , something went wrong "))

        }

    }



    val mld_list_BannerData = MutableLiveData<ArrayList<MBanner>>()
    val mld_data = MutableLiveData<ArrayList<MHomeData>>()
    fun  apiData(url_end:  String, onApiResponseListner: OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean =false) {
        val endpoint = "apiData"
        try {
            onApiResponseListner.onApiInit(false)
            val jsonObject = JsonObject()
            jsonObject.addProperty("device_type", "android")
            val apiService = ApiIClient.getClient().create(ApiInterface::class.java)
            var url = BuildConfig.BASE_URL + BuildConfig.MID_URL +  url_end

            //  LogUtils.LOGD("niravu", endpoint+"  is_dummy = "+is_dummy)
            LogUtils.LOGD("prefs", endpoint+"  url = "+url)
            if (is_dummy) {
                url = dummy_url ?: url
            } else {
                //  val call = apiService.submitApi(url)

            }
            val call = apiService.getHomeScreenData(url)
            call.enqueue(object : Callback<MHomeScreenRoot> {
                override fun onResponse(call: Call<MHomeScreenRoot>, response: Response<MHomeScreenRoot>) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                   LogUtils.LOGD("prefs", ""+endpoint+"  onResponse${response.isSuccessful}")
                    try {
                        //if (response.isSuccessful) {
                        val root: MHomeScreenRoot? = response.body()
                        root?.let {
                            if (root.status) {
                                onApiResponseListner.onApiSucess(root)
                               // mld_HomeScreenData.setValue(root);
                                root?.data?.let {
                                    mld_data.postValue(it as ArrayList<MHomeData>)
                                }
                                root?.banner_data?.let {
                                    mld_list_BannerData.setValue(it as ArrayList<MBanner>)
                                }
                            } else {
                                var error: String = getErrorMessage2(it.message, it.errors)
                                onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = error))
                            }
                        }?: let {
                            //onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = "api " + endpoint + " ResponseBody not successful", message2 = " Error : " + endpoint + "  api  ResponseBody "))
                        }//

                    } catch (e: Exception) {
                        onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = "api " + endpoint + "  e " + e.message, message2 = " Exception : " + endpoint + "   api response "))
                    }

                }

                override fun onFailure(call: Call<MHomeScreenRoot>, t: Throwable) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, t, Constants.isEXCEPTION, false, message = "api " + endpoint + "  t " + t.message, message2 = "Failure : " + endpoint + "  api  "))
                }
            })


        } catch (e: Exception) {
            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = " api " + endpoint + "  e " + e.message, message2 = "  Exception : " + endpoint + "  api  , something went wrong "))

        }

    }






    fun apiLogin(username: String, password: String,onApiResponseListner: OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean =false){

        try {
            onApiResponseListner.onApiInit(false)
            val apiService = ApiIClient.getClient().create(ApiInterface::class.java)
           // var url = "http://sarintechy.xyz/backend/web/1/authorize"
            var url =  BuildConfig.BASE_BACKEND_URL+"authorize"
            val jsonObj = JsonObject()
            jsonObj.addProperty("username",username)
            jsonObj.addProperty("password", password)

            if (is_dummy) {
                url = dummy_url ?: url
            } else {
                //  val call = apiService.submitApi(url)

            }

            val call = apiService.login(url,jsonObj)
            call.enqueue(object : Callback<RespLogin> {
                override fun onResponse(call: Call<RespLogin>, response: Response<RespLogin>) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url=url,jsonrequest = jsonObj))
                    val statusCode = response.code()
                    try {

                        if (response.isSuccessful) {
                            val root: RespLogin? = response.body()
                            root?.let {
                                if(root.status==1)
                                    onApiResponseListner.onApiSucess(it)
                                else
                                {
                                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api authorize status false" ,message2 =" Invalid Entries "))
                                }

                            }?:let {
                                onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api authorize ResponseBody not successful" ,message2 =" Invalid Entries "))
                            }

                        } else {
                            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api authorize Response not successful" ,message2 =" Invalid Entries"))
                        }
                    } catch (e: Exception) {
                        onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false,message = "Api authorize  e "+e.message ,message2 ="Invalid Entries "))
                    }

                }

                override fun onFailure(call: Call<RespLogin>, t: Throwable) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url=url))
                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, t, Constants.isEXCEPTION, false,message = "Api authorize t "+t.message ,message2 ="Invalid Entries"))
                }
            })

        } catch (e: Exception) {
            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false,message = "Api authorize  e "+e.message ,message2 ="  Exception :Api authorize  , something went wrong "))
        }

    }



    fun apiGetAccessToken(authorization_code: String,onApiResponseListner: OnApiResponseListner){

        try {
            onApiResponseListner.onApiInit(false)
            val apiService = ApiIClient.getClient().create(ApiInterface::class.java)
            val url =  BuildConfig.BASE_BACKEND_URL+"accesstoken"
            val jsonObj = JsonObject()
            jsonObj.addProperty("authorization_code",authorization_code)
            val call = apiService.accessToken(url,jsonObj)
            call.enqueue(object : Callback<RespAccessToekn> {
                override fun onResponse(call: Call<RespAccessToekn>, response: Response<RespAccessToekn>) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url=url,jsonrequest = jsonObj))
                    val statusCode = response.code()
                    try {

                      //  if (response.isSuccessful) {
                            val root: RespAccessToekn? = response.body()
                            root?.let {
                                if(root.status==1)
                                    onApiResponseListner.onApiSucess(it)
                                else
                                {
                                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api accesstoken status false" ,message2 =" Invalid Entries"))
                                }

                            }?:let {
                                onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api accesstoken ResponseBody not successful" ,message2 =" Invalid Entries "))
                            }

                       /* } else {

                       var gson = Gson()
                          /*   var errorResponse:List<ErrorResp> = gson.fromJson(
                                response.errorBody().charStream(),
                                 ErrorResp::class.java
                            )*/

                             var listType = object : TypeToken<ArrayList<ErrorResp>>() {}.type
                             var errorlist:List<ErrorResp> = Gson().fromJson( response.errorBody()?.charStream(), listType)
                            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api accesstoken Response not successful" ,message2 =" Error :Api accesstoken  Response "))
                        }*/
                    } catch (e: Exception) {
                        onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false,message = "Api accesstoken  e "+e.message ,message2 =" Invalid Entries "))
                    }

                }

                override fun onFailure(call: Call<RespAccessToekn>, t: Throwable) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url=url))
                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, t, Constants.isEXCEPTION, false,message = "Api accesstoken t "+t.message ,message2 ="Invalid Entries  "))
                }
            })

        } catch (e: Exception) {
            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false,message = "Api accesstoken  e "+e.message ,message2 ="  Invalid Entries "))
        }

    }




    fun apiRegister(username: String,email: String, password: String,onApiResponseListner: OnApiResponseListner ,dummy_url:String?=null, is_dummy:Boolean =false){

        try {
            onApiResponseListner.onApiInit(false)
            val apiService = ApiIClient.getClient().create(ApiInterface::class.java)
            var url = "http://sarintechy.xyz/backend/web/1/register"
            val jsonObj = JsonObject()
            jsonObj.addProperty("username",username)
            jsonObj.addProperty("password", password)
            jsonObj.addProperty("email", email)

            if (is_dummy) {
                url = dummy_url ?: url
            } else {
                //  val call = apiService.submitApi(url)

            }


            val call = apiService.register(url,jsonObj)
            call.enqueue(object : Callback<RespRegister> {
                override fun onResponse(call: Call<RespRegister>, response: Response<RespRegister>) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url=url,jsonrequest = jsonObj))
                  //  val statusCode = response.code()
                    try {

                       // if (response.isSuccessful) {
                            val root: RespRegister? = response.body()
                            root?.let {
                                if(root.status==1)
                                    onApiResponseListner.onApiSucess(it)
                                else
                                {
                                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api register status false" ,message2 ="Invalid Entries"))
                                }

                            }?:let {
                                onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api register ResponseBody not successful" ,message2 =" Invalid Entries"))
                            }

                       /* } else {
                            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false,message = "Api register Response not successful" ,message2 =" Error :Api register  Response "))
                        }*/
                    } catch (e: Exception) {
                        onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false,message = "Api register  e "+e.message ,message2 =" Invalid Entries"))
                    }

                }

                override fun onFailure(call: Call<RespRegister>, t: Throwable) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url=url))
                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, t, Constants.isEXCEPTION, false,message = "Api register t "+t.message ,message2 ="Invalid Entries  "))
                }
            })

        } catch (e: Exception) {
            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false,message = "Api register  e "+e.message ,message2 =" Invalid Entries "))
        }

    }


    val mld_quiz_answered = MutableLiveData<Int>()

    val mld_quiz_data = MutableLiveData<ArrayList<MQuiz>>()
    fun  apiQuizData(url_end:  String, onApiResponseListner: OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean =false) {
        val endpoint = "apiQuizData"
        try {
            onApiResponseListner.onApiInit(false)
            val jsonObject = JsonObject()
            jsonObject.addProperty("device_type", "android")
            val apiService = ApiIClient.getClient().create(ApiInterface::class.java)
            var url = BuildConfig.BASE_URL + BuildConfig.MID_URL +  url_end

            //  LogUtils.LOGD("niravu", endpoint+"  is_dummy = "+is_dummy)
            LogUtils.LOGD("quiz", endpoint+"  url = "+url)
            if (is_dummy) {
                url = dummy_url ?: url
            } else {
                //  val call = apiService.submitApi(url)

            }
            val call = apiService.getHomeScreenData(url)
            call.enqueue(object : Callback<MHomeScreenRoot> {
                override fun onResponse(call: Call<MHomeScreenRoot>, response: Response<MHomeScreenRoot>) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                    LogUtils.LOGD("quiz", ""+endpoint+"  onResponse = ${response.isSuccessful}")
                    try {
                        //if (response.isSuccessful) {
                        val root: MHomeScreenRoot? = response.body()
                        root?.let {
                            if (root.status) {
                                   onApiResponseListner.onApiSucess(root)
                                // mld_HomeScreenData.setValue(root);
                                LogUtils.LOGD("quiz", "Rep apiQuizData size =  "+ root?.quiz_data?.size)
                                root?.quiz_data?.let {

                                    mld_quiz_data.postValue(it as ArrayList<MQuiz>)
                                }

                            } else {
                                var error: String = getErrorMessage2(it.message, it.errors)
                                onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = error))
                            }
                        }?: let {
                            //onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = "api " + endpoint + " ResponseBody not successful", message2 = " Error : " + endpoint + "  api  ResponseBody "))
                        }//

                    } catch (e: Exception) {
                        onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = "api " + endpoint + "  e " + e.message, message2 = " Exception : " + endpoint + "   api response "))
                    }

                }

                override fun onFailure(call: Call<MHomeScreenRoot>, t: Throwable) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, t, Constants.isEXCEPTION, false, message = "api " + endpoint + "  t " + t.message, message2 = "Failure : " + endpoint + "  api  "))
                }
            })


        } catch (e: Exception) {
            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = " api " + endpoint + "  e " + e.message, message2 = "  Exception : " + endpoint + "  api  , something went wrong "))

        }

    }



    val mld_item_data = MutableLiveData<ArrayList<MItemData>>()
    fun  apiMItemData(url_end:  String, onApiResponseListner: OnApiResponseListner, dummy_url:String?=null, is_dummy:Boolean =false) {
        val endpoint = "item_data"
        try {
            LogUtils.LOGD("newitem", endpoint+"  url = apiMItemData")
            onApiResponseListner.onApiInit(false)
            val jsonObject = JsonObject()
            jsonObject.addProperty("device_type", "android")


            jsonObject.addProperty("X-Access-Token",   prefManager.getSharedString("access_token",""))
            val apiService = ApiIClient.getClient().create(ApiInterface::class.java)


            var url = BuildConfig.BASE_URL + BuildConfig.MID_URL +  url_end

            if (is_dummy) {
                url = dummy_url ?: url
            } else {
                //  val call = apiService.submitApi(url)
            }
            val call = apiService.getMItemData(url,prefManager.getSharedString("access_token",""))

            LogUtils.LOGD("newitem", endpoint+" "+url+" "+prefManager.getSharedString("access_token",""))

            call.enqueue(object : Callback<ArrayList<MItemData>> {
                override fun onResponse(call: Call<ArrayList<MItemData>>, response: Response<ArrayList<MItemData>>) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                    LogUtils.LOGD("newitem", ""+endpoint+"  onResponse = ${response.isSuccessful}")
                    try {
                        //if (response.isSuccessful) {
                        val root: ArrayList<MItemData>? = response.body()
                        root?.let {

                                onApiResponseListner.onApiSucess(root)

                                LogUtils.LOGD("newitem", "item size =  "+ root?.size)


                                    mld_item_data.postValue(it )


                            /* else {
                                var error: String = getErrorMessage2(it.message, it.errors)
                                onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = error))
                            }*/


                        }?: let {
                            //onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, response, Constants.isRESPONSE_ERROR, false, message = "api " + endpoint + " ResponseBody not successful", message2 = " Error : " + endpoint + "  api  ResponseBody "))
                        }

                    } catch (e: Exception) {
                        onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = "api " + endpoint + "  e " + e.message, message2 = " Exception : " + endpoint + "   api response "))
                    }

                }

                override fun onFailure(call: Call<ArrayList<MItemData>>, t: Throwable) {
                    onApiResponseListner.onApiResponse(MGsonJsonTest(url = url, jsonrequest = jsonObject))
                    onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, t, Constants.isEXCEPTION, false, message = "api " + endpoint + "  t " + t.message, message2 = "Failure : " + endpoint + "  api  "))
                }
            })


        } catch (e: Exception) {
            onApiResponseListner.onApiFailure(ApiFailure2(Constants.ALL_NETWORK, e, Constants.isEXCEPTION, false, message = " api " + endpoint + "  e " + e.message, message2 = "  Exception : " + endpoint + "  api  , something went wrong "))

        }

    }

}