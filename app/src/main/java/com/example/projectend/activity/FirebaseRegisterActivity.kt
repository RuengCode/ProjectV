package com.example.projectend.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.projectend.R
import com.example.projectend.databinding.ActivityFirebaseRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirebaseRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.singUpBtn.setOnClickListener {

            validateData()
        }
    }

    private var name = ""
    private var email = ""
    private var password = ""
    private var address = ""
    private var phone = ""

    private fun validateData() {
        name = binding.RegisterName.text.toString().trim()
        email = binding.RegisterEmail.text.toString().trim()
        password = binding.RegisterPassword.text.toString().trim()
        address = binding.RegisterAddress.text.toString().trim()
        phone = binding.RegisterPhone.text.toString().trim()

        //validate

        if (TextUtils.isEmpty(name)) {
            binding.RegisterName.error = "กรุณากรอกชื่อ"
        } else if (name.length <= 10) {
            binding.RegisterName.error = "ชื่อต้องไม่น้อยกว่า 10 ตัว"
        }
        else if (name.length > 30){
            binding.RegisterName.error = "ชื่อต้องต่ำกว่า 30 ตัว"
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.RegisterEmail.error = "ไม่ถูกต้องตามหลักอีเมล"
        } else if (TextUtils.isEmpty(password)) {
            binding.RegisterPassword.error = "กรุณากรอกรหัสผ่าน"
        } else if (password.length < 7) {
            binding.RegisterPassword.error = "รหัสผ่านต้องเท่ากับหรือมากกว่า 8 ตัว"
        } else if (TextUtils.isEmpty(address)) {
            binding.RegisterAddress.error = "กรุณากรอกที่อยู่"
        } else if (TextUtils.isEmpty(phone)) {
            binding.RegisterPhone.error = "กรุณากรอกเบอร์โทร"
        } else if (phone.length > 10) {
            binding.RegisterPhone.error = "กรอกเกินจำนวน"
        } else if (phone.length < 10) {
            binding.RegisterPhone.error = "กรุณากรอกเบอร์โทรให้ครบ"
        } else {

            createUserAccount()
        }
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
        progressDialog.setMessage("Saving user info...")

        val timeStamp = System.currentTimeMillis()
        val uid = firebaseAuth.uid
        val hasMap: HashMap<String, Any?> = HashMap()
        hasMap["uid"] = uid
        hasMap["email"] = email
        hasMap["name"] = name
        hasMap["address"] = address
        hasMap["phone"] = phone
        hasMap["profileImage"] = ""
        hasMap["userType"] = "user"
        hasMap["timeStamp"] = timeStamp

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hasMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MemuActivity::class.java))
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "failed Saveing user", Toast.LENGTH_SHORT).show()
            }

    }
}