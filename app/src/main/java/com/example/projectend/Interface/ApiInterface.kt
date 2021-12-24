package com.example.projectend.Interface

import com.example.projectend.data.Covid77Item
import com.example.projectend.data.MyDataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("api/Cases/today-cases-all")
    fun getData() : Call<List<MyDataItem>>

    @GET("api/Cases/today-cases-by-provinces")
    fun getData1() : Call<List<Covid77Item>>
}