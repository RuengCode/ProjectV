package com.example.projectend.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectend.CustomDialogFragment
import com.example.projectend.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rateMeTextView.setOnClickListener{
            var dialog = CustomDialogFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }
    }

}