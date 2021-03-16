package com.rn1.puffren.ui.product.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentDetailBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.factory.ProductViewModelFactory

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    val viewModel by viewModels<DetailViewModel> { getVmFactory(DetailFragmentArgs.fromBundle(requireArguments()).product) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.leaveDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) findNavController().popBackStack()
            }
        })

        return binding.root
    }
}