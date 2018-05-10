package com.revern.yesnowtf.ui.base.rc_view

import android.view.View

interface OnRcvItemClickListener<T> {
    fun onItemClick(view: View, item: T)
}
