package com.rn1.puffren.ui.report

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface.OnShowListener
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R
import com.rn1.puffren.data.*
import com.rn1.puffren.databinding.FragmentReportBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.showOrHide
import com.rn1.puffren.ui.history.advance.LocationAdapter
import com.rn1.puffren.util.UserManager
import com.rn1.puffren.util.Util.getDateFormat
import java.util.*

class ReportFragment : Fragment() {

    lateinit var binding: FragmentReportBinding
    val viewModel by viewModels<ReportViewModel> { getVmFactory() }

    private val todayAdapter = TodayReportAdapter()
    private val overdueAdapter = OverdueAdapter()
    private lateinit var alertDialog: AlertDialog

    private val dateFormat = getDateFormat()
    private var isOpening = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentReportBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val today = dateFormat.format(Calendar.getInstance(Locale.TAIWAN).time)
        binding.textTodayOpenDate.text = today
        binding.buttonReportSale.setOnClickListener {
            findNavController().navigate(
                NavigationDirections.actionGlobalSaleReportFragment(today)
            )
        }

        binding.buttonReport.setOnClickListener {
            when (isOpening) {
                // close
                true -> {
                    val builder = AlertDialog.Builder(context)
                    builder.apply {
                        setCancelable(true)
                        setTitle(getString(R.string.report_close))
                        setPositiveButton(getString(R.string.confirm_close)) { _, _ ->
                            viewModel.reportForToday(null, 2, UserManager.recordId)
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.close_success),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    alertDialog = builder.create()
                    alertDialog.show()
                }
                // open
                false -> {
                    val view = LayoutInflater.from(requireContext()).inflate(
                        R.layout.dialog_report_location,
                        null
                    )
                    setUpLocationPicker(view)
                    val builder = AlertDialog.Builder(context)
                    builder.apply {
                        setCancelable(true)
                        setView(view)
                        setTitle(getString(R.string.report_open))
                        setPositiveButton(getString(R.string.confirm_open), null)
                    }
                    alertDialog = builder.create()

                    alertDialog.setOnShowListener(OnShowListener {
                        val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        button.setOnClickListener {
                            val location =
                                view.findViewById<EditText>(R.id.text_location).text.toString()
                            if (location.isBlank()) {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.location_field_note),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // call api
                                viewModel.reportForToday(location, 1, null)
                                alertDialog.dismiss()
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.open_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                    alertDialog.show()
                }
            }
        }

        binding.buttonClosedToday.setOnClickListener {
            // closed
            val builder = AlertDialog.Builder(context)
            builder.apply {
                setCancelable(true)
                setTitle(getString(R.string.report_closed))
                setPositiveButton(getString(R.string.closed_comfirm)) { _, _ ->
                    viewModel.reportForToday(null, 3, null)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.closed_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            alertDialog = builder.create()
            alertDialog.show()
        }

        viewModel.reportStatus.observe(viewLifecycleOwner, Observer {
            it?.let {

                val todayInfo = it.today
                val overdueInfo = it.overdueRecords

                with(binding) {

                    recyclerTodayReport.adapter = todayAdapter
                    todayAdapter.submitList(todayInfo.details)

                    recyclerOverdueRecord.apply {
                        adapter = overdueAdapter
                        showOrHide(overdueInfo.isNotEmpty())
                    }
                    overdueAdapter.submitList(overdueInfo)

                    buttonReportSale.showOrHide(
                        todayInfo.details.isNotEmpty() && todayInfo.reportStatus == 0
                    )

                    textNoRecordToday.apply {
                        showOrHide(todayInfo.details.isEmpty() || todayInfo.isOnBreak ?: false)
                        text = when (todayInfo.isOnBreak) {
                            true -> getString(R.string.closed_today)
                            else -> getString(R.string.no_record_today)
                        }
                    }

                    viewToday.showOrHide(todayInfo.details.isNotEmpty() && !(todayInfo.isOnBreak ?: false))

                    when (todayInfo.isOnBreak) {
                        true -> {
                            setReportButton(false, todayInfo.isStillOpen ?: false)
                            setClosedButton(false)
                        }
                        else -> {
                            when (todayInfo.details.isEmpty()) {
                                true -> {
                                    setReportButton(true, todayInfo.isStillOpen ?: false)
                                    setClosedButton(true)
                                }
                                else -> {
                                    setClosedButton(false)
                                    when (todayInfo.isStillOpen) {
                                        true -> {
                                            isOpening = true
                                            setReportButton(true, todayInfo.isStillOpen)
                                            setReportStatusText(
                                                todayInfo.isStillOpen,
                                                todayInfo.reportStatus
                                            )
                                        }
                                        else -> {
                                            isOpening = false
                                            setReportStatusText(
                                                todayInfo.isStillOpen ?: false,
                                                todayInfo.reportStatus
                                            )
                                            when (todayInfo.reportStatus) {
                                                0 -> {
                                                    setReportButton(
                                                        true,
                                                        todayInfo.isStillOpen ?: false
                                                    )
                                                }
                                                else -> {
                                                    setReportButton(
                                                        false,
                                                        todayInfo.isStillOpen ?: false
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })

        return binding.root
    }

    private fun setReportButton(isEnabled: Boolean, isOpening: Boolean) {
        binding.buttonReport.apply {
            this.isEnabled = isEnabled
            backgroundTintList = when (isEnabled) {
                true -> {
                    ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.green_008000))
                }
                else -> {
                    ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.grey_e1e1e1))
                }
            }
            text = when (isOpening) {
                true -> {
                    getString(R.string.report_close)
                }
                else -> {
                    getString(R.string.report_open)
                }
            }
        }
    }

    private fun setClosedButton(isEnabled: Boolean) {
        binding.buttonClosedToday.apply {
            this.isEnabled = isEnabled
            backgroundTintList = when (isEnabled) {
                true -> {
                    ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.red_800000))
                }
                else -> {
                    ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.grey_e1e1e1))
                }
            }
        }
    }

    private fun setReportStatusText(isOpening: Boolean, reportStatus: Int?) {
        binding.textReportStatus.apply {

            when (isOpening) {
                true -> {
                    text = context.getString(R.string.opening)
                    backgroundTintList = ColorStateList.valueOf(
                        PuffRenApplication.instance.getColor(R.color.green_008000)
                    )
                }
                else -> {
                    when (reportStatus) {
                        0 -> {
                            text = context.getString(R.string.already_close)
                            backgroundTintList = ColorStateList.valueOf(
                                PuffRenApplication.instance.getColor(R.color.red_800000)
                            )
                        }
                        else -> {
                            text = context.getString(R.string.already_report_in_time)
                            backgroundTintList = ColorStateList.valueOf(
                                PuffRenApplication.instance.getColor(R.color.green_008000)
                            )
                        }
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getReportStatus()
    }

    @SuppressLint("InflateParams")
    private fun setUpLocationPicker(view: View) {
        viewModel.locationOptions.observe(viewLifecycleOwner, Observer { options ->
            options?.let {
                val textOptions = view.findViewById<TextView>(R.id.text_location_options)
                textOptions.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        val dialogView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.dialog_location, null)
                        dialogView.findViewById<RecyclerView>(R.id.recycler_location).apply {
                            adapter = LocationAdapter(viewModel).apply {
                                submitList(options)
                            }
                        }
                        val builder = AlertDialog.Builder(context)
                        builder.apply {
                            setCancelable(true)
                            setView(dialogView)
                            create()
                            show()
                        }
                    }
                }
            }
        })
    }
}