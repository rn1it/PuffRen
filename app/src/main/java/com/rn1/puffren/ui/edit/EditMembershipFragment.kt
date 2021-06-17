package com.rn1.puffren.ui.edit

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.R
import com.rn1.puffren.data.CityMain
import com.rn1.puffren.databinding.FragmentEditMembershipBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.util.INVALID_NAME_EMPTY
import com.rn1.puffren.util.INVALID_NOT_READ_USER_PRIVACY
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.Util.getDateFormat
import com.rn1.puffren.util.Util.setTextToToast
import java.util.*

class EditMembershipFragment : Fragment() {

    lateinit var binding: FragmentEditMembershipBinding
    val viewModel by viewModels<EditMembershipViewModel> { getVmFactory() }

    private val calendar = Calendar.getInstance().apply { set(1990, 0, 1) }
    private val dateFormat = getDateFormat()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditMembershipBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupCitySpinner()

        binding.birthDatePicker.setOnClickListener {

            val dialog = DatePickerDialog(
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
            dialog.datePicker.maxDate = Date().time
            dialog.show()
        }

        binding.buttonEditMemberInfoDone.setOnClickListener {
            viewModel.checkInputInfo()
        }


        binding.checkReadNotification.setOnCheckedChangeListener { p0, isChecked ->
            viewModel.isReadUserPrivacy.value = isChecked
        }

        binding.imageInformation.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            val addView = LayoutInflater
                .from(requireContext()).inflate(R.layout.dialog_user_privacy, null)
            builder.setView(addView)
            val alertDialog = builder.create()
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            alertDialog.show()
        }

        viewModel.invalidInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    INVALID_NAME_EMPTY -> {
                        setTextToToast(getString(R.string.invalid_nickname_empty))
                    }
                    INVALID_NOT_READ_USER_PRIVACY -> {
                        setTextToToast("請仔細閱讀會員權益並打勾")
                    }
                }
                viewModel.cleanInvalidInfo()
            }
        })

        viewModel.passCheck.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.updateUser()
            }
        })

        viewModel.updateUserResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                setTextToToast(getString(R.string.update_user_success))
                Logger.d("user = ${it.userInfo}")
            }
        })

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
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    p1: View?,
                    position: Int,
                    id: Long
                ) {
                    val city = parent.getItemAtPosition(position).toString()
                    Logger.d("city = $city")
                    viewModel.city.value = city
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }
}