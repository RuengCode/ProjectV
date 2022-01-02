package com.example.projectend.activity

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.animation.AnimationUtils

import android.widget.TextView
import android.widget.Toast
import com.example.projectend.Fragment.ForgotPasswordFragment
import com.example.projectend.Fragment.LoadingFragment
import com.example.projectend.R
import com.example.projectend.databinding.ActivityFirebaseLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.regex.Pattern

class FirebaseLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirebaseLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()



        binding.NewRegister.setOnClickListener {
            startActivity(Intent(this, FirebaseRegisterActivity::class.java))
        }
        binding.Login.setOnClickListener {
            validateData()

        }
        val btnForgotPassword : TextView = findViewById(R.id.ForgotPassword)
        btnForgotPassword.setOnClickListener {
            val forgotPasswordDialog = ForgotPasswordFragment()

            forgotPasswordDialog.show(supportFragmentManager,"ForgotPassword")
        }


    }

    private var email = ""
    private var password = ""
    private fun validateData() {
        email = binding.LoginEmail.text.toString().trim()
        password = binding.LoginPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.LoginEmail.error = "กรุณากรอกอีเมลให้ถูกต้อง"
        } else if (password.isEmpty()) {
            binding.LoginPassword.error = "กรุณากรอกรหัสผ่าน"
        } else {
            loginUser()
        }
    }

    private fun loginUser() {
        val loadingFragment = LoadingFragment()
        loadingFragment.show(supportFragmentManager,"ForgotPassword")


        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checkUser()

            }
            .addOnFailureListener {

                Toast.makeText(this, "Login Fail", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {

        val firebaseUser = firebaseAuth.currentUser
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val userType = snapshot.child("userType").value
                    if (userType == "user") {
                        startActivity(Intent(this@FirebaseLoginActivity, MemuActivity::class.java))
                    } else if (userType == "admin") {
                        startActivity(
                            Intent(
                                this@FirebaseLoginActivity,
                                FirebaseDashBoardAdminActivity::class.java
                            )
                        )

                    }
                }

                override fun onCancelled(error: DatabaseError) {


                }
            })
    }
}