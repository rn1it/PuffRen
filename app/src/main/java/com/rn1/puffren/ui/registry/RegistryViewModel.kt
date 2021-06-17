package com.rn1.puffren.ui.registry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.User
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.*
import kotlinx.coroutines.launch

class RegistryViewModel(val repository: PuffRenRepository): ViewModel() {

    val email = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val connection = MutableLiveData<String>()

    private val _passRegistryCheck = MutableLiveData<Boolean>()
    val passRegistryCheck: LiveData<Boolean>
        get() = _passRegistryCheck

    private val _invalidInfo = MutableLiveData<Int>()
    val invalidInfo: LiveData<Int>
        get() = _invalidInfo

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

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

    fun registry(){

            viewModelScope.launch {

                val user = User(
                    email = email.value,
                    userName = nickname.value,
                    password = password.value,
                    confirmPassword = confirmPassword.value,
                    connection = connection.value
                )

                when(val result = repository.registry(user)){

                    is DataResult.Success -> {
                        val token = result.data
                        Logger.d("註冊成功: token= $token")
                        UserManager.userToken = result.data.accessToken
                        getUserByToken(result.data.accessToken!!)
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

    fun checkRegistryInfo(){
        when {
            email.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_EMAIL_EMPTY
            nickname.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_NAME_EMPTY
            password.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_PASSWORD_EMPTY
            confirmPassword.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_PASSWORD_CONFIRM_EMPTY
            password.value != confirmPassword.value -> _invalidInfo.value = INVALID_FORMAT_PASSWORD_CONFIRM
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

}