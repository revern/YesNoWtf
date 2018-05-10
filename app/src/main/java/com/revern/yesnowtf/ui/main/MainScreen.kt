package com.revern.yesnowtf.ui.main

import android.content.Intent
import android.support.v4.content.FileProvider
import android.view.View
import com.bumptech.glide.Glide
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.view.clicks
import com.revern.yesnowtf.R
import com.revern.yesnowtf.di.di
import com.revern.yesnowtf.ui.base.BaseScreen
import kotlinx.android.synthetic.main.screen_main.view.*
import java.io.File

class MainScreen : BaseScreen<MainPM>() {

    override val screenLayout = R.layout.screen_main

    override fun providePresentationModel(): MainPM = MainPM(di.instance())

    override fun onBindPresentationModel(pm: MainPM) {
        super.onBindPresentationModel(pm)

        view?.let {
            it.fab_random.clicks() bindTo pm.randomClick.consumer
            it.fab_yes.clicks() bindTo pm.yesClick.consumer
            it.fab_no.clicks() bindTo pm.noClick.consumer
            it.fab_share.clicks() bindTo pm.shareClick.consumer
        }

        pm.inProgress.observable bindTo { showProgress(it) }
        pm.gif.observable bindTo { showGif(it) }
        pm.share.observable bindTo { share(it) }
    }

    private fun showProgress(inProgress: Boolean) {
        view?.let {
            if (inProgress) {
                it.progress_bar.visibility = View.VISIBLE
                it.iv_gif.visibility = View.GONE
            } else {
                it.progress_bar.visibility = View.GONE
                it.iv_gif.visibility = View.VISIBLE
            }
        }
    }

    private fun showGif(gifFile: File) {
        view?.let {
            Glide.with(it.context)
                    .asGif()
                    .load(gifFile)
                    .into(it.iv_gif)
        }
    }

    private fun share(gif: File) {
        activity?.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/gif"
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(it,
                    it.applicationContext.packageName + ".my.package.name.provider", gif))
            startActivity(intent)
        }
    }

}
