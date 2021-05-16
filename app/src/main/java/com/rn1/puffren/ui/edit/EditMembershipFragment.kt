package com.rn1.puffren.ui.edit

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.databinding.FragmentEditMembershipBinding
import com.rn1.puffren.ext.getVmFactory
import java.text.SimpleDateFormat
import java.util.*

class EditMembershipFragment : Fragment() {

    lateinit var binding: FragmentEditMembershipBinding
    val viewModel by viewModels<EditMembershipViewModel> { getVmFactory() }

    lateinit var datePicker: DatePicker
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditMembershipBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.birthDatePicker.setOnClickListener {
            val d = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.birthDatePicker.text = dateFormat.format(calendar.time)
            }

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        viewModel.navigateToEditPassword.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalEditPasswordFragment())
                viewModel.navigateToEditPasswordDone()
            }
        })

        return binding.root
    }
}