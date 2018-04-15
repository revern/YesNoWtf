package com.revern.yesnowtf.ui.main

import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import com.bumptech.glide.Glide
import com.revern.yesnowtf.di.di
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.view.clicks
import com.revern.yesnowtf.R
import com.revern.yesnowtf.ui.base.BaseScreen
import java.io.File

class MainScreen : BaseScreen<MainPM>() {

    @BindView(R.id.progress)
    lateinit var uiProgress: ProgressBar
    @BindView(R.id.gif)
    lateinit var uiGif: ImageView
    @BindView(R.id.random)
    lateinit var uiRandom: FloatingActionButton
    @BindView(R.id.yes)
    lateinit var uiYes: FloatingActionButton
    @BindView(R.id.no)
    lateinit var uiNo: FloatingActionButton
    @BindView(R.id.share)
    lateinit var uiShare: FloatingActionButton

    override val screenLayout = R.layout.screen_main

    override fun providePresentationModel(): MainPM = di.instance()

    override fun onBindPresentationModel(pm: MainPM) {
        super.onBindPresentationModel(pm)

        uiRandom.clicks().bindTo(pm.randomClick.consumer)
        uiYes.clicks().bindTo(pm.yesClick.consumer)
        uiNo.clicks().bindTo(pm.noClick.consumer)
        uiShare.clicks().bindTo(pm.shareClick.consumer)

        pm.inProgress.observable bindTo { showProgress(it) }
        pm.showGif.observable.bindTo { showGif(it) }
        pm.share.observable.bindTo { share(it) }
    }

    private fun showProgress(inProgress: Boolean) {
        if (inProgress) {
            uiProgress.visibility = View.VISIBLE
            uiGif.visibility = View.GONE
        } else {
            uiProgress.visibility = View.GONE
            uiGif.visibility = View.VISIBLE
        }
    }

    private fun showGif(gifFile: File) {
        activity?.let {
            Glide.with(it).asGif().load(gifFile).into(uiGif)
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
