package com.example.projectend.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectend.R
import com.example.projectend.adapter.HomeAdapter
import com.example.projectend.data.home
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListHomeActivity : AppCompatActivity() {
    private lateinit var addList : FloatingActionButton
    private lateinit var dbRef: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var homeArrayList: ArrayList<home>
    private lateinit var adapterV: HomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_home)
        addList = findViewById(R.id.floating_action_button)
        addList.setOnClickListener {
            startActivity(Intent(this,AddDataToListHomeActivity::class.java))

        }
        userRecyclerView = findViewById(R.id.rvHome)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        firebaseAuth = FirebaseAuth.getInstance()

        homeArrayList = arrayListOf<home>()
        getUserData()

    }
    private fun getUserData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Test")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {

                        val home = userSnapshot.getValue(home::class.java)

                        homeArrayList.add(home!!)

                    }

                    adapterV = HomeAdapter(this@ListHomeActivity, homeArrayList)
                    userRecyclerView.adapter = adapterV

                    adapterV.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}