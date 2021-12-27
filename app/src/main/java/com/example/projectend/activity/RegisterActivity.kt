package com.example.projectend.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.projectend.R
import com.example.projectend.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
        //ViewBinding
        private lateinit var binding : ActivityRegisterBinding
        //ActionBar


        //ProgressDialog
        private lateinit var progressDialog:ProgressDialog

        //FirebaseAuth
        private lateinit var firebaseAuth: FirebaseAuth
        private var email =""
        private var password = ""
        private var name = ""
        private var address = ""
        private var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin signup
        binding.singUpBtn.setOnClickListener {
            //validate data
            validate()
        }

    }

    private fun validate() {
        //get data
        name = binding.RegisterName.text.toString().trim()
        email = binding.RegisterEmail.text.toString().trim()
        password = binding.RegisterPassword.text.toString().trim()
        address = binding.RegisterAddress.text.toString().trim()
        phone = binding.RegisterPhone.text.toString().trim()

        //validate

        if(TextUtils.isEmpty(name)){
            binding.RegisterName.error = "กรุณากรอกชื่อ"
        }
        else if (name.length < 6){
            binding.RegisterName.error = "ชื่อต้องไม่น้อยกว่า 6 ตัว"
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.RegisterEmail.error = "ไม่ถูกต้องตามหลักอีเมล"
        }
        else if (TextUtils.isEmpty(password)){
            //password isn't entered
            binding.RegisterPassword.error = "กรุณากรอกรหัสผ่าน"
        }
        else if (password.length < 7){
            //password length is less than 6
            binding.RegisterPassword.error = "รหัสผ่านต้องเท่ากับหรือมากกว่า 8 ตัว"
        }
        else if(TextUtils.isEmpty(address)){
            binding.RegisterAddress.error = "กรุณากรอกที่อยู่"
        }
        else if(TextUtils.isEmpty(phone)){
            binding.RegisterPhone.error = "กรุณากรอกเบอร์โทร"
        }
        else if(phone.length > 10){
            binding.RegisterPhone.error = "กรอกเกินจำนวน"
        }
        else if(phone.length < 10){
            binding.RegisterPhone.error = "กรุณากรอกเบอร์โทรให้ครบ"
        }

        else{

            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"สมัครสมาชิกสำเร็จ",Toast.LENGTH_SHORT).show()
                Log.d("main_email",email.toString())
                //open profile
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this,"สมัครสมาชิกไม่สำเร็จ",Toast.LENGTH_SHORT).show()
            }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()//go back to previous activity,when back button of activity clicked
        return super.onSupportNavigateUp()
    }
}