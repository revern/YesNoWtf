package com.revern.yesnowtf.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.github.salomonbrys.kodein.*
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.revern.yesnowtf.utils.storage.IStorage
import com.revern.yesnowtf.utils.storage.Storage

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