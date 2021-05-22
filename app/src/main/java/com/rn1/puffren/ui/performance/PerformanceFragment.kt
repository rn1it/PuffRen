package com.rn1.puffren.ui.performance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.databinding.FragmentPerformanceBinding
import com.rn1.puffren.ext.getVmFactory

class PerformanceFragment : Fragment() {

    lateinit var binding: FragmentPerformanceBinding
    private val viewModel by viewModels<PerformanceViewModel> { getVmFactory() }

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
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}