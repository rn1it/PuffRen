package com.rn1.puffren.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.MainViewModel
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentLoginBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.hideKeyboard
import com.rn1.puffren.util.*

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

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.buttonLogin.setOnClickListener {
            hideKeyboard()
            viewModel.checkLoginInfo()
        }

        binding.buttonRegistry.setOnClickListener {
            hideKeyboard()
            viewModel.navigateToRegistry()
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                mainViewModel.setupUser(it)
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToProfileFragment(
                        it
                    )
                )
                viewModel.navigateToProfileDone()
            }
        })

        viewModel.navigateToRegistry.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalRegistryFragment())
                viewModel.navigateToRegistryDone()
            }
        })

        viewModel.passRegistryCheck.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    viewModel.login()
                }
            }
        })

        viewModel.loginFail.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(context, getString(R.string.login_fail), Toast.LENGTH_SHORT)
                        .show()
                    viewModel.clearLoginFail()
                }
            }
        })

        viewModel.invalidInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    INVALID_FORMAT_EMAIL_EMPTY -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.invalid_email_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    INVALID_FORMAT_PASSWORD_EMPTY -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.invalid_password_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                viewModel.cleanInvalidInfo()
            }
        })

        return binding.root
    }
}