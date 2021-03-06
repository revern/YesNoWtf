package com.revern.yesnowtf.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.revern.yesnowtf.utils.storage.IStorage
import com.revern.yesnowtf.utils.storage.Storage
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun appModule(application: Application) = Kodein.Module {

    bind<Context>() with singleton { application }

    bind<Gson>() with singleton {
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }

    bind<SharedPreferences>() with singleton {
        instance<Context>().getSharedPreferences(instance<Context>().packageName, Context.MODE_PRIVATE)
    }

    bind<IStorage>() with singleton { Storage(instance(), instance()) }

}
