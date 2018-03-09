package com.revern.yesnowtf.api

import com.revern.yesnowtf.model.entity.YesNoResponse
import com.revern.yesnowtf.BuildConfig
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(BuildConfig.API_BASE_URL) fun  take(@Query("force") value: String): Flowable<YesNoResponse>

}