package com.rn1.puffren.ui.report.sale

import androidx.databinding.InverseMethod
import androidx.lifecycle.*
import com.rn1.puffren.data.*
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch

class SaleReportViewModel(
    val repository: PuffRenRepository,
    private val argument: String
) : ViewModel() {

    private val itemMap = mutableMapOf<String, Int>()

    val openDate = MutableLiveData<String>().apply {
        value = argument
    }

    val saleAmount = MutableLiveData<Int>()

    private val _reportItems = MutableLiveData<List<ReportItem>>()
    val reportItems: LiveData<List<ReportItem>>
        get() = _reportItems

    private val _reportResult = MutableLiveData<ReportResult>()
    val reportResult: LiveData<ReportResult>
        get() = _reportResult

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

        getReportItem()
    }

    private fun getReportItem() {

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            _reportItems.value =
                when (val result = repository.getReportItems(UserManager.userToken!!)) {
                    is DataResult.Success -> {
                        _status.value = LoadApiStatus.DONE
                        result.data
                    }
                    is DataResult.Error -> {
                        _status.value = LoadApiStatus.ERROR
                        _error.value = result.exception.toString()
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

    fun setItemAmount(id: String, text: String) {
        val quantity: Int =
            try {
                text.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        itemMap[id] = quantity
    }

    fun reportSale(spinnerPosition: Int) {
        val weather = WeatherMain.values()[spinnerPosition].title

        val items = mutableListOf<ReportItem>()

        for (item in itemMap) {
            val reportItem = ReportItem(id = item.key, quantity = item.value)
            items.add(reportItem)
        }

        val reportDetail = ReportDetail(
            openDate = openDate.value,
            sales = saleAmount.value,
            weather = weather,
            items = items
        )

        viewModelScope.launch {

            _status.value = LoadApiStatus.LOADING

            _reportResult.value =
                when (val result = repository.reportSale(UserManager.userToken!!, reportDetail)) {
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

    // two way converter
    @InverseMethod("convertIntToString")
    fun convertStringToInt(value: String): Int {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun convertIntToString(value: Int): String {
        return value.toString()
    }


}