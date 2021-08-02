package com.jpndev.niravu.home

import android.os.Bundle
import com.jpndev.niravu.NiravuActivity
import com.jpndev.niravu.R
import kotlinx.android.synthetic.main.activity_quiz.view.*

class TempActivity : NiravuActivity() {
    override fun onDismiss(obj: Any?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp1)
    }

    override fun onStart() {
        super.onStart()
    }
    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }



    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}