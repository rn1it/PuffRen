package com.rn1.puffren.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.source.PuffRenRepository

class HomeViewModel(repository: PuffRenRepository) : ViewModel() {

    private val _navigateToLocation =  MutableLiveData<Boolean>()
    val navigateToLocation: LiveData<Boolean>
        get() = _navigateToLocation

    private val _navigateToItem =  MutableLiveData<Boolean>()
    val navigateToItem: LiveData<Boolean>
        get() = _navigateToItem

    private val _navigateToMember =  MutableLiveData<Boolean>()
    val navigateToMember: LiveData<Boolean>
        get() = _navigateToMember


    fun navigateToLocation(){
        _navigateToLocation.value = true
    }

    fun doneNavigateToLocation(){
        _navigateToLocation.value = null
    }

    fun navigateToItem(){
        Log.d("aaa","navigateToItem")
        _navigateToItem.value = true
    }

    fun doneNavigateToItem(){
        _navigateToItem.value = null
    }

    fun navigateToMember(){
        _navigateToMember.value = true
    }

    fun doneNavigateToMember(){
        _navigateToMember.value = null
    }


}