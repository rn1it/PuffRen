package com.rn1.puffren.ext

import android.app.Activity
import android.view.View
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as PuffRenApplication).puffRenRepository
    return ViewModelFactory(repository)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}