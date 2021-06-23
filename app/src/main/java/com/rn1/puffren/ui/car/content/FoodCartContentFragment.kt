package com.rn1.puffren.ui.car.content

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentFoodCartContentBinding
import com.rn1.puffren.ext.dismissDialog
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.loadingDialog
import com.rn1.puffren.ext.showDialog
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.ui.car.FoodCartViewModel
import com.rn1.puffren.util.*
import com.rn1.puffren.util.Util.getDateFormat
import java.util.*

class FoodCartContentFragment : Fragment() {

    private lateinit var binding: FragmentFoodCartContentBinding
    private val viewModel by viewModels<FoodCartViewModel> { getVmFactory() }

    private val adapter by lazy { FoodCarSetAdapter(viewModel) }
    private val calendar = Calendar.getInstance(Locale.TAIWAN).apply { add(Calendar.DATE, 7) }

    private val loadingDialog by lazy { loadingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFoodCartContentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.orderDatePicker.setOnClickListener {

            val dialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    binding.orderDatePicker.text = getDateFormat().format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.minDate = Calendar.getInstance(Locale.TAIWAN).apply {
                add(Calendar.DATE, 7)
            }.time.time

            dialog.show()
        }

        binding.buttonSendOrder.setOnClickListener {
            viewModel.checkInputInfo()
        }

        binding.recyclerOrderContent.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.apply {
                    submitList(it)
                    notifyDataSetChanged()
                }
            }
        })

        viewModel.invalidInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    INVALID_DATE_EMPTY -> {
                        Util.setTextToToast(getString(R.string.invalid_order_date_empty))
                    }
                    INVALID_ADDRESS_EMPTY -> {
                        Util.setTextToToast(getString(R.string.invalid_order_address_empty))
                    }
                    INVALID_NAME_EMPTY -> {
                        Util.setTextToToast(getString(R.string.invalid_order_name_empty))
                    }
                    INVALID_PHONE_EMPTY -> {
                        Util.setTextToToast(getString(R.string.invalid_order_phone_empty))
                    }
                    INVALID_EMAIL_EMPTY -> {
                        Util.setTextToToast(getString(R.string.invalid_order_email_empty))
                    }
                    INVALID_ITEM_EMPTY -> {
                        Util.setTextToToast(getString(R.string.invalid_order_item_select))
                    }
                }
                viewModel.cleanInvalidInfo()
            }
        })

        viewModel.passCheck.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.sendOrder()
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    LoadApiStatus.LOADING -> showDialog(loadingDialog)
                    LoadApiStatus.DONE, LoadApiStatus.ERROR -> dismissDialog(loadingDialog)
                }
            }
        })

        return binding.root
    }
}