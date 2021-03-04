package com.rn1.puffren.ui.product.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.R
import com.rn1.puffren.data.Product
import com.rn1.puffren.databinding.FragmentProdItemBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ui.product.ProdTypeFilter

class ProdItemFragment(prodTypeFilter: ProdTypeFilter) : Fragment() {

    private lateinit var binding: FragmentProdItemBinding
    private val viewModel by viewModels<ProdItemViewModel> { getVmFactory(prodTypeFilter) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProdItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prod_item, container, false)
        binding.lifecycleOwner = this

        recyclerView = binding.recyclerProductItem
        adapter = ProdItemAdapter(viewModel)

        recyclerView.adapter = adapter

        val list = mutableListOf<Product>()
        val product = Product()
        list.add(product)
        list.add(product)
        list.add(product)
        list.add(product)
        list.add(product)

        adapter.submitList(list)


        return binding.root
    }
}