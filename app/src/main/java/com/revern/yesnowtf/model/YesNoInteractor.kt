package com.revern.yesnowtf.model

import android.os.Environment
import com.revern.yesnowtf.api.Api
import com.revern.yesnowtf.model.entity.YesNoResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.io.File
import okio.Okio
import java.io.IOException


class YesNoInteractor(private val api: Api) {

    fun take(type: String): Flowable<YesNoResponse> = api.take(type)

    fun downloadGif(gifUrl: String): Flowable<File> = api.downloadFile(gifUrl).flatMap { response ->
        Flowable.create<File>({ subscriber ->
            try {
                val fileName = gifUrl.replaceBeforeLast('/', "")
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile, fileName)
                val sink = Okio.buffer(Okio.sink(file))
                sink.writeAll(response.body()!!.source())
                sink.close()
                subscriber.onNext(file)
                subscriber.onComplete()
            } catch (e: IOException) {
                subscriber.onError(e)
            } catch (e: KotlinNullPointerException) {
                subscriber.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }

}