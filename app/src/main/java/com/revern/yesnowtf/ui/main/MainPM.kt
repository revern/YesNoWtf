package com.revern.yesnowtf.ui.main

import com.revern.yesnowtf.model.YesNoInteractor
import com.revern.yesnowtf.model.entity.Type
import com.revern.yesnowtf.ui.base.BasePresentationModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPM(val interactor: YesNoInteractor) : BasePresentationModel() {

    lateinit var gif: String

    val randomClick = Action<Unit>()
    val yesClick = Action<Unit>()
    val noClick = Action<Unit>()
    val shareClick = Action<Unit>()

    val showGif = Command<String>(bufferSize = 1)
    val share = Command<String>(bufferSize = 0)

    override fun onCreate() {
        super.onCreate()

        randomClick.observable.subscribe {
            loadGif(Type.RANDOM)
        }.untilDestroy()
        yesClick.observable.subscribe {
            loadGif(Type.YES)
        }.untilDestroy()
        noClick.observable.subscribe {
            loadGif(Type.No)
        }.untilDestroy()
        shareClick.observable.subscribe {
            share.consumer.accept(gif)
        }

        loadGif(Type.RANDOM)
    }

    private fun loadGif(type: Type) {
        interactor.take(type.type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            gif = it.image
            showGif.consumer.accept(gif)
        }
    }

}