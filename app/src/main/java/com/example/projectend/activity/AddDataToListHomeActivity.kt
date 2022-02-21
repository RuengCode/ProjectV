package com.example.projectend.activity

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.projectend.R
import com.example.projectend.databinding.ActivityAddDataToListHomeBinding
import com.example.projectend.databinding.ActivityFirebaseRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import java.util.*

class AddDataToListHomeActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    var day = 0
    var month = 0
    var year = 0
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var countDay = 1
    private lateinit var binding: ActivityAddDataToListHomeBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var setDate1: ImageView
    private lateinit var setViewDate: EditText
    private lateinit var dayId: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnSubmit: Button
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataToListHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        setDate1 = findViewById(R.id.setDate)
        setViewDate = findViewById(R.id.edSetDate)
        pickDate()


        var firebaseDatabase = FirebaseDatabase.getInstance().reference

        binding.submitHome.setOnClickListener {
            addDataToFireBase()
            startActivity(Intent(this, ListHomeActivity::class.java))


        }

    }

    private var date = ""
    private var name = ""
    private var tempCC = ""
    private var tempPi = ""
    private var tempRR = ""
    private var tempSpO2 = ""
    private var tempPR = ""

    private fun addDataToFireBase() {
        val uid = firebaseAuth.uid
        val hasMap: HashMap<String, Any?> = HashMap()

        date = binding.edSetDate.text.toString().trim()
        name = binding.edHomeName.text.toString().trim()
        tempCC = binding.edHomeTempCC.text.toString().trim()
        tempPi = binding.edHomeTempPi.text.toString().trim()
        tempRR = binding.edHomeTempRR.text.toString().trim()
        tempSpO2 = binding.edHomeTempSpO2.text.toString().trim()
        tempPR = binding.edHomeTempPR.text.toString().trim()

        hasMap["uid"] = uid
        hasMap["date"] = date
        hasMap["name"] = name
        hasMap["tempCC"] = tempCC
        hasMap["tempPi"] = tempPi
        hasMap["tempRR"] = tempRR
        hasMap["tempSpO2"] = tempSpO2
        hasMap["tempPR"] = tempPR

        val ref = FirebaseDatabase.getInstance().getReference("Test")
        ref.child(name)
            .setValue(hasMap)
            .addOnSuccessListener {


                startActivity(Intent(this, ListHomeActivity::class.java))
            }
            .addOnFailureListener { e ->

                Toast.makeText(this, "failed Saveing user", Toast.LENGTH_SHORT).show()
            }

    }

    private fun pickDate() {
        setDate1.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.JANUARY)
        year = cal.get(Calendar.YEAR)

        Log.d("main_month", month.toString())

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year + 543
        getDateTimeCalendar()

        setViewDate.setText("$savedDay/$savedMonth/$savedYear")
    }
}