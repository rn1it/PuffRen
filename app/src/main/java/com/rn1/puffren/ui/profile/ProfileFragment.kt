package com.rn1.puffren.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.rn1.puffren.MainViewModel
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.R
import com.rn1.puffren.data.BuyDetail
import com.rn1.puffren.data.User
import com.rn1.puffren.databinding.FragmentProfileBinding
import com.rn1.puffren.ext.dismissDialog
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.loadingDialog
import com.rn1.puffren.ext.showDialog
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.SCANNER_TIMEOUT
import com.rn1.puffren.util.Util.setTextToToast
import com.squareup.moshi.Moshi

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    val viewModel by viewModels<ProfileViewModel> {
        getVmFactory(
            ProfileFragmentArgs.fromBundle(
                requireArguments()
            ).user
        )
    }
    private val moshi by lazy { Moshi.Builder().build() }
    private val jsonAdapter = moshi.adapter(BuyDetail::class.java)

    private val loadingDialog by lazy { loadingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        if (viewModel.user.value == null) {
            // user info will be null if user already logged in, and it will get user info from server
            viewModel.user.observe(viewLifecycleOwner, Observer {
                if (null != it) {
                    mainViewModel.setupUser(it)
                }
            })
        }

        viewModel.navigateToSaleReport.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalReportFragment())
                viewModel.navigateToSaleReportDone()
            }
        })

        viewModel.navigateToSaleReportHistory.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalHistoryFragment())
                viewModel.navigateToSaleReportHistoryDone()
            }
        })

        viewModel.navigateToScannerActivity.observe(viewLifecycleOwner, Observer {
            it?.let {
                val scanIntegrator = IntentIntegrator.forSupportFragment(this)
                scanIntegrator.apply {
                    setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                    setPrompt(getString(R.string.please_scan_qr_code))
                    setTimeout(SCANNER_TIMEOUT)
                    setOrientationLocked(false)
                    initiateScan()
                }
                viewModel.navigateToScannerActivityDone()
            }
        })

        //TODO ADD ORDER
        viewModel.navigateToOrder.observe(viewLifecycleOwner, Observer {
            it?.let {

                viewModel.navigateToOrderDone()
            }
        })

        viewModel.navigateToEditMember.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalEditMembershipFragment())
                viewModel.navigateToEditMemberDone()
            }
        })

        viewModel.navigateToActivity.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalActivityFragment())
                viewModel.navigateToActivityDone()
            }
        })

        viewModel.navigateToMemberQRCode.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalQRCodeFragment(it))
                viewModel.navigateToMemberQRCodeDone()
            }
        })

        viewModel.navigateToMemberCoupon.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalCouponFragment())
                viewModel.navigateToMemberCouponDone()
            }
        })

        viewModel.navigateToPerformance.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalPerformanceFragment())
                viewModel.navigateToPerformanceDone()
            }
        })

        viewModel.navigateToMemberAchievement.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalAchievementTypeFragment())
                viewModel.navigateToMemberAchievementDone()
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                setProfileImage(it)
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

    override fun onResume() {
        super.onResume()
        viewModel.getUserProfile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (scanningResult != null) {
            if (scanningResult.contents != null) {
                val scanContent = scanningResult.contents
                if (scanContent.isNotBlank()) {

                    val dialog = LayoutInflater
                        .from(requireContext())
                        .inflate(R.layout.dialog_qr_code_scan_result, null)

                    val builder = AlertDialog.Builder(context).apply {
                        setCancelable(true)
                        setView(dialog)
                    }

                    val buyDetail = jsonAdapter.fromJson(scanContent)!!

                    val alertDialog = builder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.show()

                    with(dialog) {
                        findViewById<TextView>(R.id.text_member_nickname).text =
                            buyDetail.userNickname
                        findViewById<TextView>(R.id.text_use_coupon).text = buyDetail.coupon
                        findViewById<TextView>(R.id.text_buy_total).text =
                            buyDetail.totalAmount.toString()
                        findViewById<TextView>(R.id.text_coupon_discount).text =
                            buyDetail.couponDiscount.toString()
                        findViewById<TextView>(R.id.text_member_discount).text =
                            buyDetail.memberDiscount.toString()
                        findViewById<TextView>(R.id.text_total_after_discount).text =
                            buyDetail.amountAfterDiscount.toString()
                        findViewById<TextView>(R.id.button_create_order).setOnClickListener {
                            //TODO
                        }
                        findViewById<TextView>(R.id.button_back).setOnClickListener {
                            alertDialog.dismiss()
                        }
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            setTextToToast(getString(R.string.error_msg_contact_it))
        }
    }

    private fun setProfileImage(user: User) {
        when (user.level) {
            "1" -> binding.imageProfile.setImageResource(R.drawable.lv1_member)
            "2" -> binding.imageProfile.setImageResource(R.drawable.lv2_member)
            "3" -> binding.imageProfile.setImageResource(R.drawable.lv3_member)
            "4" -> binding.imageProfile.setImageResource(R.drawable.lv4_member)
            "5" -> binding.imageProfile.setImageResource(R.drawable.lv5_member)
            else -> binding.imageProfile.setImageResource(R.drawable.vendor)
        }
    }
}