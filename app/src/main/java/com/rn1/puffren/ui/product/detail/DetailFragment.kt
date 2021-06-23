package com.rn1.puffren.ui.product.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentDetailBinding
import com.rn1.puffren.ext.dismissDialog
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.loadingDialog
import com.rn1.puffren.ext.showDialog
import com.rn1.puffren.network.LoadApiStatus

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    val viewModel by viewModels<DetailViewModel> { getVmFactory(DetailFragmentArgs.fromBundle(requireArguments()).product) }

    private val loadingDialog by lazy { loadingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recycler = binding.recyclerItemPackage
        val adapter = PuffPackageAdapter(viewModel)
        recycler.adapter = adapter

        viewModel.leaveDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) findNavController().popBackStack()
            }
        })

        viewModel.itemPackage.observe(viewLifecycleOwner, Observer {
            it?.let {

                binding.buttonDetailAdd.apply {
                    text = PuffRenApplication.instance.getString(R.string.note_choose_favor)
                    isEnabled = true
                    setBackgroundColor(PuffRenApplication.instance.getColor(R.color.orange_ffa626))
                }
            }
        })

        viewModel.navigateToCart.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalCartFragment(viewModel.product.value, viewModel.itemPackage.value))
                viewModel.navigateToCartDone()
            }
        })

        viewModel.product.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.product = it
                adapter.submitList(it.groupBuyingDetails)
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