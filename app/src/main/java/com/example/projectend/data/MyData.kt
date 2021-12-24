package com.example.projectend.data

import com.google.gson.annotations.SerializedName

data class MyData(

	@field:SerializedName("MyData")
	val myData: List<MyDataItem?>? = null
)

data class MyDataItem(

	@field:SerializedName("total_case_excludeabroad")
	val totalCaseExcludeabroad: Int? = null,

	@field:SerializedName("new_recovered")
	val newRecovered: Int? = null,

	@field:SerializedName("txn_date")
	val txnDate: String? = null,

	@field:SerializedName("total_death")
	val totalDeath: Int? = null,

	@field:SerializedName("new_death")
	val newDeath: Int? = null,

	@field:SerializedName("total_case")
	val totalCase: Int? = null,

	@field:SerializedName("total_recovered")
	val totalRecovered: Int? = null,

	@field:SerializedName("new_case_excludeabroad")
	val newCaseExcludeabroad: Int? = null,

	@field:SerializedName("update_date")
	val updateDate: String? = null,

	@field:SerializedName("new_case")
	val newCase: Int? = null
)
