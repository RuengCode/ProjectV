package com.example.projectend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val txt = findViewById<TextView>(R.id.text1)
        txt.setOnClickListener {
            val dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager,"Hello world")
        }

    }
}