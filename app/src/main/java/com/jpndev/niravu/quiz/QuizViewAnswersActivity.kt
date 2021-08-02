package com.jpndev.niravu.quiz

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.jpndev.niravu.*
import com.jpndev.niravu.base.WebActivitykt
import com.jpndev.niravu.interfaces.OnApiResponseListner
import com.jpndev.niravu.utility.ComingSoonDialog
import com.jpndev.niravu.utility.Constants
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel

import kotlinx.android.synthetic.main.activity_quiz_view_answers.*
import xyz.sangcomz.chameleon.Chameleon
import java.util.concurrent.TimeUnit
class QuizViewAnswersActivity : NiravuActivity() {

  

    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)
        appViewModel.appRepository.mld_quiz_answered.postValue( 0)

    }
   // private lateinit var home_url: String
    //  var span_count: Int =2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_view_answers)

        LogUtils.LOGD("quiz", "QuizViewAnswer onCreate  ")
      //  home_url="15"
      //  home_url=intent.getStringExtra(Intent.EXTRA_TEXT)
        initAppViewModel()
        appViewModel.qs_view_mode=true
        setRCvManager()
        setViews( appViewModel.appRepository.mld_quiz_data.value)
        //viewModelObserVer()
        setClicks()

    }

    override fun onResume() {
        super.onResume()
        appViewModel.qs_view_mode=true
    }


    private fun setViews(items: ArrayList<MQuiz>?) {

        chameleon.showState(Chameleon.STATE.CONTENT)

        onSetAdapter(items)
        onSetQSNOAdapter(items)
        index_txv.setText( appViewModel.appRepository.mld_quiz_answered.value.toString()+"/"+(adapter?.items?.size?:0).toString())
    }
    private fun setClicks() {

        close_dimv.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

 
        index_dimv.setOnClickListener(View.OnClickListener {
            if(qs_no_chameleon.isVisible)
                qs_no_chameleon.visibility=View.GONE
            else
                qs_no_chameleon.visibility=View.VISIBLE
        })
        timer_txv.setOnClickListener(View.OnClickListener {


            var items: ArrayList<MQuiz> = ArrayList<MQuiz>()
            for (i in 0..99) {
                var choices: ArrayList<String> =ArrayList<String>()
                val choice: String=getRandomString(6)
                choices.add(getRandomString(10));

                choices.add(choice)
                choices.add(getRandomString(8))
                choices.add(getRandomString(7))
                var qs: String=getRandomString(5)+" "+getRandomString(3)+ " "+getRandomString(5)
                for (k in 0..6){
                    var qs_temp: String=getRandomString(6)
                    qs=qs+" "+qs_temp
                }

                var quiz:MQuiz= MQuiz(question=qs+" ?" ,correct_answer = choice,choices =choices,question_text_color_1 = null,id = i+230 ,type=200)

                items.add(quiz)
            }
            LogUtils.LOGD("quiz_2", "QuizViewAnswer items size   "+items.size)
            LogUtils.LOGD("quiz_2", "QuizViewAnswer ques 2  "+items?.get(2)?.question)
            LogUtils.LOGD("quiz_2", "QuizViewAnswer ques 5  "+items?.get(5)?.question)
            /* val jsonArray = JsonArray(items)
             val jSONArray = JSONArray(items)*/
            val jsonArray: JsonArray = Gson().toJsonTree(items).getAsJsonArray()
            var jsontest2:String?=null
            jsontest2=""+jsonArray
            //showToastMessage("jsonArray ="+jsonArray)
            LogUtils.LOGD("quiz_2", "   "+jsonArray)
            var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clipData = ClipData.newPlainText("", jsontest2)
            clipboardManager?.setPrimaryClip(clipData)
            // jsontest=""
            showToastMessage("Copied Json data and cleared")



        })


    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
    override fun onBackPressed() {
        appViewModel.qs_view_mode=false
        onBackFinish()
    }
    override fun onDismiss(obj: Any?) {
    }
    private fun setRCvManager() {
        

        val layoutManager  =  LinearLayoutManager(this@QuizViewAnswersActivity, LinearLayoutManager.HORIZONTAL, false);
        val snapHelper : SnapHelper =  PagerSnapHelper();
        rcv.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(rcv)



        LogUtils.LOGD("quiz", "QuizViewAnswer setRCvManager   ")

        val mDivider: Drawable? = ContextCompat.getDrawable(this@QuizViewAnswersActivity, R.drawable.divider_1)
        mDivider?.let{
            val hItemDecoration = DividerItemDecoration(
                this@QuizViewAnswersActivity,
                DividerItemDecoration.VERTICAL
            )

            hItemDecoration.setDrawable(it)
            rcv.addItemDecoration(hItemDecoration)
        }




        val layoutManager_no  =  LinearLayoutManager(this@QuizViewAnswersActivity);
        qs_no_rcv.setLayoutManager(layoutManager_no)
        val mDivider_no: Drawable? = ContextCompat.getDrawable(this@QuizViewAnswersActivity, R.drawable.divider_1)
        mDivider_no?.let{
            val hItemDecoration = DividerItemDecoration(
                this@QuizViewAnswersActivity,
                DividerItemDecoration.VERTICAL
            )

            hItemDecoration.setDrawable(it)
            qs_no_rcv.addItemDecoration(hItemDecoration)
        }


    }
    private fun viewModelObserVer() {

        //refreshScreen(home_url )

        appViewModel.appRepository.mld_quiz_data.observe(this, Observer { items ->
            items?.let {

                chameleon.showState(Chameleon.STATE.CONTENT)
                //  arraylist=items
                //onSetAdapter()
                onSetAdapter(items)
                onSetQSNOAdapter(items)
                index_txv.setText( appViewModel.appRepository.mld_quiz_answered.value.toString()+"/"+(adapter?.items?.size?:0).toString())
            } ?: let {
                chameleon.showState(Chameleon.STATE.EMPTY)
            }
        })
        index_txv.setText("0/"+(adapter?.items?.size?:0).toString())
        appViewModel.appRepository.mld_quiz_answered.observe(this, Observer { items ->
            items?.let {


                index_txv.setText(items.toString()+"/"+(adapter?.items?.size?:0).toString())
            } ?: let {

            }
        })
        /*   appViewModel.setBackGrounds(cly= top_clay)
           appViewModel.appRepository.s_pref.stringLiveData("app_object", "").observe(this,Observer { json ->
   
               if(isValid(json)){
                   var m_app: MApp? =getAppObject(json)
   
                   appViewModel.setBackGrounds(getAppObject(json),top_clay)
   
               }
   
           })*/


    }

    private fun updateCount(items: ArrayList<MQuiz>) {

    }

    fun refreshScreen(home_url: String ) {

        appViewModel.callQuizData(home_url, object: OnApiResponseListner {
            override fun onApiInit(obj: Any?) {
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
                            WebActivitykt.startWebActivity2(this@QuizViewAnswersActivity,obj.web_url,obj?.web_properties)

                        }?:let {
                            obj.browser_url?.let {

                                openBrowser( it)

                            }?:let {
                                ComingSoonDialog.showDialog(this@QuizViewAnswersActivity, MBaseDialog( Constants.COMING_SOON))

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
    var adapter : QuizAdapter? =null
    var arraylist: List<MQuiz>?=null
    fun onSetAdapter(items: List<MQuiz>?=null) {
        // LogUtils.LOGD("quiz", "QuizViewAnswer onSetAdapter size =  "+items?.size)
        adapter = QuizAdapter(items, this@QuizViewAnswersActivity,appViewModel, { mgame : MQuiz?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        rcv.setAdapter(adapter)
        
    }




    private fun partItemClicked(item:  MQuiz?,  postion:Int, purpose:Int=0) {
        LogUtils.LOGD("quiz_2", "QA partItemClicked  = "+postion)
        if(purpose==2)
        {
            if(postion!=-1) {
                /* qsno_adapter?.notifyDataSetChanged()
   
                   adapter?.notifyDataSetChanged()*/

                qsno_adapter?.notifyItemChanged(postion)
                adapter?.notifyItemChanged(postion)

                // if((item?.answered?:0)<=1)
                if(!(item?.is_answered?:false))
                    appViewModel.appRepository.mld_quiz_answered.postValue( appViewModel.appRepository.mld_quiz_answered.value?.plus(1))

                LogUtils.LOGD("quiz_2", "QA adapter size  = "+(adapter?.items?.size?:0).toString())
                //  postion=postion+1;
                if((adapter?.items?.size?:0 )>postion+1)
                    appViewModel.rcvScroll(rcv,postion+1,1000)
                //rcv?.getLayoutManager()?.smoothScrollToPosition(postion+1)
                if((qsno_adapter?.items?.size?:0 )>postion+1)
                    appViewModel.rcvScroll(qs_no_rcv,postion+1,500)
                // qs_no_rcv?.getLayoutManager()?.scrollToPosition(postion+1)

            }
            // qsno_adapter?.notifyDataSetChanged()
        }else {
            item?.let {
                rcv?.getLayoutManager()?.scrollToPosition(postion)
                // adapter?.notifyItemChanged(postion)
                when (it.type) {

                }

            }
        }
    }

    var qsno_adapter : QuestionIndexAdapter? =null
    fun onSetQSNOAdapter(items: List<MQuiz>?=null) {
        LogUtils.LOGD("quiz_2", "QuizViewAnswer onSetQSNOAdapter size =  "+items?.size)
        qsno_adapter = QuestionIndexAdapter(items, this@QuizViewAnswersActivity,appViewModel, { mgame : MQuiz?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        qs_no_rcv.setAdapter(qsno_adapter)
    }

}
