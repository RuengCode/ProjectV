package com.example.projectend.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectend.databinding.ActivityFirebaseDashBoardAdminBinding
import com.google.firebase.auth.FirebaseAuth

class FirebaseDashBoardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirebaseDashBoardAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseDashBoardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutAdmin.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this,MemuActivity::class.java))
            finish()
        }
        else{
            val name = firebaseUser.displayName
            val email = firebaseUser.email
            binding.emailAdmin.text = email
            binding.nameAdmin.text = name
        }
    }
}