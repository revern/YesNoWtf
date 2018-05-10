package com.revern.yesnowtf.ui.main

import com.bluelinelabs.conductor.Router
import com.revern.yesnowtf.model.YesNoModel
import com.revern.yesnowtf.model.entity.Type
import com.revern.yesnowtf.ui.base.BasePM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File

class MainPM(private val router: Router, private val model: YesNoModel) : BasePM() {

    val inProgress = State(false)
    val gif = State<File>()

    val randomClick = Action<Unit>()
    val yesClick = Action<Unit>()
    val noClick = Action<Unit>()
    val shareClick = Action<Unit>()

    val share = Command<File>(bufferSize = 0)

    override fun onCreate() {
        super.onCreate()

        randomClick.observable.subscribe { loadGif(Type.RANDOM) }.untilDestroy()
        yesClick.observable.subscribe { loadGif(Type.YES) }.untilDestroy()
        noClick.observable.subscribe { loadGif(Type.No) }.untilDestroy()
        shareClick.observable.subscribe { share.consumer.accept(gif.value) }.untilDestroy()

        loadGif(Type.RANDOM)
    }

    private fun loadGif(type: Type) {
        model.take(type.type)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { inProgress.consumer.accept(true) }
                .doOnTerminate { inProgress.consumer.accept(false) }
                .flatMap { model.downloadGif(it.image) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    gif.consumer.accept(it)
                }, onError = {
                    errors.consumer.accept(it.message)
                })
    }

}
