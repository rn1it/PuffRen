package com.rn1.puffren.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.MainViewModel
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentHomeBinding
import com.rn1.puffren.ext.dismissDialog
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.loadingDialog
import com.rn1.puffren.ext.showDialog
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.UserManager

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    private val loadingDialog by lazy { loadingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

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

        viewModel.navigateToProfile.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (UserManager.userToken == null) {
                    findNavController().navigate(NavigationDirections.actionGlobalLoginFragment())
                    viewModel.navigateToProfileDone()
                } else {
                    findNavController().navigate(NavigationDirections.actionGlobalProfileFragment(mainViewModel.user))
                    viewModel.navigateToProfileDone()
                }
            }
        })

        viewModel.navigateToFoodCar.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalFoodCarFragment())
                viewModel.navigateToFoodCarDone()
            }
        })

        viewModel.homePageItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
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