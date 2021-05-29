package com.rn1.puffren.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.data.*
import com.rn1.puffren.databinding.FragmentReportBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.show

class ReportFragment : Fragment() {

    lateinit var binding: FragmentReportBinding
    val viewModel by viewModels<ReportViewModel> { getVmFactory() }

    private val todayAdapter = TodayReportAdapter()
    private val overdueAdapter = OverdueAdapter()
    var reportStatus : ReportStatus? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        testData()
        binding = FragmentReportBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // for test -----------------------
//        reportStatus?.let {
//            with(binding) {
//                // today
//                textTodayOpenDate.text = it.today.openDate
//                recyclerTodayReport.adapter = todayAdapter
//                todayAdapter.submitList(it.today.details)
//
//                //overdue
//                recyclerOverdueRecord.apply {
//                    show()
//                    adapter = overdueAdapter
//                }
//                overdueAdapter.submitList(it.overdueRecords)
//            }
//        }
        // --------------------------

        viewModel.reportStatus.observe(viewLifecycleOwner, Observer {
            it?.let {

                with(binding) {
                    // today
                    if (it.today.details.isNotEmpty()){
                        textTodayOpenDate.text = it.today.openDate
                        recyclerTodayReport.adapter = todayAdapter
                        todayAdapter.submitList(it.today.details)
                    }
                    //overdue
                    if(it.overdueRecords.isNotEmpty()) {
                        recyclerOverdueRecord.apply {
                            show()
                            adapter = overdueAdapter
                        }
                        overdueAdapter.submitList(it.overdueRecords)
                    }
                }
            }
        })
        return binding.root
    }

    private fun testData() {
        val detail1 = ReportOpenStatus(recordId = "31", openStatus = 2, openTime = "15:00", closeTime = "22:20", openLocation = "台北市大安區安和路二段161號台北市大安區安和路二段161號台北市大安區安和路二段161號")
        val detail2 = ReportOpenStatus(recordId = "32", openStatus = 1, openTime = "12:00", closeTime = "22:20", openLocation = "台北市大安區安和路二段162號台北市大安區安和路二段161號台北市大安區安和路二段161號")
        val detail3 = ReportOpenStatus(recordId = "313", openStatus = 2, openTime = "13:00", closeTime = "22:20", openLocation = "台北市大安區安和路二段163號台北市大安區安和路二段161號台北市大安區安和路二段161號")
        val detail4 = ReportOpenStatus(recordId = "314", openStatus = 1, openTime = "16:00", closeTime = "22:20", openLocation = "台北市大安區安和路二段164號台北市大安區安和路二段161號台北市大安區安和路二段161號")
        val openInfo = OpenInfo(isStillOpen = false, openDate = "2021-05-15", reportStatus = 0, details = listOf(detail1, detail2))
        val overdueInfo = OverdueInfo(openDate = "2021-05-15", details = listOf(detail3, detail4))
        reportStatus = ReportStatus(openInfo, listOf(overdueInfo, overdueInfo))
    }
}