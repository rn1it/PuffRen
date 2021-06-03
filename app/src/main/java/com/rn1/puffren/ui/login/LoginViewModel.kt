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
import com.rn1.puffren.util.*
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: PuffRenRepository) : ViewModel() {

    val email = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _invalidInfo = MutableLiveData<Int>()
    val invalidInfo: LiveData<Int>
        get() = _invalidInfo

    private val _passRegistryCheck = MutableLiveData<Boolean>()
    val passRegistryCheck: LiveData<Boolean>
        get() = _passRegistryCheck

    private val _navigateToRegistry = MutableLiveData<Boolean>()
    val navigateToRegistry: LiveData<Boolean>
        get() = _navigateToRegistry

    private val _loginFail = MutableLiveData<Boolean>()
    val loginFail: LiveData<Boolean>
        get() = _loginFail

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    fun login(){
        if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
            viewModelScope.launch {

                val login = Login(email.value, password.value)

                when(val result = repository.login(login)){

                    is DataResult.Success -> {
                        val token = result.data.accessToken!!
                        Logger.d("登入成功: token= $token")
                        UserManager.userToken = token
                        UserManager.userId = result.data.userId
                        getUserByToken(token)
                    }

                    is DataResult.Fail -> {
                        _loginFail.value = true
                        Logger.d("Login Fail")
                    }

                    is DataResult.Error -> {
                        _loginFail.value = true
                        Logger.d("Login Error")
                    }
                }
            }
        } else {
            _loginFail.value = true
            Logger.d("loginCheck fail")
        }
    }

    fun clearLoginFail() {
        _loginFail.value = null
    }

    fun checkLoginInfo(){
        when {
            email.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_FORMAT_EMAIL_EMPTY
            password.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_FORMAT_PASSWORD_EMPTY
            else -> {
                _passRegistryCheck.value = true
            }
        }
    }

    fun cleanInvalidInfo() {
        _invalidInfo.value = null
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

    fun navigateToRegistry(){
        _navigateToRegistry.value = true
    }

    fun navigateToRegistryDone(){
        _navigateToRegistry.value = null
    }
}