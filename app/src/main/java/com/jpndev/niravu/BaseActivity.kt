package com.jpndev.niravu

import android.view.View
import androidx.appcompat.app.AppCompatActivity

import androidx.constraintlayout.widget.Group
import com.jpndev.utilitylibrary.base.BaseAppCompactActivity

abstract class BaseActivity : BaseAppCompactActivity() {

    fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }
}