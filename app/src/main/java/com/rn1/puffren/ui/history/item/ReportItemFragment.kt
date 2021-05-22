package com.rn1.puffren.ui.history.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.databinding.FragmentReportItemBinding
import com.rn1.puffren.ext.getVmFactory

class ReportItemFragment : Fragment() {

    lateinit var binding: FragmentReportItemBinding
    private val viewModel by viewModels<ReportItemViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentReportItemBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val recyclerView = binding.recyclerReportItem
        val adapter = ReportItemAdapter()
        recyclerView.adapter = adapter

        viewModel.reportItems.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}