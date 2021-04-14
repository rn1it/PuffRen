package com.rn1.puffren.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.databinding.FragmentLoginBinding
import com.rn1.puffren.ext.getVmFactory

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    val viewModel by viewModels<LoginViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalProfileFragment(it))
            }
        })




        // Inflate the layout for this fragment
        return binding.root
    }
}