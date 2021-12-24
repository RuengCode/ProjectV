package com.example.projectend.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectend.Fragment.CovidTodayFragment
import com.example.projectend.Fragment.MainFragment
import com.example.projectend.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MemuActivity : AppCompatActivity() {
    private lateinit var  bottonNavigation : BottomNavigationView

    private val mainFragment = MainFragment()
    private val covidTodayFragment = CovidTodayFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memu)

        replaceFragment(mainFragment)

        bottonNavigation = findViewById(R.id.bottom_navigation)
        bottonNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_1 -> replaceFragment(mainFragment)
                R.id.page_2 -> replaceFragment(covidTodayFragment)
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