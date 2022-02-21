package com.example.projectend.Fragment

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.projectend.R
import com.example.projectend.activity.ProfileEditActivity
import com.example.projectend.activity.SplashActivity
import com.example.projectend.adapter.MySliderImageAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.lang.Exception


class ProfileFragment : Fragment() {

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var imageProfile: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profilePhone: TextView
    private lateinit var profileAddress: TextView
    private lateinit var editProfile: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        imageProfile = view.findViewById(R.id.profile_image)
        profileName = view.findViewById(R.id.tvProfileName)
        profileEmail = view.findViewById(R.id.tvProfileEmail)
        profilePhone = view.findViewById(R.id.tvProfilePhone)
        profileAddress = view.findViewById(R.id.tvProfileAddress)
        editProfile = view.findViewById(R.id.btnEditProfile)


        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        editProfile.setOnClickListener {
            startActivity(Intent(context, ProfileEditActivity::class.java))
        }

        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setMessage("you want logout or exit ?")
                    .setPositiveButton("Logout") { dialog, which ->

                        firebaseAuth.signOut()
                        checkUser()
                    }
                    .setNegativeButton("Cancel") { dialog, which ->

                    }
                    .show()
            }
        }
//        val user:FirebaseUser? = firebaseAuth.currentUser

//        if (user != null){
//            if (user.photoUrl != null){
//                Picasso.get().load(user.photoUrl).into(imageProfile)
//            }else{
//                Picasso.get().load("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png").into(imageProfile)
//            }
//            profileName.setText(user.displayName)
//            profileEmail.setText(user.email)
//            profilePhone.setText(user.phoneNumber)
//            profileAddress.setText(user.phoneNumber)
//        }


        return view
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            val intent = Intent(context, SplashActivity::class.java)
            startActivity(intent)

        } else {

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
                    profileName.text = name
                    profileEmail.text = email
                    profilePhone.text = phone
                    profileAddress.text = address

                    try {
                        Picasso.get().load(profileImage).into(imageProfile)
                    } catch (e: Exception) {

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

}