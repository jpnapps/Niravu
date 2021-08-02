package com.jpndev.niravu

import android.app.Application
import com.jpndev.niravu.utility.LogUtils

class JpApplication : Application() {






    override fun onCreate() {
        super.onCreate()
        context = this

        LogUtils.LOGD("jithish", " App JpApplication ")




    }


    override fun onTerminate() {
        super.onTerminate()
        //  WatchTower.shutDown()
    }


    internal var foo: String ?=""
    fun saveFoo(foo: String) {
        this.foo = foo
    }

    public companion object {
        public var context: JpApplication? = null

    }
}
