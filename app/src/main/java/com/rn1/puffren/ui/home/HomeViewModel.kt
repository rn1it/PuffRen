package com.rn1.puffren.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.HomePageItem
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PuffRenRepository) : ViewModel() {

    private val _homePageItems = MutableLiveData<List<HomePageItem>>()
    val homePageItem: LiveData<List<HomePageItem>>
        get() = _homePageItems

    private val _navigateToLocation =  MutableLiveData<Boolean>()
    val navigateToLocation: LiveData<Boolean>
        get() = _navigateToLocation

    private val _navigateToItem =  MutableLiveData<Boolean>()
    val navigateToItem: LiveData<Boolean>
        get() = _navigateToItem

    private val _navigateToProfile =  MutableLiveData<Boolean>()
    val navigateToProfile: LiveData<Boolean>
        get() = _navigateToProfile

    private val _navigateToFoodCar =  MutableLiveData<Boolean>()
    val navigateToFoodCar: LiveData<Boolean>
        get() = _navigateToFoodCar

    private val _navigateToQrCode =  MutableLiveData<Boolean>()
    val navigateToQrCode: LiveData<Boolean>
        get() = _navigateToQrCode

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

        getHomePageItem()
    }

    private fun getHomePageItem(){

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getHomePageItem()
            _homePageItems.value = when(result){
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is DataResult.Fail -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.error
                    null
                }
                is DataResult.Error -> {
                    _status.value = LoadApiStatus.ERROR
                    _error.value = result.exception.toString()
                    null
                }
            }
        }
    }

    fun navigate(homePageItem: HomePageItem){
        when(homePageItem.entry) {
            0 -> {
                navigateToLocation()
            }
            1 -> {
                navigateToItem()
            }
            2 -> {
                navigateToProfile()
            }
            3 -> {
                navigateToFoodCar()
            }
            4 -> {
                navigateToQrCode()
            }
        }
    }

    private fun navigateToLocation(){
        _navigateToLocation.value = true
    }

    fun doneNavigateToLocation(){
        _navigateToLocation.value = null
    }

    private fun navigateToItem(){
        _navigateToItem.value = true
    }

    fun doneNavigateToItem(){
        _navigateToItem.value = null
    }

    private fun navigateToProfile(){
        _navigateToProfile.value = true
    }

    fun navigateToProfileDone(){
        _navigateToProfile.value = null
    }

    private fun navigateToFoodCar(){
        _navigateToFoodCar.value = true
    }

    fun navigateToFoodCarDone(){
        _navigateToFoodCar.value = null
    }

    private fun navigateToQrCode(){
        _navigateToQrCode.value = true
    }

    fun navigateToQrCodeDone(){
        _navigateToQrCode.value = null
    }
}