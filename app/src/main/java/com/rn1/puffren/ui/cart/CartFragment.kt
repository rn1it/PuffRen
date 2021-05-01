package com.rn1.puffren.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rn1.puffren.databinding.FragmentCartBinding
import com.rn1.puffren.ext.getVmFactory

class CartFragment: Fragment() {

    lateinit var binding: FragmentCartBinding
    val viewModel by viewModels<CartViewModel> {
        getVmFactory(
            CartFragmentArgs.fromBundle(requireArguments()).product,
            CartFragmentArgs.fromBundle(requireArguments()).itemPackage
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val s1 = "巧克力"
        val s2 = "巧克力1"
        val s3 = "巧克力2"
        val s4 = "巧克力3"
        val s5 = "巧克力4"
        val list = mutableListOf<String>()
        list.add(s1)
        list.add(s2)
        list.add(s3)
        list.add(s4)
        list.add(s5)

        val recycler = binding.recyclerFavors
        val adapter = FavorAdapter()
        recycler.adapter = adapter

        adapter.submitList(list)


        return binding.root
    }
}