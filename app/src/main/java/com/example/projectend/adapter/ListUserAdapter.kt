package com.example.projectend.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.projectend.R
import com.example.projectend.data.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ListUserAdapter(val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.MyViewHolder>() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var builder: AlertDialog.Builder

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageUser: ImageView
        val nameUser: TextView
        val emailUser: TextView
        val delete: Button

        init {

            imageUser = itemView.findViewById(R.id.userListImage)
            nameUser = itemView.findViewById(R.id.userListName)
            emailUser = itemView.findViewById(R.id.userListEmail)
            delete = itemView.findViewById(R.id.deleteUser1)

            builder = AlertDialog.Builder(context)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.nameUser.text = currentItem.name
        holder.emailUser.text = currentItem.email

        firebaseAuth = FirebaseAuth.getInstance()

        Picasso.get().load(currentItem.profileImage).into(holder.imageUser)
        holder.delete.setOnClickListener {
            builder.setTitle("ลบข้อมูล")
                .setMessage("คุณต้องการลบข้อมูล User หรือไม่ ?")
                .setCancelable(true)
                .setIcon(R.drawable.zombie)
                .setPositiveButton("ลบ") { dialogInterface, it ->
                    var ref = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(userList[position].uid!!)
                    ref.removeValue()
                    userList.clear()

                }
                .setNegativeButton("กลับ") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}