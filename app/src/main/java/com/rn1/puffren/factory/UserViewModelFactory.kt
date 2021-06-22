package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.data.User
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.profile.ProfileViewModel
import com.rn1.puffren.ui.qrcode.QRCodeViewModel

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val puffRenRepository: PuffRenRepository,
                           private val user: User?
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>)=
        with(modelClass) {
            when {
                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(puffRenRepository, user)

                isAssignableFrom(QRCodeViewModel::class.java) ->
                    QRCodeViewModel(puffRenRepository, user)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}