package com.rn1.puffren.ui.registry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentRegistryBinding
import com.rn1.puffren.ext.getVmFactory

class RegistryFragment : Fragment() {

    lateinit var binding: FragmentRegistryBinding
    val viewModel by viewModels<RegistryViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegistryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.passRegistryCheck.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    viewModel.registry()
                }
            }
        })

        return binding.root
    }
}