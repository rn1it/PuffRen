package com.rn1.puffren.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Login
import com.rn1.puffren.data.User
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: PuffRenRepository) : ViewModel() {

    val email = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun login(){
        if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
            viewModelScope.launch {

                val login = Login(email.value, password.value)

                when(val result = repository.login(login)){

                    is DataResult.Success -> {
                        val token = result.data.accessToken!!
                        Logger.d("登入成功: token= $token")
                        UserManager.userToken = token
                        getUserByToken(token)
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

    private fun getUserByToken(token: String){

        viewModelScope.launch {

            when(val result = repository.getLoginUser(token)){

                is DataResult.Success -> {
                    val user = result.data
                    Logger.d("登入使用者: $user")
                    _user.value = user
                }

                is DataResult.Fail -> {
                    Logger.d("Fail")
                }

                is DataResult.Error -> {
                    Logger.d("Error")
                }
            }
        }
    }

    fun navigateToProfileDone(){
        _user.value = null
    }

}