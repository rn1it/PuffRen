package com.rn1.puffren.ui.registry

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.MainViewModel
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.R
import com.rn1.puffren.data.EntryFrom
import com.rn1.puffren.databinding.FragmentRegistryBinding
import com.rn1.puffren.ext.*
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.*
import com.rn1.puffren.util.Util.setTextToToast

class RegistryFragment : Fragment() {

    lateinit var binding: FragmentRegistryBinding
    val viewModel by viewModels<RegistryViewModel> {
        getVmFactory(
            RegistryFragmentArgs.fromBundle(
                requireArguments()
            ).entryFrom
        )
    }

    private val loadingDialog by lazy { loadingDialog() }
    private val messageDialog by lazy { messageDialog(getString(R.string.registry_success)) }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegistryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.checkBox.setOnCheckedChangeListener { p0, isChecked ->
            viewModel.isReadUserPrivacy.value = isChecked
        }

        binding.textPrivacy.setOnClickListener {
            val view = LayoutInflater
                .from(requireContext()).inflate(R.layout.dialog_user_privacy, null)

            val webView = view.findViewById<WebView>(R.id.web_view)
            webView.loadUrl(PRIVACY_FILE)

            val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
            val height = (resources.displayMetrics.heightPixels * 0.95).toInt()
            val dialog = Dialog(requireActivity()).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(view)
                setCanceledOnTouchOutside(true)
                setCancelable(true)
                window?.apply {
                    setLayout(width, height)
                    setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.round_corner_border
                        )
                    )
                }
            }
            showDialog(dialog)
        }


        viewModel.passRegistryCheck.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    viewModel.registry()
                }
            }
        })

        viewModel.invalidInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    INVALID_EMAIL_EMPTY -> {
                        setTextToToast(getString(R.string.invalid_email_empty))
                    }
                    INVALID_NAME_EMPTY -> {
                        setTextToToast(getString(R.string.invalid_nickname_empty))
                    }
                    INVALID_PASSWORD_EMPTY -> {
                        setTextToToast(getString(R.string.invalid_password_empty))
                    }
                    INVALID_PASSWORD_CONFIRM_EMPTY -> {
                        setTextToToast(getString(R.string.invalid_password_comfirm_empty))
                    }
                    INVALID_FORMAT_PASSWORD_CONFIRM -> {
                        setTextToToast(getString(R.string.password_comfirm_fail))
                    }
                    INVALID_NOT_READ_USER_PRIVACY -> {
                        setTextToToast(getString(R.string.user_privacy_must_read))
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

        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                mainViewModel.setupUser(it)

                showDialog(messageDialog)
                Handler().postDelayed({
                    when (viewModel.entryFrom.value!!) {
                        EntryFrom.FROM_QR_CODE -> {
                            when (UserManager.isPuffren) {
                                true -> {
                                    findNavController().navigate(NavigationDirections.actionGlobalHomeFragment())
                                }
                                else -> {
                                    findNavController().navigate(
                                        RegistryFragmentDirections.actionRegistryFragmentToQRCodeFragment(
                                            it
                                        )
                                    )
                                }
                            }
                        }

                        EntryFrom.FROM_PROFILE -> {
                            findNavController().navigate(
                                RegistryFragmentDirections.actionRegistryFragmentToProfileFragment(
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

        return binding.root
    }
}