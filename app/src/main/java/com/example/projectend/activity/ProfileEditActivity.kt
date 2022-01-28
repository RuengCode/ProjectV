package com.example.projectend.activity

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.projectend.Fragment.ProfileFragment
import com.example.projectend.R
import com.example.projectend.databinding.ActivityProfileEditBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import retrofit2.http.Url
import java.lang.Exception

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var edImageProfile: ImageView
    private lateinit var edProfileName: EditText
    private lateinit var edProfileEmail: EditText
    private lateinit var edProfilePhone: EditText
    private lateinit var edProfileAddress: EditText
    private lateinit var btnSuccess: Button
    private val profileFragment = ProfileFragment()
    private var imageUri: Uri? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        edImageProfile = findViewById(R.id.edProfile_image)
        edProfileName = findViewById(R.id.edProfileName)
        edProfileEmail = findViewById(R.id.edProfileEmail)
        edProfilePhone = findViewById(R.id.edProfilePhone)
        edProfileAddress = findViewById(R.id.edProfileAddress)
        btnSuccess = findViewById(R.id.btnSuccess)

        edImageProfile.setOnClickListener {
            showImageAttachMenu()
        }
        btnSuccess.setOnClickListener {
            validateData()
        }
    }

    private var name = ""
    private var phone = ""
    private var address = ""

    private fun validateData() {
        name = edProfileName.text.toString().trim()
        phone = edProfilePhone.text.toString().trim()
        address = edProfileAddress.text.toString().trim()
        if (name.isEmpty()) {
            edProfileName.error = "กรุณาใส่ชื่อใหม่"
        } else if (phone.isEmpty()) {
            edProfilePhone.error = "กรุณาใส่เบอร์ใหม่"
        } else if (address.isEmpty()) {
            edProfileAddress.error = "กรุณาใส่ชที่อยู่ใหม่"
        } else {
            if (imageUri == null) {
                updateProfile("")
            } else {
                uploadImage()
            }
        }

    }

    private fun uploadImage() {
        progressDialog.setMessage("Uploading profile image")
        progressDialog.show()
                                                                                                                                                                                                                                                                                                                                                                                val filePathAndName = "ProfileImages/" + firebaseAuth.uid
        val reference = FirebaseStorage.getInstance().getReference(filePathAndName)
        reference.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadImageUrl = "${uriTask.result}"
                updateProfile(uploadImageUrl)
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Fail....!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateProfile(uploadImageUrl: String) {
        progressDialog.setMessage("Updating profile...")
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["name"] = "$name"
        hashMap["phone"] = "$phone"
        hashMap["address"] = "$address"


        if (imageUri != null) {
            hashMap["profileImage"] = uploadImageUrl
        }
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()

                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Fail..Fail..!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("name").value}"
                    val email = "${snapshot.child("email").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val phone = "${snapshot.child("phone").value}"
                    val address = "${snapshot.child("address").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val userType = "${snapshot.child("userType").value}"

//                    val  formattedDate =

                    //set data
                    edProfileName.setText(name)
                    edProfileEmail.setText(email)
                    edProfilePhone.setText(phone)
                    edProfileAddress.setText(address)


                    try {
                        Picasso.get().load(profileImage).into(edImageProfile)
                    } catch (e: Exception) {

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun showImageAttachMenu() {
        val popupMenu = PopupMenu(this, edImageProfile)
        popupMenu.menu.add(Menu.NONE, 0, 0, "Camera")
        popupMenu.menu.add(Menu.NONE, 1, 1, "Gallery")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == 0) {
                pickImageCamera()
            } else if (id == 1) {
                pickImageGallery()
            }
            true
        }
    }

    private fun pickImageCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
//                imageUri = data!!.data

                edImageProfile.setImageURI(imageUri)
            } else {
                Toast.makeText(this, "ไม่ๆๆๆๆๆๆ", Toast.LENGTH_SHORT).show()
            }
        }

    )
    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data!!.data

                edImageProfile.setImageURI(imageUri)
            } else {
                Toast.makeText(this, "ไม่ๆๆๆๆๆๆ", Toast.LENGTH_SHORT).show()
            }
        }
    )
}