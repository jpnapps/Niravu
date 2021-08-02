package com.jpndev.niravu.gaming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jpndev.niravu.NiravuActivity
import com.jpndev.niravu.R

class Game1PlayActivity : NiravuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game1_play)
    }

    override fun onDismiss(obj: Any?) {
        TODO("Not yet implemented")
    }
}
