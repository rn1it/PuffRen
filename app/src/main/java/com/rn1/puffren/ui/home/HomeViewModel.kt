package com.rn1.puffren.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.HomePageItem
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PuffRenRepository) : ViewModel() {

    init {
        getHomePageImages()
    }

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

    private val _navigateToMember =  MutableLiveData<Boolean>()
    val navigateToMember: LiveData<Boolean>
        get() = _navigateToMember

    private fun getHomePageImages(){

        viewModelScope.launch {

            //TODO ?????????????????????
//            _status.value = LoadApiStatus.LOADING

            val result = repository.getHomePageItem()
            _homePageItems.value = when(result){
                is DataResult.Success -> {
          //TODO ?????????????????????
//                    _status.value = LoadApiStatus.DONE
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
                navigateToMember()
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

    private fun navigateToMember(){
        _navigateToMember.value = true
    }

    fun doneNavigateToMember(){
        _navigateToMember.value = null
    }


}