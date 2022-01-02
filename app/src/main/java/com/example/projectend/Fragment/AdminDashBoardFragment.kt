package com.example.projectend.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projectend.R
import com.example.projectend.activity.ListUserActivity
import com.example.projectend.activity.SplashActivity
import com.example.projectend.adapter.ListUserAdapter
import com.example.projectend.data.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.techiness.progressdialoglibrary.ProgressDialog
import java.lang.Exception


class AdminDashBoardFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adminImage: ImageView
    private lateinit var adminName: TextView
    private lateinit var adminEmail: TextView
    private lateinit var itemCountUser: TextView
    private lateinit var listUserAll: LinearLayout
    private lateinit var userArrayList : ArrayList<User>
    private lateinit var dbRef : DatabaseReference
    private lateinit var progressDialog: ProgressDialog
    private lateinit var refresh : SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_dash_board, container, false)
        adminImage = view.findViewById(R.id.adminImage)
        adminName = view.findViewById(R.id.adminName)
        adminEmail = view.findViewById(R.id.adminEmail)
        listUserAll = view.findViewById(R.id.itemListUserAll)
        itemCountUser = view.findViewById(R.id.userItemCountList)
        refresh = view.findViewById(R.id.refreshAdminMenu)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()
        listUserAll.setOnClickListener {
            startActivity(Intent(context, ListUserActivity::class.java))
        }
        userArrayList = arrayListOf<User>()
        getUserData()

        val logoutAdmin : Button = view.findViewById(R.id.logoutAdmin)
        logoutAdmin.setOnClickListener {
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setMessage("you want logout or exit ?")
                    .setPositiveButton("Logout"){dialog, which->

                        firebaseAuth.signOut()
                        checkUser()
                    }
                    .setNegativeButton("Cancel"){dialog, which->

                    }
                    .show()
            }
        }
        refreshData()
        return view

    }

    private fun refreshData() {

        refresh.setOnRefreshListener {
            Toast.makeText(context,"เรียบร้อยจ้า",Toast.LENGTH_SHORT).show()
            refresh.isRefreshing = false
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            val intent = Intent(context, SplashActivity::class.java)
            startActivity(intent)

        } else {

        }
    }

    private fun getUserData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val counter = snapshot.childrenCount

                itemCountUser.text = counter.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


//        dbRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    for (userSnapshot in snapshot.children) {
//                        val user = userSnapshot.getValue(User::class.java)
//                        userArrayList.add(user!!)
//                    }
//                    itemCountUser.text = ListUserAdapter(userArrayList).itemCount.toString()
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
    }


    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("name").value}"
                    val email = "${snapshot.child("email").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"

                    adminName.text = name
                    adminEmail.text = email

                    try {
                        Picasso.get().load(profileImage).into(adminImage)
                    } catch (e: Exception) {

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }


}