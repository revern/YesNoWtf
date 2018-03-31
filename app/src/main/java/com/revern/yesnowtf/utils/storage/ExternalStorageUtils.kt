package com.revern.yesnowtf.utils.storage

import android.os.Environment
import okhttp3.ResponseBody
import okio.Okio
import retrofit2.Response
import java.io.File

class ExternalStorageUtils {

    companion object {

        private const val APP_NAME = "YesNoWtf"

        fun saveGif(gifUrl: String, response: Response<ResponseBody>): File {
            val appDir = File(Environment.getExternalStorageDirectory(), APP_NAME)
            val fileName = gifUrl.replaceBeforeLast('/', "")
            val file = File(Environment.getExternalStorageDirectory().absoluteFile, fileName)
            val sink = Okio.buffer(Okio.sink(file))
            sink.writeAll(response.body()!!.source())
            sink.close()
            return file
        }

    }

}