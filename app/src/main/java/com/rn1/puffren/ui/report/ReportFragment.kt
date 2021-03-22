package com.rn1.puffren.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rn1.puffren.data.Report
import com.rn1.puffren.databinding.FragmentReportBinding
import com.rn1.puffren.ext.getVmFactory

class ReportFragment : Fragment() {

    lateinit var binding: FragmentReportBinding
    val viewModel by viewModels<ReportViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentReportBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val recyclerSaleReport = binding.recyclerSaleReport
        val adapter = ReportAdapter()

        val report1 = Report("1")
        val report2 = Report("2")
        val report3 = Report("3")
        val report4 = Report("4")

        val list = mutableListOf<Report>()
        list.add(report1)
        list.add(report2)
        list.add(report3)
        list.add(report4)

        recyclerSaleReport.adapter = adapter
        adapter.submitList(list)


        // Inflate the layout for this fragment
        return binding.root
    }

}