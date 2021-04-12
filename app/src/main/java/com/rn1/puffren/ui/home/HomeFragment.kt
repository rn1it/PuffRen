package com.rn1.puffren.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentHomeBinding
import com.rn1.puffren.ext.getVmFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val recyclerView = binding.recyclerHomePage
        val adapter = HomePageAdapter(viewModel)
        recyclerView.adapter = adapter


        viewModel.navigateToLocation.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalLocationFragment())
                viewModel.doneNavigateToLocation()
            }
        })

        viewModel.navigateToItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalProdFragment())
                viewModel.doneNavigateToItem()
            }
        })

        viewModel.navigateToMember.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalEditMembershipFragment())
                viewModel.doneNavigateToMember()
            }
        })

        viewModel.homePageItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

//        viewModel.status.observe(viewLifecycleOwner, Observer {
//            it?.let {
//
//            }
//        })


        return binding.root
    }
}