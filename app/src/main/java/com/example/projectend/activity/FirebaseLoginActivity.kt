package com.example.projectend.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
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
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.NewRegister.setOnClickListener {
            startActivity(Intent(this, FirebaseRegisterActivity::class.java))
        }
        binding.Login.setOnClickListener {


            validateData()
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
        progressDialog.setMessage("Logging In....")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checkUser()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Login Fail", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        progressDialog.setMessage("Checking User...")
        val firebaseUser = firebaseAuth.currentUser
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()
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