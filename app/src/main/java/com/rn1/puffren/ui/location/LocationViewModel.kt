package com.rn1.puffren.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.Day
import com.rn1.puffren.data.PartnerInfo
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import kotlinx.coroutines.launch

class LocationViewModel(
    val repository: PuffRenRepository
): ViewModel() {

    private val _partners = MutableLiveData<List<PartnerInfo>>()
    val partners: LiveData<List<PartnerInfo>>
        get() = _partners

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

        getPartnersInfoByDay(Day.TODAY)
    }

    fun getPartnersInfoByDay(day: Day){

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING
            _partners.value = when(val result = repository.getPartnersInfoByDay(day.value)) {
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
}