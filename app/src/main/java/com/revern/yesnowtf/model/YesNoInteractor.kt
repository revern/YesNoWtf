package com.revern.yesnowtf.model

import com.revern.yesnowtf.api.Api
import com.revern.yesnowtf.model.entity.YesNoResponse
import io.reactivex.Flowable

class YesNoInteractor(private val api: Api) {

    fun take(type: String): Flowable<YesNoResponse> = api.take(type)

}