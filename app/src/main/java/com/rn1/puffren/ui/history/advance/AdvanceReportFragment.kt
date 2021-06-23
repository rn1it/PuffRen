package com.rn1.puffren.ui.history.advance

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentAdvanceReportBinding
import com.rn1.puffren.ext.*
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.ui.report.sale.SaleReportFragmentArgs
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.Util.getTimeFormat
import java.util.*

class AdvanceReportFragment : Fragment() {

    lateinit var binding: FragmentAdvanceReportBinding
    val viewModel by viewModels<AdvanceReportViewModel> {
        getVmFactory(SaleReportFragmentArgs.fromBundle(requireArguments()).date)
    }

    private val timeFormat = getTimeFormat()
    private lateinit var alertDialog: AlertDialog
    private val calendar = Calendar.getInstance(Locale.TAIWAN)

    private val loadingDialog by lazy { loadingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAdvanceReportBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.textTime.apply {

            setOnClickListener {
                val hours = calendar.get(Calendar.HOUR_OF_DAY)
                val minutes = calendar.get(Calendar.MINUTE)
                TimePickerDialog(
                    requireContext(),
                    R.style.Theme_AppCompat_Dialog,
                    TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
                        val c = Calendar.getInstance()
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        c.set(Calendar.MINUTE, minute)
                        c.timeZone = TimeZone.getDefault()
                        val eTime = timeFormat.format(c.time)
                        binding.textTime.text = eTime
                    },
                    hours,
                    minutes,
                    false
                ).apply {
                    show()
                }
            }
        }

        viewModel.locationOptions.observe(viewLifecycleOwner, Observer { options ->
            options?.let {
                binding.textOption.apply {
                    show()
                    setOnClickListener {
                        setUpLocationPicker(options)
                    }
                }
            }
        })

        viewModel.reportResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                Logger.d("$it ${getString(R.string.report_success)}")
                Toast.makeText(context, getString(R.string.report_success), Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }
        })

        viewModel.location.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.textLocation.setText(it)
                alertDialog.dismiss()
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

    @SuppressLint("InflateParams")
    private fun setUpLocationPicker(options: List<String>) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        val addView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_location, null)
        val recyclerView = addView.findViewById<RecyclerView>(R.id.recycler_location).apply {
            adapter = LocationAdapter(viewModel).apply {
                submitList(options)
            }
        }
        builder.setView(addView)
        alertDialog = builder.create()
        alertDialog.show()
    }
}