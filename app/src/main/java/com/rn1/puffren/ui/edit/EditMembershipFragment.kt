package com.rn1.puffren.ui.edit

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.data.CityMain
import com.rn1.puffren.databinding.FragmentEditMembershipBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.util.Util.getDateFormat
import com.rn1.puffren.util.Util.setTextToToast
import java.util.*

class EditMembershipFragment : Fragment() {

    lateinit var binding: FragmentEditMembershipBinding
    val viewModel by viewModels<EditMembershipViewModel> { getVmFactory() }

    private val calendar = Calendar.getInstance().apply { set(1990, 1, 1) }
    private val dateFormat = getDateFormat()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditMembershipBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupCitySpinner()

        setupBt()

        binding.birthDatePicker.setOnClickListener {

            val picker = DatePickerDialog(
                requireContext(),
                AlertDialog.THEME_HOLO_DARK,
                { _, year, monthOfYear, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    binding.birthDatePicker.text = dateFormat.format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            picker.show()
        }

        viewModel.navigateToEditPassword.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalEditPasswordFragment())
                viewModel.navigateToEditPasswordDone()
            }
        })

        viewModel.spinnerPosition.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.spinnerCity.setSelection(it)
            }
        })

        return binding.root
    }

    private fun setupCitySpinner() {

        val list = mutableListOf<String>()
        for (city in CityMain.values()) {
            list.add(city.title)
        }

        val arrayAdapter:ArrayAdapter<String> = object: ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            list
        ){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                view.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                return view
            }
        }

        binding.spinnerCity.apply {

            adapter = arrayAdapter
            onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>, p1: View?, position: Int, id: Long) {
                    val city = parent.getItemIdAtPosition(position).toString()
                    viewModel.city.value = city
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun setupBt() {
        binding.buttonEditPassword.setOnClickListener {
            setTextToToast("開發中，敬請期待!")
        }
        binding.buttonEditMemberInfoDone.setOnClickListener {
            setTextToToast("開發中，敬請期待!")
        }
    }
}