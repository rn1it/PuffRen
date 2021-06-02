package com.rn1.puffren.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.MainViewModel
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.R
import com.rn1.puffren.data.User
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

        viewModel.navigateToQRCode.observe(viewLifecycleOwner, Observer {
            it?.let {

                viewModel.navigateToQRCodeDone()
            }
        })

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

                viewModel.navigateToActivityDone()
            }
        })

        viewModel.navigateToMemberQRCode.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalQRCodeFragment())
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


        return binding.root
    }

    private fun setProfileImage(user: User){
        when(user.level){
            "1" -> binding.imageProfile.setImageResource(R.drawable.lv1_member)
            "2" -> binding.imageProfile.setImageResource(R.drawable.lv2_member)
            "3" -> binding.imageProfile.setImageResource(R.drawable.lv3_member)
            "4" -> binding.imageProfile.setImageResource(R.drawable.lv4_member)
            "5" -> binding.imageProfile.setImageResource(R.drawable.lv5_member)
            else -> binding.imageProfile.setImageResource(R.drawable.vendor)
        }
    }
}