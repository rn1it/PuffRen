package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.data.EntryFrom
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.login.LoginViewModel
import com.rn1.puffren.ui.registry.RegistryViewModel

@Suppress("UNCHECKED_CAST")
class EntryViewModelFactory(
    private val puffRenRepository: PuffRenRepository,
    private val entryFrom: EntryFrom
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>)=
        with(modelClass) {
            when {
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(puffRenRepository, entryFrom)

                isAssignableFrom(RegistryViewModel::class.java) ->
                    RegistryViewModel(puffRenRepository, entryFrom)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}