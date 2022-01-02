package com.example.projectend.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.projectend.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.w3c.dom.Text
import java.util.*

class AddDataToListHomeActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener {
    var day = 0
    var month = 0
    var year = 0
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    private lateinit var setDate1: ImageView
    private lateinit var setViewDate : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data_to_list_home)
        setDate1 = findViewById(R.id.setDate)
        setViewDate = findViewById(R.id.edSetDate)
        pickDate()

    }

    private fun pickDate() {
        setDate1.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    private fun getDateTimeCalendar() {
        val cal  = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.JANUARY)
        year = cal.get(Calendar.YEAR)

        Log.d("main_month",month.toString())

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month +1
        savedYear = year+543
        getDateTimeCalendar()

        setViewDate.setText("$savedDay/$savedMonth/$savedYear")
    }
}