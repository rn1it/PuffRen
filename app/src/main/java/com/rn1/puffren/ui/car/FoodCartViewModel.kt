package com.rn1.puffren.ui.car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.*
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.*
import kotlinx.coroutines.launch

class FoodCartViewModel(
    val repository: PuffRenRepository
): ViewModel() {

    private val _foodCartResult = MutableLiveData<FoodCartResult>()
    val foodCartResult: LiveData<FoodCartResult>
        get() = _foodCartResult

    private val _navigateToFoodCartContent = MutableLiveData<Boolean>()
    val navigateToFoodCartContent: LiveData<Boolean>
        get() = _navigateToFoodCartContent

    val items = MutableLiveData<List<ReportItem>>()

    val date = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val orderName = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    private val _invalidInfo = MutableLiveData<Int>()
    val invalidInfo: LiveData<Int>
        get() = _invalidInfo

    private val _passCheck = MutableLiveData<Boolean>()
    val passCheck: LiveData<Boolean>
        get() = _passCheck

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

        getFoodCart()
    }

    private fun getFoodCart(){

        viewModelScope.launch {

            val loginResult = LoginResult(userId = UserManager.userId)

            _foodCartResult.value = when (val result = repository.getFoodCart(loginResult)) {
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE

                    createFoodSetList(result.data.foodCartSets)
                    result.data
                }
                is DataResult.Error -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value =  result.exception.toString()
                    null
                }
                is DataResult.Fail -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.error
                    null
                }
            }
        }
    }

    private fun createFoodSetList(foodCartSets: List<FoodCartSet>) {
        val list = foodCartSets.map {
            ReportItem(id = it.id, title = it.title, price = it.price)
        }
        items.value = list
    }

    fun plusItem(reportItem: ReportItem) {
        val list = items.value!!.toMutableList()
        list.remove(reportItem)
        reportItem.quantity += 1
        list.add(reportItem)
        list.sortBy { it.id }
        items.value = list
    }

    fun minusItem(reportItem: ReportItem) {
        val list = items.value!!.toMutableList()
        list.remove(reportItem)
        if (reportItem.quantity > 0) {
            reportItem.quantity -= 1
        }
        list.add(reportItem)
        list.sortBy { it.id }
        items.value = list
    }

    fun checkInputInfo() {
        var isSelectItem = false

        for (item in items.value!!) {
            if (item.quantity > 0) {
                isSelectItem = true
                break
            }
        }

        when {
            date.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_DATE_EMPTY
            address.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_ADDRESS_EMPTY
            orderName.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_NAME_EMPTY
            phone.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_PHONE_EMPTY
            email.value.isNullOrEmpty() -> _invalidInfo.value = INVALID_EMAIL_EMPTY
            !isSelectItem ->  _invalidInfo.value = INVALID_ITEM_EMPTY

            else -> {
                _passCheck.value = true
            }
        }
    }

    fun cleanInvalidInfo() {
        _invalidInfo.value = null
    }

    fun sendOrder() {
        Logger.d("訂單成立!")
        //TODO wait for api
    }

    fun navigateToFoodCartContent() {
        _navigateToFoodCartContent.value = true
    }

    fun navigateToFoodCartContentDone() {
        _navigateToFoodCartContent.value = null
    }
}