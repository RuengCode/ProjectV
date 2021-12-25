package com.example.projectend.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.projectend.R

class RegisterActivity : AppCompatActivity() {
        //ViewBinding
        private lateinit var binding: ActivityRegisterBinding

        //ActionBar
        private lateinit var actionBar: ActionBar

        //ProgressDialog
        private lateinit var progressDialog:ProgressDialog

        //FirebaseAuth
        private lateinit var firebaseAuth: FirebaseAuth
        private var email =""
        private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Actionbar,enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowCustomEnabled(true)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle clck, begin signup
        binding.singUpBtn.setOnClicklistner {
            //validate data
            validate()
        }

    }

    private fun validate() {
        //get data
        email = binding.emailEt.text.toString().trim()
        email = binding.passwordEt.text.toString().trim()

        //validata
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.setError ("Invalid email format")
        }
        else if (TextUtils.isEmpty(password)){
            //password isn't entered
            binding.passwordEt.setError ("Please enter pasword")
        }
        else if (password.length <6){
            //password length is less than 6
            binding.passwordEt.setError ("Password muts atleast 6 chracters long")
        }
        else{
            //data is valid, continue signuo
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.creteUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with email $email",Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
            .addOnSuccessListener { e->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()//go back to previous activity,when back button of activity clicked
        return super.onSupportNavigateUp()
    }
}