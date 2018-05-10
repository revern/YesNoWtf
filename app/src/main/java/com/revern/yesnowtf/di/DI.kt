package com.revern.yesnowtf.di

import android.app.Application
import com.bluelinelabs.conductor.Router
import com.revern.yesnowtf.di.modules.appModule
import com.revern.yesnowtf.di.modules.netModule
import com.revern.yesnowtf.model.YesNoModel
import com.revern.yesnowtf.BuildConfig
import com.revern.yesnowtf.ui.main.MainPM
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

lateinit var di: Kodein

fun initKodeinDI(app: Application) {
    di = Kodein {
        import(appModule(app))
        import(netModule(BuildConfig.API_BASE_URL))
        bind<YesNoModel>() with singleton { YesNoModel(instance()) }
        bind<MainPM>() with factory { router: Router -> MainPM(router, instance()) }
    }
}
