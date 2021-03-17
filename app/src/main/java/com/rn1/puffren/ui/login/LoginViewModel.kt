package com.rn1.puffren.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.source.PuffRenRepository

class LoginViewModel(val repository: PuffRenRepository) : ViewModel() {

    val email = MutableLiveData<String>()

    val password = MutableLiveData<String>()



}