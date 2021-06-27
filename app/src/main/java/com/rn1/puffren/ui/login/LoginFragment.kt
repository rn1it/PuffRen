package com.rn1.puffren.ui.login

import android.os.Bundle
import android.os.Handler
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
import com.rn1.puffren.data.EntryFrom
import com.rn1.puffren.databinding.FragmentLoginBinding
import com.rn1.puffren.ext.*
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.*

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    val viewModel by viewModels<LoginViewModel> { getVmFactory(LoginFragmentArgs.fromBundle(requireArguments()).entryFrom) }

    private val loadingDialog by lazy { loadingDialog() }
    private val messageDialog by lazy { messageDialog(getString(R.string.login_success)) }

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

                showDialog(messageDialog)
                Handler().postDelayed({
                    when(viewModel.entryFrom.value!!) {
                        EntryFrom.FROM_QR_CODE -> {
                            when (UserManager.isPuffren) {
                                true -> {
                                    findNavController().navigate(NavigationDirections.actionGlobalHomeFragment())
                                }
                                else -> {
                                    findNavController().navigate(
                                        LoginFragmentDirections.actionLoginFragmentToQRCodeFragment(it)
                                    )
                                }
                            }
                        }
                        EntryFrom.FROM_PROFILE -> {
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToProfileFragment(
                                    it
                                )
                            )
                        }
                    }

                    dismissDialog(messageDialog)
                }, 1000)

                viewModel.navigateToProfileDone()
            }
        })

        viewModel.navigateToRegistry.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalRegistryFragment(viewModel.entryFrom.value!!))
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
                    INVALID_EMAIL_EMPTY -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.invalid_email_empty),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    INVALID_PASSWORD_EMPTY -> {
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

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    LoadApiStatus.LOADING -> showDialog(loadingDialog)
                    LoadApiStatus.DONE, LoadApiStatus.ERROR -> dismissDialog(loadingDialog)
                }
            }
        })

        return binding.root
    }
}