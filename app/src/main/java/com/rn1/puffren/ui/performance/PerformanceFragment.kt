package com.rn1.puffren.ui.performance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.databinding.FragmentPerformanceBinding
import com.rn1.puffren.ext.*
import com.rn1.puffren.network.LoadApiStatus

class PerformanceFragment : Fragment() {

    lateinit var binding: FragmentPerformanceBinding
    private val viewModel by viewModels<PerformanceViewModel> { getVmFactory() }

    private val loadingDialog by lazy { loadingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPerformanceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val recycler = binding.recyclerPerformance
        val adapter = PerformanceAdapter()
        recycler.adapter = adapter

        viewModel.performances.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isEmpty()) {
                    recycler.hide()
                } else {
                    adapter.submitList(it)
                }
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    LoadApiStatus.LOADING -> showDialog(loadingDialog)
                    LoadApiStatus.DONE, LoadApiStatus.ERROR -> dismissDialog(loadingDialog)
                }
            }
        })

        return binding.root
    }

}