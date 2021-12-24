package com.example.projectend.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectend.Interface.ApiInterface
import com.example.projectend.R
import com.example.projectend.adapter.CountyAdapter
import com.example.projectend.adapter.MyAdapter
import com.example.projectend.data.Covid77Item
import com.example.projectend.data.MyDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL1 = "https://covid19.ddc.moph.go.th/"
class CountyFragment : Fragment() {
    lateinit var myAdapter: CountyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_county, container, false)

        getData(view)

        return view
    }

    private fun getData(view : View) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL1)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData1()
        retrofitData.enqueue(object : Callback<List<Covid77Item>?> {
            override fun onResponse(
                call: Call<List<Covid77Item>?>,
                response: Response<List<Covid77Item>?>
            ) {

                val responseBody = response.body()!!
                val rvCovidCounty = view.findViewById<RecyclerView>(R.id.rvCovidCounty)

                rvCovidCounty.setHasFixedSize(true)
                linearLayoutManager = LinearLayoutManager(context)
                rvCovidCounty.layoutManager = linearLayoutManager

                myAdapter = CountyAdapter(requireContext(),responseBody)
                myAdapter.notifyDataSetChanged()
                rvCovidCounty.adapter = myAdapter

                Log.d("main",response.body().toString())

            }

            override fun onFailure(call: Call<List<Covid77Item>?>, t: Throwable) {
                Toast.makeText(context,"ข้อมูลไม่เข้า", Toast.LENGTH_SHORT).show()
            }
        })
    }


}