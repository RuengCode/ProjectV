package com.example.projectend.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.projectend.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListHomeActivity : AppCompatActivity() {
    private lateinit var addList : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_home)
        addList = findViewById(R.id.floating_action_button)
        addList.setOnClickListener {
            startActivity(Intent(this,AddDataToListHomeActivity::class.java))
        }
    }
}