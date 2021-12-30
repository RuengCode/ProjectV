package com.example.projectend.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.example.projectend.R
import com.example.projectend.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var imageUri : Uri
    private lateinit var imageProfile:ImageView
    private lateinit var profileName : EditText
    private lateinit var profileEmail : EditText
    private lateinit var profilePhone : EditText
    private lateinit var profileAddress : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile,container,false)

        imageProfile = view.findViewById(R.id.profile_image)
        profileName = view.findViewById(R.id.tvProfileName)
        profileEmail = view.findViewById(R.id.tvProfileEmail)
        profilePhone = view.findViewById(R.id.tvProfilePhone)
        profileAddress = view.findViewById(R.id.tvProfileAddress)

        firebaseAuth = FirebaseAuth.getInstance()
        val user:FirebaseUser? = firebaseAuth.currentUser

        if (user != null){
            if (user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(imageProfile)
            }else{
                Picasso.get().load("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png").into(imageProfile)
            }
            profileName.setText(user.displayName)
            profileEmail.setText(user.email)
            profilePhone.setText(user.phoneNumber)
            profileAddress.setText(user.phoneNumber)
        }



       return view
    }



}