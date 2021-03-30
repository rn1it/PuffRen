package com.rn1.puffren.ui.add2cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rn1.puffren.databinding.DialogAdd2cartBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.util.Logger

class Add2cartDialog: BottomSheetDialogFragment() {

    lateinit var binding: DialogAdd2cartBinding
    val viewModel by viewModels<Add2cartViewModel> { getVmFactory()}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogAdd2cartBinding.inflate(inflater, container, false)
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