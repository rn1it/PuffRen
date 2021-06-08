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
import com.rn1.puffren.ext.hide
import com.rn1.puffren.ext.show
import com.rn1.puffren.ui.history.advance.LocationAdapter
import com.rn1.puffren.util.UserManager
import com.rn1.puffren.util.Util.getDateFormat
import java.util.*

class ReportFragment : Fragment() {

    lateinit var binding: FragmentReportBinding
    val viewModel by viewModels<ReportViewModel> { getVmFactory() }

    private val todayAdapter = TodayReportAdapter()
    private val overdueAdapter = OverdueAdapter()
    var reportStatus: ReportStatus? = null
    private lateinit var alertDialog: AlertDialog

    private val dateFormat = getDateFormat()
    private var isOpening = false
    private var closedToday = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        testData()
        binding = FragmentReportBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // for test -----------------------
//        reportStatus?.let {
//            with(binding) {
//                // today
//                textTodayOpenDate.text = it.today.openDate
//                recyclerTodayReport.adapter = todayAdapter
//                todayAdapter.submitList(it.today.details)
//
//                //overdue
//                recyclerOverdueRecord.apply {
//                    show()
//                    adapter = overdueAdapter
//                }
//                overdueAdapter.submitList(it.overdueRecords)
//            }
//        }
        // --------------------------
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
                    viewModel.reportForToday(null, 2, "47")
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
                /**
                 * Default: 1. disable open and closed button
                 *          2. show no record today
                 * Today:
                 * 1.with detail ->
                 *      a. isStillOpen == true : close button, status opening
                 *      b. isStillOpen == false : open button
                 *          x. reportStatus == 0 : show report button, status close
                 *          y. reportStatus == 1 : hide report button, show status reported
                 *
                 *
                 *
                 * 2.no detail -> open button and closed button
                 *
                 * Overdue:
                 *
                 */
                val todayInfo = it.today
                val overdueInfo = it.overdueRecords

                with(binding) {

                    if (todayInfo.details.isNotEmpty()) {
                        textNoRecordToday.hide()
                        viewToday.show()
                        recyclerTodayReport.adapter = todayAdapter
                        todayAdapter.submitList(todayInfo.details)
                        when (todayInfo.isStillOpen) {
                            true -> {
                                isOpening = true
                                textReportStatus.apply {
                                    text = context.getString(R.string.opening)
                                    backgroundTintList = ColorStateList.valueOf(
                                        PuffRenApplication.instance.getColor(R.color.green_008000)
                                    )
                                }
                                buttonReport.apply {
                                    isEnabled = true
                                    text = getString(R.string.report_close)
                                    backgroundTintList = ColorStateList.valueOf(
                                        PuffRenApplication.instance.getColor(R.color.green_008000)
                                    )
                                }
                                buttonClosedToday.apply {
                                    isEnabled = false
                                    backgroundTintList = ColorStateList.valueOf(
                                        PuffRenApplication.instance.getColor(R.color.grey_e1e1e1)
                                    )
                                }
                                buttonReportSale.hide()
                            }
                            false -> {
                                isOpening = false
                                buttonReport.text = getString(R.string.report_open)

                                when (todayInfo.reportStatus) {
                                    0 -> {
                                        buttonReport.backgroundTintList = ColorStateList.valueOf(
                                            PuffRenApplication.instance.getColor(R.color.green_008000)
                                        )
                                        buttonReport.isEnabled = true
                                        buttonReportSale.show()
                                        textReportStatus.apply {
                                            text = context.getString(R.string.already_close)
                                            backgroundTintList = ColorStateList.valueOf(
                                                PuffRenApplication.instance.getColor(R.color.red_800000)
                                            )
                                        }
                                    }
                                    1 -> {
                                        buttonReport.backgroundTintList = ColorStateList.valueOf(
                                            PuffRenApplication.instance.getColor(R.color.grey_e1e1e1)
                                        )
                                        buttonReportSale.hide()
                                        textReportStatus.apply {
                                            text = context.getString(R.string.already_report_in_time)
                                            backgroundTintList = ColorStateList.valueOf(
                                                PuffRenApplication.instance.getColor(R.color.green_008000)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        buttonReport.apply {
                            isEnabled = true
                            backgroundTintList =
                                ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.green_008000))
                        }
                        buttonClosedToday.apply {
                            isEnabled = true
                            backgroundTintList =
                                ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.red_800000))
                        }
                    }

                    if (overdueInfo.isNotEmpty()) {
                        recyclerOverdueRecord.apply {
                            show()
                            adapter = overdueAdapter
                        }
                        overdueAdapter.submitList(overdueInfo)
                    }
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReportStatus()
    }

    private fun testData() {
        val detail1 = ReportOpenStatus(
            recordId = "31",
            openStatus = 2,
            openTime = "15:00",
            closeTime = "22:20",
            openLocation = "台北市大安區安和路二段161號台北市大安區安和路二段161號台北市大安區安和路二段161號"
        )
        val detail2 = ReportOpenStatus(
            recordId = "32",
            openStatus = 1,
            openTime = "12:00",
            closeTime = "22:20",
            openLocation = "台北市大安區安和路二段162號台北市大安區安和路二段161號台北市大安區安和路二段161號"
        )
        val detail3 = ReportOpenStatus(
            recordId = "313",
            openStatus = 2,
            openTime = "13:00",
            closeTime = "22:20",
            openLocation = "台北市大安區安和路二段163號台北市大安區安和路二段161號台北市大安區安和路二段161號"
        )
        val detail4 = ReportOpenStatus(
            recordId = "314",
            openStatus = 1,
            openTime = "16:00",
            closeTime = "22:20",
            openLocation = "台北市大安區安和路二段164號台北市大安區安和路二段161號台北市大安區安和路二段161號"
        )
        val openInfo = OpenInfo(
            isStillOpen = false, openDate = "2021-05-15", reportStatus = 0, details = listOf(
                detail1,
                detail2
            )
        )
        val overdueInfo = OverdueInfo(openDate = "2021-05-15", details = listOf(detail3, detail4))
        reportStatus = ReportStatus(openInfo, listOf(overdueInfo, overdueInfo))
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