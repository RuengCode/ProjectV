package com.example.projectend.Fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.projectend.R
import com.example.projectend.activity.LoginActivity
import com.example.projectend.activity.ProfileActivity
import com.example.projectend.activity.RegisterActivity
import org.w3c.dom.Text


class MainFragment : Fragment() {
    private lateinit var menu1: LinearLayout
    private lateinit var menu2: LinearLayout
    private lateinit var menu3: LinearLayout
    private lateinit var menu4: LinearLayout
    private lateinit var menu5: LinearLayout
    private lateinit var menu6: LinearLayout
    private lateinit var menu7: LinearLayout
    private lateinit var menu8: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        menu1 = view.findViewById(R.id.menu1)
        menu2 = view.findViewById(R.id.menu2)
        menu3 = view.findViewById(R.id.menu3)
        menu4 = view.findViewById(R.id.menu4)
        menu5 = view.findViewById(R.id.menu5)
        menu6 = view.findViewById(R.id.menu6)
        menu7 = view.findViewById(R.id.menu7)
        menu8 = view.findViewById(R.id.menu8)

        menu1.setOnClickListener {
            val intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
        }
        menu2.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        menu3.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }


        return view
    }
//    private fun clickMenu(id : LinearLayout,activity: Activity){
//        id.setOnClickListener {
//            val intent = Intent(context, activity::class.java)
//            startActivity(intent)
//        }
//    }

}