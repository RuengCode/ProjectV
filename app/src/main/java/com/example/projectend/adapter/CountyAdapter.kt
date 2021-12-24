package com.example.projectend.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectend.R
import com.example.projectend.data.Covid77Item
import com.example.projectend.data.MyDataItem

class CountyAdapter (val context: Context, val countyList : List<Covid77Item>) : RecyclerView.Adapter<CountyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var newCase: TextView
        var totalCase: TextView
        var dead: TextView
        var totalDead: TextView

        init {
            name = itemView.findViewById(R.id.tvCname)
            newCase = itemView.findViewById(R.id.tvCnewCase)
            totalCase = itemView.findViewById(R.id.tvCtotalCase)
            dead = itemView.findViewById(R.id.tvCdeath)
            totalDead = itemView.findViewById(R.id.tvCdeathAll)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.item_covid_county, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = countyList[position].province
        holder.newCase.text = countyList[position].newCase.toString()
        holder.totalCase.text = countyList[position].totalCase.toString()
        holder.dead.text = countyList[position].newDeath.toString()
        holder.totalDead.text = countyList[position].totalDeath.toString()

    }

    override fun getItemCount(): Int {
        return countyList.size
    }
}