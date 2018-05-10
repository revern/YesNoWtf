package com.revern.yesnowtf.api

import com.revern.yesnowtf.model.entity.YesNoResponse
import com.revern.yesnowtf.BuildConfig
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface Api {

    @GET(BuildConfig.API_BASE_URL) fun take(@Query("force") value: String): Flowable<YesNoResponse>

    @Streaming @GET fun downloadFile(@Url fileUrl: String): Flowable<Response<ResponseBody>>

}
