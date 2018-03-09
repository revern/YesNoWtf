package com.revern.yesnowtf.ui.main

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import com.bumptech.glide.Glide
import com.revern.yesnowtf.di.di
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.view.clicks
import com.revern.yesnowtf.R
import com.revern.yesnowtf.ui.base.BaseScreen

class MainScreen : BaseScreen<MainPM>() {

    @BindView(R.id.progress) lateinit var uiProgress: ProgressBar
    @BindView(R.id.gif) lateinit var uiGif: ImageView
    @BindView(R.id.random) lateinit var uiRandom: FloatingActionButton
    @BindView(R.id.yes) lateinit var uiYes: FloatingActionButton
    @BindView(R.id.no) lateinit var uiNo: FloatingActionButton
    @BindView(R.id.share) lateinit var uiShare: FloatingActionButton

    override val screenLayout = R.layout.screen_main

    override fun providePresentationModel(): MainPM = di.instance()

    override fun initViews() {
        super.initViews()

        uiShare.backgroundTintList = ColorStateList
                .valueOf(activity!!.resources.getColor(R.color.colorAccentDark))
    }

    override fun onBindPresentationModel(pm: MainPM) {
        super.onBindPresentationModel(pm)



        uiRandom.clicks().bindTo(pm.randomClick.consumer)
        uiYes.clicks().bindTo(pm.yesClick.consumer)
        uiNo.clicks().bindTo(pm.noClick.consumer)
        uiShare.clicks().bindTo(pm.shareClick.consumer)

        pm.showGif.observable.bindTo { showGif(it) }
        pm.share.observable.bindTo { share(it) }

    }

    private fun showGif(gifUrl: String) {
        Glide.with(activity!!).asGif().load(gifUrl).into(uiGif)
    }

    private fun share(gifUrl: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, gifUrl)
        intent.type = "text/plain"
        startActivity(intent)
    }

}
