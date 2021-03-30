package com.rn1.puffren.ui.product.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R
import com.rn1.puffren.data.ItemPackage
import com.rn1.puffren.databinding.FragmentDetailBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.factory.ProductViewModelFactory

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    val viewModel by viewModels<DetailViewModel> { getVmFactory(DetailFragmentArgs.fromBundle(requireArguments()).product) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recycler = binding.recyclerItemPackage
        val adapter = ItemPackageAdapter(viewModel)

        val list = mutableListOf<ItemPackage>()
        val itemPackage1 = ItemPackage(1, 200)
        val itemPackage2 = ItemPackage(2, 400, 380)
        val itemPackage3 = ItemPackage(3, 600, 560)
        val itemPackage4 = ItemPackage(4, 800, 740)
        list.add(itemPackage1)
        list.add(itemPackage2)
        list.add(itemPackage3)
        list.add(itemPackage4)

        adapter.submitList(list)

        recycler.adapter = adapter


        viewModel.leaveDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) findNavController().popBackStack()
            }
        })

        viewModel.itemPackage.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.buttonDetailAdd.text = PuffRenApplication.instance.getString(R.string.note_choose_favor)
            }
        })

        viewModel.show2Cart.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalAdd2cartDialog())
            }
        })

        return binding.root
    }
}