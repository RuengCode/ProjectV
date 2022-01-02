package com.example.projectend.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectend.Interface.ApiInterface
import com.example.projectend.R
import com.example.projectend.activity.*
import com.example.projectend.adapter.MyAdapter
import com.example.projectend.adapter.MySliderImageAdapter
import com.example.projectend.data.MyDataItem
import com.google.firebase.auth.FirebaseAuth
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainFragment : Fragment() {
    private lateinit var menu1: LinearLayout
    private lateinit var menu2: LinearLayout
    private lateinit var menu3: LinearLayout
    private lateinit var menu4: LinearLayout
    private lateinit var menu5: LinearLayout
    private lateinit var menu6: LinearLayout
    private lateinit var menu7: LinearLayout
    private lateinit var menu8: LinearLayout

    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val imageSlider = view.findViewById<SliderView>(R.id.imageSlider)
        menu1 = view.findViewById(R.id.menu1)
        menu2 = view.findViewById(R.id.menu2)
        menu3 = view.findViewById(R.id.menu3)
        menu4 = view.findViewById(R.id.menu4)
        menu5 = view.findViewById(R.id.menu5)
        menu6 = view.findViewById(R.id.menu6)
        menu7 = view.findViewById(R.id.menu7)
        menu8 = view.findViewById(R.id.menu8)

        firebaseAuth = FirebaseAuth.getInstance()

        menu1.setOnClickListener {
            val intent = Intent(context, FirebaseRegisterActivity::class.java)
            startActivity(intent)
        }
        menu2.setOnClickListener {
            val intent = Intent(context, FirebaseLoginActivity::class.java)
            startActivity(intent)
        }
        menu3.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
        menu4.setOnClickListener {
            val intent = Intent(context, ListHomeActivity::class.java)
            startActivity(intent)
        }

        getData(view)

        val imageList: ArrayList<String> = ArrayList()
        imageList.add("https://www.img.in.th/images/2c64237a06e358914c5ef2dc24eb1263.png")
        imageList.add("https://www.img.in.th/images/a73e6de619e788582c7c810ff8191547.png")
        imageList.add("https://www.img.in.th/images/7bafd8e102de6d5936456af35a2e3ba4.png")
        imageList.add("https://www.img.in.th/images/c5c6b59928c0d1845226544330859683.png")
        imageList.add("https://www.img.in.th/images/b71741d8278081a7361802882fd225bd.png")
        setImageInSlider(imageList, imageSlider)

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

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

    private fun getData(view: View) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {

                val responseBody = response.body()!!
                val rvCovidToday = view.findViewById<RecyclerView>(R.id.rvCovidTodayMain)

                rvCovidToday.setHasFixedSize(true)
                linearLayoutManager = LinearLayoutManager(context)
                rvCovidToday.layoutManager = linearLayoutManager

                myAdapter = MyAdapter(requireContext(), responseBody)
                myAdapter.notifyDataSetChanged()
                rvCovidToday.adapter = myAdapter

                Log.d("main", response.body().toString())

            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Toast.makeText(context, "ข้อมูลไม่เข้า", Toast.LENGTH_SHORT).show()
            }
        })
    }

}