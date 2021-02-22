package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: PuffRenRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {

                isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}