package com.rn1.puffren.custom

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R
import com.rn1.puffren.data.SaleCalendar
import com.rn1.puffren.ext.show
import kotlinx.android.synthetic.main.item_day_cell.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    context: Context,
    private val dates: List<Date>,
    private val currentDate: Calendar,
    private val saleCalendar: SaleCalendar?,
    private val selectedDate: Date?
) : ArrayAdapter<Date>(context, R.layout.item_day_cell) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val today = Calendar.getInstance(Locale.TAIWAN)
    private var selectedCalendar= Calendar.getInstance(Locale.TAIWAN)

    init {
        selectedDate?.let {
            selectedCalendar.time = it
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val monthDate = dates[position]
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = monthDate
        val dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)
        val displayDay = dateCalendar.get(Calendar.DATE)
        val displayMonth = dateCalendar.get(Calendar.MONTH) + 1
        val displayYear = dateCalendar.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH) + 1
        val currentYear = currentDate.get(Calendar.YEAR)

        var view = convertView

        if (view == null) {
            view = inflater.inflate(R.layout.item_day_cell, parent, false)
        }

        view?.let {

            // mark today
            if(displayYear == today.get(Calendar.YEAR)
                && displayMonth == (today.get(Calendar.MONTH) + 1)
                && displayDay == today.get(Calendar.DATE)) {
                view.text_calendar_day.setTextColor(PuffRenApplication.instance.getColor(R.color.orange_ffa626))
            }

            // mark selected date
            selectedDate?.let {
                if(displayYear == selectedCalendar.get(Calendar.YEAR)
                    && displayMonth == (selectedCalendar.get(Calendar.MONTH) + 1)
                    && displayDay == selectedCalendar.get(Calendar.DATE)) {
                    view.text_calendar_day.setTextColor(PuffRenApplication.instance.getColor(R.color.bg_color_yellow))
                    view.selected_circle.visibility = View.VISIBLE
                }
            }

            if (displayMonth == currentMonth && displayYear == currentYear) {
                it.setBackgroundColor(PuffRenApplication.instance.getColor(R.color.bg_color_yellow))
            } else {
                it.setBackgroundColor(PuffRenApplication.instance.getColor(R.color.grey_f5f5f5))
                it.text_calendar_day.setTextColor(PuffRenApplication.instance.getColor(R.color.white))
                // disable not current month action
                it.visibility = View.GONE
            }

            val dayNumber = view.findViewById<TextView>(R.id.text_calendar_day)
            val bot = view.findViewById<View>(R.id.bot)
            dayNumber.text = dayNo.toString()

            val eventCalendar = Calendar.getInstance(Locale.TAIWAN)

            saleCalendar?.let {
                for (date in saleCalendar.expectToOpen) {
                    eventCalendar.time = convertStringToDate(date.reportDate!!)
                    if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH)
                        && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                        && displayYear == eventCalendar.get(Calendar.YEAR)) {
                        bot.apply {
                            show()
                            backgroundTintList = ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.colorAccent))
                        }
                    }
                }
                for (date in saleCalendar.relax) {
                    eventCalendar.time = convertStringToDate(date.reportDate!!)
                    if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH)
                        && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                        && displayYear == eventCalendar.get(Calendar.YEAR)) {
                        bot.apply {
                            show()
                            backgroundTintList = ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.grey_999999))
                        }
                    }
                }
                for (date in saleCalendar.reportDetails) {
                    eventCalendar.time = convertStringToDate(date.openDate!!)
                    if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH)
                        && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                        && displayYear == eventCalendar.get(Calendar.YEAR)) {
                        bot.apply {
                            show()
                            backgroundTintList = ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.colorPrimaryDark))
                        }
                    }
                }
            }
        }
        return view!!
    }

    private fun convertStringToDate(eventDate: String): Date{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN)
        var date: Date? = null

        try {
            date = format.parse(eventDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date!!
    }

    override fun getCount(): Int {
        return dates.size
    }

    override fun getPosition(item: Date?): Int {
        return dates.indexOf(item)
    }

    override fun getItem(position: Int): Date {
        return dates[position]
    }
}