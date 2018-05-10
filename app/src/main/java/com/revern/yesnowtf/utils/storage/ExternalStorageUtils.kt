package com.revern.yesnowtf.utils.storage

import android.os.Environment
import okhttp3.ResponseBody
import okio.Okio
import retrofit2.Response
import java.io.File

class ExternalStorageUtils {

    companion object {
        private const val APP_DIR_NAME = "YesNoWtf"
        private const val YES_DIR_NAME = "Yes"
        private const val NO_DIR_NAME = "No"
        private const val ELSE_DIR_NAME = "Else"

        fun saveGif(gifUrl: String, response: Response<ResponseBody>): File {
            val appDir = getDirectory(Environment.getExternalStorageDirectory(), APP_DIR_NAME)
            val subDir = getDirectory(appDir.absoluteFile, getSubDirName(gifUrl))
            val fileName = gifUrl.replaceBeforeLast('/', "")
            val file = File(subDir.absoluteFile, fileName)
            val sink = Okio.buffer(Okio.sink(file))
            response.body()?.let {
                sink.writeAll(it.source())
                sink.close()
            }
            return file
        }

        private fun getDirectory(rootDir: File, dirName: String): File {
            val dir = File(rootDir, dirName)
            if (!dir.exists()) dir.mkdir()
            return dir
        }

        private fun getSubDirName(url: String): String =
                when {
                    url.contains("/yes/") -> YES_DIR_NAME
                    url.contains("/no/") -> NO_DIR_NAME
                    else -> ELSE_DIR_NAME
                }
    }

}
