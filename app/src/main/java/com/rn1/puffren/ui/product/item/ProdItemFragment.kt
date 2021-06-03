package com.rn1.puffren.ui.product.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentProdItemBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.hide
import com.rn1.puffren.ext.show
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

        adapter = ProdItemAdapter(viewModel)
        recyclerView = binding.recyclerProductItem.also {
            it.adapter = adapter
        }

        viewModel.productList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.product.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalDetailFragment(it))
                viewModel.navigateToProductDetailDone()
            }
        })

        if (viewModel.getProdTypeFilter() == ProdTypeFilter.DELIVERY) {
            binding.layoutComingSoon.show()
        } else {
            binding.layoutComingSoon.hide()
        }

        return binding.root
    }
}