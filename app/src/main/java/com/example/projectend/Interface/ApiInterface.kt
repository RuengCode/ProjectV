package com.example.projectend.Interface

import com.example.projectend.data.MyDataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("api/Cases/today-cases-all")
    fun getData() : Call<List<MyDataItem>>
}