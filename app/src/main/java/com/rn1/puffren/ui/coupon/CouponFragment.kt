package com.rn1.puffren.ui.coupon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.databinding.FragmentCouponBinding
import com.rn1.puffren.ext.getVmFactory

class CouponFragment : Fragment() {

    lateinit var binding: FragmentCouponBinding
    private val viewModel by viewModels<CouponViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCouponBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val recyclerView = binding.recyclerCoupon
        val adapter = CouponAdapter()
        recyclerView.adapter = adapter

        viewModel.coupons.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}