package com.jpndev.niravu.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.jpndev.niravu.MQuiz
import com.jpndev.niravu.NiravuActivity
import com.jpndev.niravu.R
import com.jpndev.niravu.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.activity_detail5.*
import kotlinx.android.synthetic.main.activity_quiz_result.*
import kotlinx.android.synthetic.main.activity_quiz_result.close_dimv

class QuizResultActivity : NiravuActivity() {

    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)
      //  appViewModel.appRepository.mld_quiz_answered.postValue( 0)

    }
    private lateinit var home_url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)
        gradientChart.chartValues = arrayOf(
            10f, 30f, 25f, 32f, 13f, 5f, 18f, 36f, 20f, 30f, 28f, 27f, 29f
        )
        initAppViewModel()
        setViews( appViewModel.appRepository.mld_quiz_data.value)
        setClickS()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        onBackFinish()
    }

    private fun setViews(items: ArrayList<MQuiz>?) {
        var unanswered: Int=0
        var correct_answered: Int=0
        var in_correct_answered: Int=0
        items?.let{
            for( i in 0..it.size-1)
            {
                if(items.get(i).your_answer?.isNotEmpty()?:false)
                {
                    if( items.get(i).your_answer?.equals(items.get(i).correct_answer,true)?:false)
                    {
                        correct_answered++
                    }else{
                        in_correct_answered++
                    }
                }
                else
                {
                    unanswered++
                }



            }
        }

        defSetText(answer_value_txv,correct_answered)
        defSetText(unanswer_value_txv,unanswered)
        defSetText(wrong_answer_value_txv,in_correct_answered)
        var score:Double=0.0
        score= (correct_answered-(in_correct_answered.toDouble()/3)).toDouble()

        defSetText(score_value_txv,  if(score<0)0 else score.roundTo(2))


    }

    private fun setClickS() {
        view_answer_txv.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@QuizResultActivity, QuizViewAnswersActivity::class.java)
           // intent.putExtra(Intent.EXTRA_TEXT, item.detail_url)
            startActivity(intent)

        })
        close_dimv.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }

    override fun onDismiss(obj: Any?) {
        TODO("Not yet implemented")
    }
}
