package com.example.projectend.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.projectend.R
import com.example.projectend.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    //ViewBinding

    private lateinit var binding: ActivityLoginBinding

    //ActionBar


    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar


        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser();

        //handle click, open SignUpActivity
        binding.NewRegister.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        //handle click, begin login
        binding.Login.setOnClickListener{
            //before logging in, validate data
            validaData()
        }
    }
        private  fun validaData() {
            //get data
            email = binding.LoginEmail.text.toString().trim()
            password = binding.LoginPassword.text.toString().trim()

            //validate data
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                //invalid email format
                binding.LoginEmail.error = "Invalid email format"
            }
            else if (TextUtils.isEmpty(password)){
                //no password entered
                binding.LoginPassword.error = "Please enter password"
            }
            else{
                //data is validated, begin login
                firebaseLogin()
            }
        }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"LoggedIn as $email",Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener { e ->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
            //if user is already logged in go to profile activity
            //get current user
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null){
                //user is already logged in
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
        }

}