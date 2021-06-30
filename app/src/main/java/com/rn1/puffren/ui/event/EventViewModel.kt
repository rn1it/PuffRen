package com.rn1.puffren.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.*
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.EVENT_SCRATCH_CARD
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch

class EventViewModel(
    val repository: PuffRenRepository
) : ViewModel() {

    private val _eventInfo = MutableLiveData<EventInfo>()
    val eventInfo: LiveData<EventInfo>
        get() = _eventInfo

    private val _prize = MutableLiveData<Prize>()
    val prize: LiveData<Prize>
        get() = _prize

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

    fun getEventInfo() {

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            _eventInfo.value = when (val result =
                repository.getEventInfo(UserManager.userToken!!, EVENT_SCRATCH_CARD)) {
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

    fun getPrizeByEventId() {

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING
            val eventId = _eventInfo.value!!.event!!.eventId!!

            _prize.value = when (val result =
                repository.getPrize(UserManager.userToken!!, EVENT_SCRATCH_CARD, eventId)) {
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