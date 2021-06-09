package com.rn1.puffren.ext

import android.view.View

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.showOrHide(isValid: Boolean) {
    visibility = if (isValid) {
        View.VISIBLE
    } else {
        View.GONE
    }
}