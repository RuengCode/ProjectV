package com.example.projectend.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectend.R
import com.example.projectend.data.MyDataItem

class MyAdapter(val context: Context,val userList : List<MyDataItem>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var date : TextView
        var newCase : TextView
        var totalRecover : TextView
        var todayNew : TextView
        var totalCase : TextView
        var newDead : TextView
        var totalDeath : TextView

        init {
            date = itemView.findViewById(R.id.today_date)
            newCase = itemView.findViewById(R.id.today_recovered)
            totalRecover = itemView.findViewById(R.id.today_total_recovered)
            todayNew = itemView.findViewById(R.id.today_new)
            totalCase = itemView.findViewById(R.id.today_total_case)
            newDead = itemView.findViewById(R.id.today_new_death)
            totalDeath = itemView.findViewById(R.id.today_total_death)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_covid_today,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = userList[position].txnDate
        holder.newCase.text = userList[position].newRecovered.toString()
        holder.totalRecover.text = userList[position].totalRecovered.toString()
        holder.todayNew.text = userList[position].newCase.toString()
        holder.totalCase.text = userList[position].totalCase.toString()
        holder.newDead.text = userList[position].newDeath.toString()
        holder.totalDeath.text = userList[position].totalDeath.toString()

    }


    override fun getItemCount(): Int {
       return  userList.size
    }
}