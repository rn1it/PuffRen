package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.MainViewModel
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.cart.CartViewModel
import com.rn1.puffren.ui.home.HomeViewModel
import com.rn1.puffren.ui.login.LoginViewModel
import com.rn1.puffren.ui.registry.RegistryViewModel
import com.rn1.puffren.ui.report.ReportViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: PuffRenRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(repository)

                isAssignableFrom(RegistryViewModel::class.java) ->
                    RegistryViewModel(repository)

                isAssignableFrom(ReportViewModel::class.java) ->
                    ReportViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}