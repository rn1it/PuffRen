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
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.show
import java.text.SimpleDateFormat
import java.util.*

class AdvanceReportFragment : Fragment() {

    lateinit var binding: FragmentAdvanceReportBinding
    val viewModel by viewModels<AdvanceReportViewModel> { getVmFactory() }

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.TAIWAN)
    private lateinit var alertDialog: AlertDialog
    private val calendar = Calendar.getInstance(Locale.TAIWAN)

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
                Toast.makeText(context, "$it 回報完成", Toast.LENGTH_SHORT).show()
                findNavController().navigate(AdvanceReportFragmentDirections.actionAdvanceReportFragmentToHistoryFragment())
            }
        })

        viewModel.location.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.textLocation.setText(it)
                alertDialog.dismiss()
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
                //TODO
                //submitList(options)
                submitList(listOf("a","b","c"))
            }
        }
        builder.setView(addView)
        alertDialog = builder.create()
        alertDialog.show()
    }
}