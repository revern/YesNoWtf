package com.revern.yesnowtf.ui.base

import me.dmdev.rxpm.PresentationModel
import me.dmdev.rxpm.navigation.NavigationMessage

abstract class BasePM : PresentationModel() {

    val errors = Command<String>()

    protected fun sendMessage(message: NavigationMessage) {
        navigationMessages.consumer.accept(message)
    }

    protected fun showError(error: String?) {
        errors.consumer.accept(error ?: "Unknown error")
    }

}