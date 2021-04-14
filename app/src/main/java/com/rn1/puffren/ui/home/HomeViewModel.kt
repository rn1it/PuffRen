package com.rn1.puffren.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.HomePageItem
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PuffRenRepository) : ViewModel() {

    private val _homePageItems = MutableLiveData<List<HomePageItem>>()
    val homePageItem: LiveData<List<HomePageItem>>
        get() = _homePageItems

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _navigateToLocation =  MutableLiveData<Boolean>()
    val navigateToLocation: LiveData<Boolean>
        get() = _navigateToLocation

    private val _navigateToItem =  MutableLiveData<Boolean>()
    val navigateToItem: LiveData<Boolean>
        get() = _navigateToItem

    private val _navigateToProfile =  MutableLiveData<Boolean>()
    val navigateToProfile: LiveData<Boolean>
        get() = _navigateToProfile

    init {
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
                    _error.value = result.error
                    null
                }
                is DataResult.Error -> {
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
}