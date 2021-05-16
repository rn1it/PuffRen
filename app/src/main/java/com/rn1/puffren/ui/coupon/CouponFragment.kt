package com.rn1.puffren.ui.coupon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rn1.puffren.R
import com.rn1.puffren.data.Coupon
import com.rn1.puffren.databinding.FragmentCouponBinding

class CouponFragment : Fragment() {

    lateinit var binding: FragmentCouponBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCouponBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerCoupon
        val adapter = CouponAdapter()
        recyclerView.adapter = adapter
        val list = mutableListOf<Coupon>().apply {
            add(Coupon("1"))
            add(Coupon("2"))
        }
        adapter.submitList(list)

        return binding.root
    }

}