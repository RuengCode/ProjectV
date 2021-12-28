package com.example.projectend.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.projectend.R
import com.example.projectend.databinding.ActivityContactBinding
import com.example.projectend.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityForgotPasswordBinding

    //firebase dialog
    private lateinit var firebaseAuth: FirebaseAuth

    //Progress Dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth= FirebaseAuth.getInstance()

        //init/setup progress
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, begin password recovery process
        binding.SubmitLogin.setOnClickListener {
            validaData()
        }
    }
    private var email1=""
    private fun validaData() {
        //get data
        email1 = binding.SubmitLogin.text.toString().trim()

        //validate
        if (email1.isEmpty()){
            Toast.makeText(this,"Enter email...",Toast.LENGTH_SHORT).show()
        }
        else if (Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            Toast.makeText(this,"Invalid  email pattern...",Toast.LENGTH_SHORT).show()
        }
        else {
            recoverPassword()
        }
    }

    private fun recoverPassword() {
        progressDialog.setMessage("Sending password reset instructions to $email1")
        progressDialog.show()
        firebaseAuth.sendPasswordResetEmail(email1)
            .addOnSuccessListener {
                //sent
                progressDialog.dismiss()
                Toast.makeText(this,"Instructions sent to \n$email1",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e->
                //failed
                Toast.makeText(this,"Failed to send due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }
}