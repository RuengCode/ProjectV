package com.example.projectend.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectend.R
import com.example.projectend.data.User
import com.example.projectend.data.home
import com.google.firebase.auth.FirebaseAuth

class HomeAdapter(val context: Context, private val homeList: ArrayList<home>) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var builder: AlertDialog.Builder

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView
        val nameHome: TextView
        val tempCC: TextView
        val tempRR: TextView
        val tempPR: TextView
        val tempSpO2: TextView

        init {

            date = itemView.findViewById(R.id.date)
            nameHome = itemView.findViewById(R.id.nameHome)
            tempCC = itemView.findViewById(R.id.tempCC)
            tempRR = itemView.findViewById(R.id.tempPR)
            tempPR = itemView.findViewById(R.id.tempRR)
            tempSpO2 = itemView.findViewById(R.id.tempSpO2)

            builder = AlertDialog.Builder(context)

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_home, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeAdapter.MyViewHolder, position: Int) {
        val currentItem = homeList[position]

        holder.nameHome.text = currentItem.name

    }

    override fun getItemCount(): Int = homeList.size
}
