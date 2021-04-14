package com.rn1.puffren.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.databinding.FragmentProdBinding
import com.rn1.puffren.databinding.FragmentProfileBinding
import com.rn1.puffren.ext.getVmFactory

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    val viewModel by viewModels<ProfileViewModel> { getVmFactory(ProfileFragmentArgs.fromBundle(requireArguments()).user) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToSaleReport.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalReportFragment())
                viewModel.navigateToSaleReportDone()
            }
        })

        viewModel.navigateToSaleReportHistory.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })

        viewModel.navigateToQRCode.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })

        return binding.root
    }
}