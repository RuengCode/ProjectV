package com.example.projectend.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectend.Fragment.AdminDashBoardFragment
import com.example.projectend.Fragment.MainFragment
import com.example.projectend.R
import com.example.projectend.databinding.ActivityFirebaseDashBoardAdminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class FirebaseDashBoardAdminActivity : AppCompatActivity() {
    private lateinit var adminBottomNavigation: BottomNavigationView
    private lateinit var binding: ActivityFirebaseDashBoardAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val adminDashBoardFragment = AdminDashBoardFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseDashBoardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
//        checkUser()
//
//        binding.logoutAdmin.setOnClickListener {
//            firebaseAuth.signOut()
//            checkUser()
//        }
        replaceFragment(adminDashBoardFragment)
        adminBottomNavigation = findViewById(R.id.admin_bottom_navigation)
        adminBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.admin_1 -> replaceFragment(adminDashBoardFragment)
                R.id.admin_2 -> replaceFragment(adminDashBoardFragment)
                R.id.admin_3 -> replaceFragment(adminDashBoardFragment)
                R.id.admin_4 -> replaceFragment(adminDashBoardFragment)
                R.id.admin_5 -> replaceFragment(adminDashBoardFragment)

            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.admin_fragment_container, fragment)
            transaction.commit()

        }
    }
//    private fun checkUser() {
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser == null){
//            startActivity(Intent(this,MemuActivity::class.java))
//            finish()
//        }
//        else{
//            val name = firebaseUser.displayName
//            val email = firebaseUser.email
//            binding.emailAdmin.text = email
//            binding.nameAdmin.text = name
//        }
//    }
}