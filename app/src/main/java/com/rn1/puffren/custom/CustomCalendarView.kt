package com.rn1.puffren.custom

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.rn1.puffren.R
import com.rn1.puffren.data.Events
import com.rn1.puffren.util.MAX_CALENDAR_DAYS
import java.text.SimpleDateFormat
import java.util.*

class CustomCalendarView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var currentDateText: TextView
    private lateinit var gridView: GridView
    private lateinit var alertDialog: AlertDialog
    private lateinit var calendarAdapter: CalendarAdapter

    private val calendar = Calendar.getInstance(Locale.TAIWAN)
    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.TAIWAN)
    private val monthFormat = SimpleDateFormat("MM", Locale.TAIWAN)
    private val yearFormat = SimpleDateFormat("yyyy", Locale.TAIWAN)
    private val eventDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN)

    private val dates = mutableListOf<Date>()
    private val eventsList = mutableListOf<Events>()

    private var selectedDate: Date? = null

    init {
        initialLayout()
        setUpCalendar()
    }

    private fun initialLayout() {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.calendar_layout, this)

        nextButton = view.findViewById(R.id.bt_next)
        previousButton = view.findViewById(R.id.bt_previous)
        currentDateText = view.findViewById(R.id.text_current_date)
        gridView = view.findViewById(R.id.grid)

        previousButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            setUpCalendar()
        }

        nextButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            setUpCalendar()
        }

        setupGridItemClickListener()
    }

    private fun setupGridItemClickListener() {
        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val builder = AlertDialog.Builder(context)
                builder.setCancelable(true)
                val addView = LayoutInflater.from(parent.context).inflate(R.layout.add_new_event_layout, null)
                val eventName = addView.findViewById<EditText>(R.id.edit_type_event)
                val eventTime = addView.findViewById<TextView>(R.id.text_event_time)
                val setTime = addView.findViewById<ImageButton>(R.id.image_bt_set_time)
                val addEvent = addView.findViewById<Button>(R.id.bt_add_event)

                setTime.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    val hours = calendar.get(Calendar.HOUR_OF_DAY)
                    val minutes = calendar.get(Calendar.MINUTE)
                    val timePickerDialog = TimePickerDialog(addView.context, R.style.Theme_AppCompat_Dialog, TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
                        val c = Calendar.getInstance()
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        c.set(Calendar.MINUTE, minute)
                        c.timeZone = TimeZone.getDefault()
                        val hFormat = SimpleDateFormat("K:mm a", Locale.TAIWAN)
                        val eTime = hFormat.format(c.time)
                        eventTime.text = eTime
                    }, hours, minutes, false)
                }

                val date = eventDateFormat.format(dates.get(position))
                val month = monthFormat.format(dates.get(position))
                val year = yearFormat.format(dates.get(position))

                addEvent.setOnClickListener {
                    saveEvent(eventName.text.toString(), eventTime.text.toString(), date, month, year)
                    setUpCalendar()
                    alertDialog.dismiss()
                }

                builder.setView(addView)
                alertDialog = builder.create()
                alertDialog.show()
            }
    }

    @SuppressLint("InflateParams")
    private val onDayItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        val addView = LayoutInflater.from(parent.context).inflate(R.layout.add_new_event_layout, null)
        val eventName = addView.findViewById<EditText>(R.id.edit_type_event)
        val eventTime = addView.findViewById<TextView>(R.id.text_event_time)
        val setTime = addView.findViewById<ImageButton>(R.id.image_bt_set_time)
        val addEvent = addView.findViewById<Button>(R.id.bt_add_event)

        setTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hours = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(addView.context,
                R.style.Theme_AppCompat_Dialog,
                TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
                    val c = Calendar.getInstance()
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    c.set(Calendar.MINUTE, minute)
                    c.timeZone = TimeZone.getDefault()
                    val hfromate = SimpleDateFormat("K:mm a", Locale.TAIWAN)
                    val eTime = hfromate.format(c.time)
                    eventTime.text = eTime
                },
                hours,
                minutes,
                false
            )
        }

        val date = eventDateFormat.format(dates[position])
        val month = monthFormat.format(dates[position])
        val year = yearFormat.format(dates[position])

        addEvent.setOnClickListener {
            saveEvent(eventName.text.toString(), eventTime.text.toString(), date, month, year)
            setUpCalendar()
            alertDialog.dismiss()
        }

        builder.setView(addView)
        alertDialog = builder.create()
        alertDialog.show()
    }

    private fun setUpCalendar() {
        val currentDate = dateFormat.format(calendar.time)
        currentDateText.text = currentDate

        dates.clear()
        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        // 從星期日開始算 所以要-1
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        collectEventsPerMonth(monthFormat.format(calendar.time), yearFormat.format(calendar.time))

        while (dates.size < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendarAdapter = CalendarAdapter(context, dates, calendar, eventsList, selectedDate)
        gridView.adapter = calendarAdapter

    }

    private fun saveEvent(event: String, time: String, date: String, month: String, year: String){
        //TODO add event
        eventsList.add(Events(event, time, date, month, year))
        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show()
    }

    private fun collectEventsPerMonth(month: String, year: String){

    }

//    constructor(context: Context?) : super(context)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

}