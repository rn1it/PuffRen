package com.rn1.puffren.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rn1.puffren.NavigationDirections
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R
import com.rn1.puffren.custom.CalendarAdapter
import com.rn1.puffren.data.*
import com.rn1.puffren.databinding.FragmentHistoryBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.hide
import com.rn1.puffren.ext.show
import com.rn1.puffren.ui.history.item.ReportItemAdapter
import com.rn1.puffren.util.MAX_CALENDAR_DAYS
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {

    lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModels<HistoryViewModel> { getVmFactory() }

    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var currentDateText: TextView
    private lateinit var gridView: GridView
    private lateinit var calendarAdapter: CalendarAdapter

    private val calendar = Calendar.getInstance(Locale.TAIWAN)
    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.TAIWAN)
    private val eventDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN)

    private val dates = mutableListOf<Date>()
    private val eventsList = mutableListOf<Events>()

    private var selectedDate: Date? = null
    private var destination = 1

    private var saleCalendar: SaleCalendar? = null
    private val reportItemAdapter = ReportItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        testData()
        initialLayout()
        setUpCalendar(saleCalendar)

        binding.textReport.setOnClickListener {
            viewModel.navigate(destination)
        }

        viewModel.saleCalendar.observe(viewLifecycleOwner, Observer {
            it?.let {
                saleCalendar = it
                setUpCalendar(saleCalendar)
            }
        })

        viewModel.navigateToReportItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalReportItemFragment())
                viewModel.navigateToReportItemDone()
            }
        })

        viewModel.navigateToAdvanceReport.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalAdvanceReportFragment())
                viewModel.navigateToAdvanceReportDone()
            }
        })
        return binding.root
    }

    private fun initialLayout() {

        nextButton = binding.btNext
        previousButton = binding.btPrevious
        currentDateText = binding.textCurrentDate
        gridView = binding.calendarDate

        // last month
        previousButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            setUpCalendar(saleCalendar)
        }

        // next month
        nextButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            setUpCalendar(saleCalendar)
        }

        setupGridItemClickListener()
    }

    private fun setUpCalendar(saleCalendar: SaleCalendar?) {
        val currentDate = dateFormat.format(calendar.time)
        currentDateText.text = currentDate

        dates.clear()
        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        // start day count from Sunday, so need -1
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (dates.size < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendarAdapter = CalendarAdapter(requireContext(), dates, calendar, saleCalendar, selectedDate)
        gridView.adapter = calendarAdapter
    }

    private fun setupGridItemClickListener() {

        val today = eventDateFormat.format(Date())

        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                selectedDate = dates[position]
                setUpCalendar(saleCalendar)

                val date = eventDateFormat.format(selectedDate!!)
                compareDate(today, date)

                 saleCalendar?.let { calendar ->
                    val expect = calendar.expectToOpen.filter { it.reportDate == date }
                    val relax = calendar.relax.filter { it.reportDate == date }
                    val open = calendar.reportDetails.filter { it.openDate == date }

                    with(binding) {
                        if (expect.isNotEmpty()) {
                            viewReportDetail.hide()
                            textReport.apply {
                                show()
                                text = context.getString(R.string.already_advance_report)
                            }
                            textReportData.apply {
                                show()
                                text = PuffRenApplication.instance.getString(R.string.open_at_, expect[0].expectedOpenTime)
                            }
                        } else if (relax.isNotEmpty()) {
                            viewReportDetail.hide()
                            textReport.hide()
                            textReportData.apply {
                                show()
                                text = getString(R.string.closed_today)
                            }
                        } else if (open.isNotEmpty()) {
                            reportDetail = open[0]
                            recyclerReportDetail.adapter = reportItemAdapter
                            reportItemAdapter.submitList(open[0].details)
                            viewReportDetail.show()
                            textReport.hide()
                            textReportData.hide()
                        } else {
                            viewReportDetail.hide()
                            textReport.show()
                            textReportData.apply {
                                show()
                                text = getString(R.string.no_report_data)
                            }
                        }
                    }
                }

                /**
                val date = eventDateFormat.format( dates[position])
                val month = monthFormat.format(dates[position])
                val year = yearFormat.format(dates[position])
                Logger.d("today = $today")
                Logger.d("date = $date")
                Logger.d("month = $month")
                Logger.d("year = $year")
                Logger.d("position = $position")
                Logger.d("dates[position] = ${dates[position]}")
                */
            }
    }

    /**
     * d1.compareTo(d2) < 0 ==
     * if d1 before d2 -> d1 < d2 == true
     */
    private fun compareDate(d1: String, d2: String){
        when(d1 < d2) {
            true -> {
                binding.textReport.text = getString(R.string.report_advance)
                destination = 2
            }
            else -> {
                binding.textReport.text = getString(R.string.report_data)
                destination = 1
            }
        }
    }

    private fun testData() {
        val list1 = listOf(ReportOpenStatus(reportDate = "2021-05-10", expectedOpenTime = "9:00" , openLocation = "台北市士林區中山北路六段234號"))
        val list2 = listOf(ReportOpenStatus(reportDate = "2021-05-08"))
        val list4 = listOf(ReportItem("0", "原殼泡芙", 20, 20), ReportItem("0", "原泡芙", 30, 30), ReportItem("0", "殼泡芙", 20, 90))
        val list3 = listOf(ReportDetail(id = "0", openDate = "2021-05-18", details = list4))
        saleCalendar = SaleCalendar(list1, list2, list3)
    }
}