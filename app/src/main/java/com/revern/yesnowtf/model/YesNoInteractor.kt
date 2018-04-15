package com.revern.yesnowtf.model

import com.revern.yesnowtf.api.Api
import com.revern.yesnowtf.model.entity.YesNoResponse
import com.revern.yesnowtf.utils.storage.ExternalStorageUtils
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.io.File
import java.io.IOException

class YesNoInteractor(private val api: Api) {

    fun take(type: String): Flowable<YesNoResponse> = api.take(type)

    fun downloadGif(gifUrl: String): Flowable<File> = api.downloadFile(gifUrl).flatMap { response ->
        Flowable.create<File>({ subscriber ->
            try {
                subscriber.onNext(ExternalStorageUtils.saveGif(gifUrl, response))
                subscriber.onComplete()
            } catch (e: IOException) {
                subscriber.onError(e)
            } catch (e: KotlinNullPointerException) {
                subscriber.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }

}