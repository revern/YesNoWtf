package com.revern.yesnowtf

import android.app.Application
import com.revern.yesnowtf.di.initKodeinDI
import com.revern.yesnowtf.utils.initializeStetho

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKodeinDI(this)
        initializeStetho(this)
    }

}
