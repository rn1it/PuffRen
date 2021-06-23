package com.rn1.puffren.ui.registry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentRegistryBinding
import com.rn1.puffren.ext.dismissDialog
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.loadingDialog
import com.rn1.puffren.ext.showDialog
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.*

class RegistryFragment : Fragment() {

    lateinit var binding: FragmentRegistryBinding
    val viewModel by viewModels<RegistryViewModel> { getVmFactory() }

    private val loadingDialog by lazy { loadingDialog() }

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

        viewModel.invalidInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    INVALID_EMAIL_EMPTY -> {
                        Toast.makeText(requireContext(), getString(R.string.invalid_email_empty), Toast.LENGTH_SHORT).show()
                    }
                    INVALID_NAME_EMPTY -> {
                        Toast.makeText(requireContext(), getString(R.string.invalid_nickname_empty), Toast.LENGTH_SHORT).show()
                    }
                    INVALID_PASSWORD_EMPTY -> {
                        Toast.makeText(requireContext(), getString(R.string.invalid_password_empty), Toast.LENGTH_SHORT).show()
                    }
                    INVALID_PASSWORD_CONFIRM_EMPTY -> {
                        Toast.makeText(requireContext(), getString(R.string.invalid_password_comfirm_empty), Toast.LENGTH_SHORT).show()
                    }
                    INVALID_FORMAT_PASSWORD_CONFIRM -> {
                        Toast.makeText(requireContext(), getString(R.string.password_comfirm_fail), Toast.LENGTH_SHORT).show()
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