package com.rn1.puffren.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Login
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(val repository: PuffRenRepository) : ViewModel() {

    val email = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var viewModelJob = Job()

    private val coroutineScope = viewModelScope



    fun login(){
        if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
            coroutineScope.launch {

                val login = Login(email.value, password.value)

                when(val result = repository.login(login)){

                    is DataResult.Success -> {
                        Logger.d("${result.data.message}")
                    }

                    is DataResult.Fail -> {
                        Logger.d("Fail")
                    }

                    is DataResult.Error -> {
                        Logger.d("Error")
                    }
                }
            }
        } else {
            Logger.d("loginCheck fail")
        }
    }

}