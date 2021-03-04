package com.rn1.puffren.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentProdBinding

class ProdFragment : Fragment() {

    lateinit var binding: FragmentProdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prod, container, false)
        binding.lifecycleOwner = this
        binding.viewpagerProduct.let {
            binding.tabsCatalog.setupWithViewPager(it)
            it.adapter = ProdTypeAdapter(childFragmentManager)
            it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabsCatalog))
        }

        return binding.root
    }

}