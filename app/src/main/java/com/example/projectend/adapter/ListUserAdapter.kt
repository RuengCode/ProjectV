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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ListUserAdapter(val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.MyViewHolder>() {
    private lateinit var firebaseAuth: FirebaseAuth

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageUser: ImageView
        val nameUser: TextView
        val emailUser: TextView

        init {
            firebaseAuth = FirebaseAuth.getInstance()
            imageUser = itemView.findViewById(R.id.userListImage)
            nameUser = itemView.findViewById(R.id.userListName)
            emailUser = itemView.findViewById(R.id.userListEmail)
            val delete1: Button = itemView.findViewById(R.id.deleteUser1)
            delete1.setOnClickListener { popupMenus(it) }

        }

        private fun popupMenus(itemView: View ) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(context, itemView)
            popupMenus.inflate(R.menu.show_menu)

            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(context).inflate(R.layout.add_item, null)
                        val name = v.findViewById<EditText>(R.id.userName)
                        val email = v.findViewById<EditText>(R.id.userNo)
                        AlertDialog.Builder(context)
                            .setView(v)
                            .setPositiveButton("Ok") { dialog, _ ->
                                position.name = name.text.toString()
                                position.email = email.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context,
                                    "User Information is Edited",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete -> {

                        AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.z_heal)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes") { dialog, _ ->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context,
                                    "Deleted this Information",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                                val ref = FirebaseDatabase.getInstance().getReference("Users")
                                ref.child(position.name!!)
                                    .removeValue()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else -> true

                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
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


        Picasso.get().load(currentItem.profileImage).into(holder.imageUser)


    }

    override fun getItemCount(): Int {
        return userList.size
    }
}