package com.rn1.puffren.custom

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.rn1.puffren.R
import com.rn1.puffren.data.Events
import kotlinx.android.synthetic.main.item_day_cell.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    context: Context,
    private val dates: List<Date>,
    private val currentDate: Calendar,
    private val events: List<Events>
) : ArrayAdapter<Date>(context, R.layout.item_day_cell) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val today = Calendar.getInstance(Locale.TAIWAN)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val monthDate = dates[position]
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = monthDate
        val dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)
        val displayDay = dateCalendar.get(Calendar.DATE)
        val displayMonth = dateCalendar.get(Calendar.MONTH) + 1
        val displayYear = dateCalendar.get(Calendar.YEAR)
        val currentDay = currentDate.get(Calendar.DATE)
        val currentMonth = currentDate.get(Calendar.MONTH) + 1
        val currentYear = currentDate.get(Calendar.YEAR)

        var view = convertView

        if (view == null) {
            view = inflater.inflate(R.layout.item_day_cell, parent, false)
        }

        view?.let {

            if(displayYear == today.get(Calendar.YEAR)
                && displayMonth == (today.get(Calendar.MONTH) + 1)
                && displayDay == today.get(Calendar.DATE)) {
                it.text_calendar_day.setTextColor(context.resources.getColor(R.color.orange_ffa626))
            }

            if (displayMonth == currentMonth && displayYear == currentYear) {
                it.setBackgroundColor(context.resources.getColor(R.color.bg_color_yellow))
            } else {
                it.setBackgroundColor(Color.parseColor("#f5f5f5"))
                it.text_calendar_day.setTextColor(Color.parseColor("#ffffff"))
            }

            val dayNumber = view.findViewById<TextView>(R.id.text_calendar_day)
            val eventNumber = view.findViewById<TextView>(R.id.text_events_id)
            dayNumber.text = dayNo.toString()

            val eventCalendar = Calendar.getInstance()
            val arrayList = mutableListOf<String>()

            for (event in events) {
                eventCalendar.time = convertStringToDate(event.date!!)
                if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH)
                    && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                    && displayYear == eventCalendar.get(Calendar.YEAR)) {
                    arrayList.add(event.event!!)
                    eventNumber.text = "${arrayList.size} events"
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