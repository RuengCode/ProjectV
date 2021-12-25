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

class LoginActivity : AppCompatActivity() {

    //ViewBibding
    private lateinit var binding:ActivtyloginBinding

    //ActionBar
    private lateinit var actionBar:ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivtyloginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        //configure actionbar
        actionBar= supportActionBar!!
        actionBar.title="Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser();

        //handle click, open SignUpActivity
        binding.noAccounTv.setOnClicklistener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        //handle click, begin login
        binding.loginBtn.setOnClicklistener{
            //before logging in, validate data
            validaData()
        }
    }
        private  fun validaData() {
            //get data
            email = binding.emailEt.text.toString().trim()
            password = binding.passwordEt.text.toString().trim()

            //validate data
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                //invalid email format
                binding.emailEt.setError("Invalid email format")
            }
            else if (TextUtils.isEmpty(password)){
                //no password entered
                binding.password.setError("Please enter password")
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