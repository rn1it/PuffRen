package com.rn1.puffren.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.Logger

class EditMembershipViewModel(
    val repository: PuffRenRepository
) : ViewModel() {

    private val _navigateToEditPassword = MutableLiveData<Boolean>()
    val navigateToEditPassword: LiveData<Boolean>
        get() = _navigateToEditPassword

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    fun navigateToEditPassword(){
        _navigateToEditPassword.value = true
    }

    fun navigateToEditPasswordDone(){
        _navigateToEditPassword.value = null
    }

}