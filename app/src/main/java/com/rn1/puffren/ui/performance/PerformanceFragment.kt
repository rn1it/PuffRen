package com.rn1.puffren.ui.performance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rn1.puffren.R
import com.rn1.puffren.data.Performance
import com.rn1.puffren.databinding.FragmentPerformanceBinding

class PerformanceFragment : Fragment() {

    lateinit var binding: FragmentPerformanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPerformanceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        val list = mutableListOf<Performance>().also {
            it.add(Performance("1"))
            it.add(Performance("2"))
            it.add(Performance("3"))
            it.add(Performance("4"))
        }

        val recycler = binding.recyclerPerformance
        val adapter = PerformanceAdapter().apply {
            submitList(list)
        }
        recycler.adapter = adapter




        return binding.root
    }

}