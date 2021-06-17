package com.rn1.puffren.ui.car

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.databinding.FragmentFoodCarBinding
import com.rn1.puffren.ext.getVmFactory

class FoodCartFragment : Fragment() {

    private lateinit var binding: FragmentFoodCarBinding
    private val viewModel by viewModels<FoodCartViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFoodCarBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToFoodCartContent.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalFoodCartContentFragment())
                viewModel.navigateToFoodCartContentDone()
            }
        })

        return binding.root
    }
}