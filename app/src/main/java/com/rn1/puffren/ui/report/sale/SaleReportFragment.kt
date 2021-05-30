package com.rn1.puffren.ui.report.sale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.databinding.FragmentSaleReportBinding
import com.rn1.puffren.ext.getVmFactory

class SaleReportFragment : Fragment() {

    lateinit var binding: FragmentSaleReportBinding
    val viewModel by viewModels<SaleReportViewModel> { getVmFactory(SaleReportFragmentArgs.fromBundle(requireArguments()).date) }
    private lateinit var adapter: ReportItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSaleReportBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        adapter = ReportItemAdapter(viewModel)
        binding.recyclerReportItem.adapter = adapter

        binding.buttonReportSale.setOnClickListener {
            val spinnerPosition = binding.spinner.selectedItemPosition
            viewModel.reportSale(spinnerPosition)
        }

        viewModel.reportItems.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.passAndSubmitList(it)
            }
        })

        viewModel.reportResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        })
        return binding.root
    }
}