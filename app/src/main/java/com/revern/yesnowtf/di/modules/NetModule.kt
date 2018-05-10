package com.revern.yesnowtf.di.modules

import android.content.Context
import com.revern.yesnowtf.api.Api
import com.revern.yesnowtf.utils.addStethoInterceptors
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.io.File
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

fun netModule(baseUrl: String) = Kodein.Module {

    bind<File>() with singleton { instance<Context>().cacheDir }

    bind<OkHttpClient>() with singleton { createHttpClient(instance()) }

    bind<Api>() with singleton {
        Retrofit.Builder()
                .client(instance())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(instance()))
                .build()
                .create(Api::class.java) }
}

private fun createHttpClient(cachedDir: File): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
            .cache(Cache(cachedDir, 20 * 1024 * 1024))
            .readTimeout(30, TimeUnit.SECONDS)
    addStethoInterceptors(httpClientBuilder)
    return httpClientBuilder.build()
}
