package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.MainViewModel
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.coupon.CouponViewModel
import com.rn1.puffren.ui.edit.EditMembershipViewModel
import com.rn1.puffren.ui.history.HistoryViewModel
import com.rn1.puffren.ui.history.advance.AdvanceReportViewModel
import com.rn1.puffren.ui.history.item.ReportItemViewModel
import com.rn1.puffren.ui.home.HomeViewModel
import com.rn1.puffren.ui.location.LocationViewModel
import com.rn1.puffren.ui.login.LoginViewModel
import com.rn1.puffren.ui.performance.PerformanceViewModel
import com.rn1.puffren.ui.registry.RegistryViewModel
import com.rn1.puffren.ui.report.ReportViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: PuffRenRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(repository)

                isAssignableFrom(RegistryViewModel::class.java) ->
                    RegistryViewModel(repository)

                isAssignableFrom(ReportViewModel::class.java) ->
                    ReportViewModel(repository)

                isAssignableFrom(EditMembershipViewModel::class.java) ->
                    EditMembershipViewModel(repository)

                isAssignableFrom(HistoryViewModel::class.java) ->
                    HistoryViewModel(repository)

                isAssignableFrom(ReportItemViewModel::class.java) ->
                    ReportItemViewModel(repository)

                isAssignableFrom(LocationViewModel::class.java) ->
                    LocationViewModel(repository)

                isAssignableFrom(CouponViewModel::class.java) ->
                    CouponViewModel(repository)

                isAssignableFrom(PerformanceViewModel::class.java) ->
                    PerformanceViewModel(repository)

                isAssignableFrom(AdvanceReportViewModel::class.java) ->
                    AdvanceReportViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}