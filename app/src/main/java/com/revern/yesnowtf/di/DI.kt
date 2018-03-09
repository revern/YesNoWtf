package com.revern.yesnowtf.di

import android.app.Application
import com.revern.yesnowtf.di.modules.appModule
import com.revern.yesnowtf.di.modules.netModule
import com.revern.yesnowtf.model.YesNoInteractor
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.revern.yesnowtf.BuildConfig
import com.revern.yesnowtf.ui.main.MainPM

lateinit var di: Kodein

fun initKodeinDI(app: Application) {
    di = Kodein {
        import(appModule(app))
        import(netModule(BuildConfig.API_BASE_URL))
        bind<YesNoInteractor>() with singleton { YesNoInteractor(instance()) }
        bind<MainPM>() with singleton { MainPM(instance()) }
    }
}