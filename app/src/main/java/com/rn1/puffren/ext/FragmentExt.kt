package com.rn1.puffren.ext

import androidx.fragment.app.Fragment
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory{
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return ViewModelFactory(repository)
}