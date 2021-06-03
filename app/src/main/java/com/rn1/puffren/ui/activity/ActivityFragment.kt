package com.rn1.puffren.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.databinding.FragmentActivityBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ui.coupon.CouponViewModel

class ActivityFragment : Fragment() {

    lateinit var binding: FragmentActivityBinding
    private val viewModel by viewModels<CouponViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentActivityBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


//        viewModel.coupons.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

        return binding.root
    }
}