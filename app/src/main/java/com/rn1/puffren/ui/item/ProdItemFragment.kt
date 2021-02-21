package com.rn1.puffren.ui.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentProdItemBinding

class ProdItemFragment : Fragment() {

    lateinit var binding: FragmentProdItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prod_item, container, false)




        return binding.root
    }

}