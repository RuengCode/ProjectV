package com.example.projectend.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.projectend.R
import com.example.projectend.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding : ActivityProfileBinding




    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email =""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click logout
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
      //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //user not null, user is logged in, get user info
            val email = firebaseUser.email
            //set to text view
            binding.showTxt.text = email
        }
        else{
            //user not null, user is loggedin, goto login activity
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}