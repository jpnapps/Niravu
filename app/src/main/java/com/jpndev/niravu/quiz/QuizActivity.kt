package com.jpndev.niravu.quiz

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.jpndev.niravu.*
import com.jpndev.niravu.base.WebActivitykt
import com.jpndev.niravu.interfaces.OnApiResponseListner
import com.jpndev.niravu.utility.ComingSoonDialog
import com.jpndev.niravu.utility.Constants
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface
import kotlinx.android.synthetic.main.activity_quiz.*
import xyz.sangcomz.chameleon.Chameleon
import java.util.concurrent.TimeUnit


class QuizActivity : NiravuActivity() {
    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)
        appViewModel.appRepository.mld_quiz_answered.postValue( 0)

    }
    private lateinit var home_url: String
    private  var duration: Long =60
  //  var span_count: Int =2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)


        home_url=intent.getStringExtra(Intent.EXTRA_TEXT)
        duration=intent.getLongExtra(Intent.EXTRA_REFERRER,20)
     //   span_count=intent.getIntExtra(Intent.EXTRA_COMPONENT_NAME,2)
        LogUtils.LOGD("quiz", "GA home_url  "+duration)
        LogUtils.LOGD("quiz_2", "GA duration  "+duration)
        initAppViewModel()

        setRCvManager()
        viewModelObserVer()
        setClicks()

    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    var timer:CountDownTimer?=null
    private fun setTimer(milisecond: Long, countdowninterval: Long) {

            timer= object:CountDownTimer(milisecond, countdowninterval) {
            override fun onTick(millisUntilFinished: Long) {




                timer_txv.setText( String.format("%02d:%02d",

                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60))
               // counter++
            }

            override fun onFinish() {
                timer_txv.setText("FINISH!!")
                if(!isFinishing)
                timeUpDialog()
            }
        }.start()

    }

    private fun setClicks() {

        submit_btn.setOnClickListener(View.OnClickListener {
            finishExam()




        })
        index_dimv.setOnClickListener(View.OnClickListener {
            if(qs_no_chameleon.isVisible)
                qs_no_chameleon.visibility=View.GONE
            else
                qs_no_chameleon.visibility=View.VISIBLE
        })
        timer_txv.setOnClickListener(View.OnClickListener {
if(BuildConfig.isShowApi) {

    var items: ArrayList<MQuiz> = ArrayList<MQuiz>()
    for (i in 0..99) {
        var choices: ArrayList<String> = ArrayList<String>()
        val choice: String = getRandomString(6)
        choices.add(getRandomString(10));

        choices.add(choice)
        choices.add(getRandomString(8))
        choices.add(getRandomString(7))
        var qs: String = getRandomString(5) + " " + getRandomString(3) + " " + getRandomString(5)
        for (k in 0..6) {
            var qs_temp: String = getRandomString(6)
            qs = qs + " " + qs_temp
        }

        var quiz: MQuiz = MQuiz(
            question = qs + " ?",
            correct_answer = choice,
            choices = choices,
            question_text_color_1 = null,
            id = i + 230,
            type = 200
        )

        items.add(quiz)
    }
    LogUtils.LOGD("quiz_2", "GA items size   " + items.size)
    LogUtils.LOGD("quiz_2", "GA ques 2  " + items?.get(2)?.question)
    LogUtils.LOGD("quiz_2", "GA ques 5  " + items?.get(5)?.question)
    /* val jsonArray = JsonArray(items)
            val jSONArray = JSONArray(items)*/
    val jsonArray: JsonArray = Gson().toJsonTree(items).getAsJsonArray()
    var jsontest2: String? = null
    jsontest2 = "" + jsonArray
    //showToastMessage("jsonArray ="+jsonArray)
    LogUtils.LOGD("quiz_2", "   " + jsonArray)
    var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clipData = ClipData.newPlainText("", jsontest2)
    clipboardManager?.setPrimaryClip(clipData)
    // jsontest=""
    showToastMessage("Copied Json data and cleared")
}


        })


    }

    private fun submitDialog() {
        val mBottomSheetDialog = BottomSheetMaterialDialog.Builder(this)
            .setTitle("Submit?")
            .setMessage("Finished answering,submit now? ")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialogInterface, which ->

                Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_SHORT)
                    .show()

                dialogInterface.dismiss()
            }
            .setNegativeButton(
                "Not Yet ",
                R.drawable.ic_close_white
            ) { dialogInterface, which ->
                Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT)
                    .show()
                dialogInterface.dismiss()
            }
            .build()


        mBottomSheetDialog.show()
    }


    private fun closeDialog() {
         mBottomSheetDialog?.let{
            it.dismiss()
        }
         mBottomSheetDialog = BottomSheetMaterialDialog.Builder(this)
            .setTitle("CONFIRM EXIT ")
            .setMessage("Are you sure you want to exit ?  \nYou will lose your progresss.")
            .setCancelable(true)
            .setPositiveButton(
                "Continue Exam "
            ) { dialogInterface, which ->

                dialogInterface.dismiss()

            }
            .setNegativeButton(
                "Exit "
            ) { dialogInterface, which ->

            /*    Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT)
                    .show()*/
                dialogInterface.dismiss()
                onBackFinish()
            }
            .build()

        val animationView: LottieAnimationView = mBottomSheetDialog!!.getAnimationView()
        animationView.visibility=View.VISIBLE
        animationView.scaleType=ImageView.ScaleType.CENTER_INSIDE
        animationView.setAnimationFromUrl("https://assets2.lottiefiles.com/packages/lf20_eaSxEJ.json")
        mBottomSheetDialog!!.show()
    }

    private fun timeUpDialog() {
        mBottomSheetDialog?.let{
            it.dismiss()
        }

         mBottomSheetDialog = BottomSheetMaterialDialog.Builder(this)

            .setTitle("Time up")
            .setMessage("Exam time finished")
            .setAnimation(R.raw.icn_waiting1)
            .setCancelable(false)
            .setPositiveButton(
                "Finish Exam "
            ) { dialogInterface, which ->

                finishExam(dialogInterface,2000)



            }
            .setNegativeButton(
                "Exit "
            ) { dialogInterface, which ->

                /*    Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT)
                        .show()*/
                dialogInterface.dismiss()
                onBackFinish()
            }
            .build()


        mBottomSheetDialog!!.show()
    }
    var mBottomSheetDialog  :  BottomSheetMaterialDialog?=null
    private fun lastQuestionFinishDialog(  ) {
        mBottomSheetDialog?.let{
            it.dismiss()
        }

         mBottomSheetDialog = BottomSheetMaterialDialog.Builder(this)

            //.setTitle("Time up")
            // .setMessage("Exam time finished")
            .setAnimation(R.raw.icn_waiting1)
            .setCancelable(false)
            .setPositiveButton(
                "Finish Exam "
            ) { dialogInterface, which ->

                finishExam(dialogInterface,2000)


            }
            .setNegativeButton(
                "Not Yet "
            ) { dialogInterface, which ->

                /*    Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT)
                        .show()*/
                dialogInterface.dismiss()
            }
            .build()
        mBottomSheetDialog?.let {
            val animationView: LottieAnimationView = it.getAnimationView()
            animationView.visibility = View.VISIBLE
            animationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            animationView.setAnimationFromUrl("https://assets10.lottiefiles.com/packages/lf20_rV3H6B.json")

            it.show()
        }
    }


    private fun finishExam(dialogInterface: DialogInterface?=null, time: Long=2000) {
        setLoading("Calculating Result...")
        showProgress()

        Handler().postDelayed(Runnable {
            dialogInterface?.dismiss()
            hideProgress()
            val intent = Intent(this@QuizActivity, QuizResultActivity::class.java)
            // intent.putExtra(Intent.EXTRA_TEXT, item.detail_url)
            startActivity(intent)
            finish()


        },time)
    }



    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
    override fun onBackPressed() {
        ///  super.onBackPressed()
        closeDialog()

        //onBackFinishAffinity()
    }
    override fun onDismiss(obj: Any?) {
    }
    private fun setRCvManager() {

val layoutManager  =  LinearLayoutManager(this@QuizActivity, LinearLayoutManager.HORIZONTAL, false);
        val snapHelper : SnapHelper =  PagerSnapHelper();
        rcv.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(rcv)



        LogUtils.LOGD("quiz", "GA setRCvManager   ")

        val mDivider: Drawable? = ContextCompat.getDrawable(this@QuizActivity, R.drawable.divider_1)
        mDivider?.let{
            val hItemDecoration = DividerItemDecoration(
                this@QuizActivity,
                DividerItemDecoration.VERTICAL
            )

            hItemDecoration.setDrawable(it)
            rcv.addItemDecoration(hItemDecoration)
        }




        val layoutManager_no  =  LinearLayoutManager(this@QuizActivity);
        qs_no_rcv.setLayoutManager(layoutManager_no)
        val mDivider_no: Drawable? = ContextCompat.getDrawable(this@QuizActivity, R.drawable.divider_1)
        mDivider_no?.let{
            val hItemDecoration = DividerItemDecoration(
                this@QuizActivity,
                DividerItemDecoration.VERTICAL
            )

            hItemDecoration.setDrawable(it)
            qs_no_rcv.addItemDecoration(hItemDecoration)
        }



    }
    private fun viewModelObserVer() {

        LogUtils.LOGD("quiz", "GA viewModelObserVer url=  "+home_url)
        refreshScreen(home_url )

        appViewModel.appRepository.mld_quiz_data.observe(this, Observer { items ->
            items?.let {

                chameleon.showState(Chameleon.STATE.CONTENT)

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
                    setTimer(duration*60000,1000)
                    if(obj.in_app)
                    {
                        // checkUpdate(obj)


                    }
                    else
                    {
                        obj.web_url?.let {
                            WebActivitykt.startWebActivity2(this@QuizActivity,obj.web_url,obj?.web_properties)

                        }?:let {
                            obj.browser_url?.let {

                                openBrowser( it)

                            }?:let {
                                ComingSoonDialog.showDialog(this@QuizActivity, MBaseDialog( Constants.COMING_SOON))

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
       // LogUtils.LOGD("quiz", "GA onSetAdapter size =  "+items?.size)
        adapter = QuizAdapter(items, this@QuizActivity,appViewModel, { mgame : MQuiz?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        rcv.setAdapter(adapter)

    }


   /* fun onSetAdapter() {
        LogUtils.LOGD("quiz", "GA onSetAdapter size =  "+arraylist?.size)
        adapter = QuizAdapter(arraylist, this@QuizActivity,appViewModel, { mgame : MQuiz?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        rcv.setAdapter(adapter)
        LogUtils.LOGD("quiz", "GA onSetQSNOAdapter size =  "+arraylist?.size)
        qsno_adapter = QuestionIndexAdapter(arraylist, this@QuizActivity,appViewModel, { mgame : MQuiz?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        qs_no_rcv.setAdapter(qsno_adapter)
    }*/

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
                else
                {
                    lastQuestionFinishDialog()
                }
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
        LogUtils.LOGD("quiz", "GA onSetQSNOAdapter size =  "+items?.size)
        qsno_adapter = QuestionIndexAdapter(items, this@QuizActivity,appViewModel, { mgame : MQuiz?, postion:Int, purpose:Int  -> partItemClicked(mgame,postion,purpose) })
        qs_no_rcv.setAdapter(qsno_adapter)
    }



}
