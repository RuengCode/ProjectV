package com.example.projectend.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectend.Interface.ApiInterface
import com.example.projectend.R
import com.example.projectend.adapter.MyAdapter
import com.example.projectend.data.MyDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

const val BASE_URL = "https://covid19.ddc.moph.go.th/"
class CovidTodayFragment : Fragment() {
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.fragment_covid_today, container, false)
        val rvCovidToday = view.findViewById<RecyclerView>(R.id.rvCovidToday)



        getData(view)

        return view
    }

    private fun getData(view : View) {
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
                val rvCovidToday = view.findViewById<RecyclerView>(R.id.rvCovidToday)

                rvCovidToday.setHasFixedSize(true)
                linearLayoutManager = LinearLayoutManager(context)
                rvCovidToday.layoutManager = linearLayoutManager

                myAdapter = MyAdapter(requireContext(),responseBody)
                myAdapter.notifyDataSetChanged()
                rvCovidToday.adapter = myAdapter

                Log.d("main",response.body().toString())

            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Toast.makeText(context,"ข้อมูลไม่เข้า",Toast.LENGTH_SHORT).show()
            }
        })
    }


}