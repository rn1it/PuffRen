package com.rn1.puffren.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.CityMain
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.UpdateUserResult
import com.rn1.puffren.data.User
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.*
import kotlinx.coroutines.launch

class EditMembershipViewModel(
    val repository: PuffRenRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    val nickname = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val spinnerPosition = MutableLiveData<Int>()
    val address = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val birthday = MutableLiveData<String>()
    val isReadUserPrivacy = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val _updateUserResult = MutableLiveData<UpdateUserResult>()
    val updateUserResult: LiveData<UpdateUserResult>
        get() = _updateUserResult

    private val _passCheck = MutableLiveData<Boolean>()
    val passCheck: LiveData<Boolean>
        get() = _passCheck

    private val _invalidInfo = MutableLiveData<Int>()
    val invalidInfo: LiveData<Int>
        get() = _invalidInfo

    private val _navigateToEditPassword = MutableLiveData<Boolean>()
    val navigateToEditPassword: LiveData<Boolean>
        get() = _navigateToEditPassword

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getUserProfile()
    }

    private fun getUserProfile(){
        viewModelScope.launch {

            when(val result = repository.getLoginUser(UserManager.userToken!!)){

                is DataResult.Success -> {
                    val user = result.data
                    _user.value = user
                    nickname.value = user.userName
                    fullName.value = user.fullName
                    address.value = user.address
                    phone.value = user.phone
                    address.value = user.address
                    birthday.value = user.birthDate

                    city.value = user.city
                    if (null != user.city) {
                        spinnerPosition.value = getCitySpinnerPosition(user.city)
                    }
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

    private fun getCitySpinnerPosition(city: String): Int {
        val cities = CityMain.values()
        cities.forEachIndexed { index, cityMain ->
            if (city == cityMain.title) {
                return index
            }
        }
        return 0
    }

    fun checkInputInfo() {
        when {
            nickname.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_FORMAT_NICKNAME_EMPTY
            isReadUserPrivacy.value == false ->  _invalidInfo.value = INVALID_NOT_READ_USER_PRIVACY
            else -> {
                _passCheck.value = true
            }
        }
    }

    fun cleanInvalidInfo() {
        _invalidInfo.value = null
    }

    fun updateUser() {
        viewModelScope.launch {

            val user = User(
                userName = nickname.value,
                fullName = fullName.value,
                city = city.value,
                address = address.value,
                phone = phone.value,
                birthDate = birthday.value
            )

            _updateUserResult.value = when(val result = repository.updateUser(UserManager.userToken!!, user)){

                is DataResult.Success -> {
                    result.data
                }

                is DataResult.Fail -> {
                    Logger.d("Fail")
                    null
                }

                is DataResult.Error -> {
                    Logger.d("Error")
                    null
                }
            }
        }
    }

    fun navigateToEditPassword(){
        _navigateToEditPassword.value = true
    }

    fun navigateToEditPasswordDone(){
        _navigateToEditPassword.value = null
    }

}