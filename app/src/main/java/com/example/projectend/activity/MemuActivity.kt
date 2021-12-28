package com.example.projectend.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.projectend.Fragment.CountyFragment
import com.example.projectend.Fragment.CovidTodayFragment
import com.example.projectend.Fragment.MainFragment
import com.example.projectend.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MemuActivity : AppCompatActivity() {
    private lateinit var  bottonNavigation : BottomNavigationView

    private val mainFragment = MainFragment()
    private val covidTodayFragment = CovidTodayFragment()
    private val countyFragment = CountyFragment()
    private lateinit var  menu1 : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memu)

        replaceFragment(mainFragment)

        bottonNavigation = findViewById(R.id.bottom_navigation)
        bottonNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_1 -> replaceFragment(mainFragment)
                R.id.page_2 -> replaceFragment(covidTodayFragment)
                R.id.page_3 -> replaceFragment(countyFragment)

            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()

        }
    }
}