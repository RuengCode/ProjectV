package com.example.projectend.data

import com.google.gson.annotations.SerializedName

data class Covid77(

	@field:SerializedName("Covid77")
	val covid77: List<Covid77Item?>? = null
)

data class Covid77Item(

	@field:SerializedName("total_case_excludeabroad")
	val totalCaseExcludeabroad: Int? = null,

	@field:SerializedName("txn_date")
	val txnDate: String? = null,

	@field:SerializedName("total_death")
	val totalDeath: Int? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("new_death")
	val newDeath: Int? = null,

	@field:SerializedName("total_case")
	val totalCase: Int? = null,

	@field:SerializedName("new_case_excludeabroad")
	val newCaseExcludeabroad: Int? = null,

	@field:SerializedName("update_date")
	val updateDate: String? = null,

	@field:SerializedName("new_case")
	val newCase: Int? = null
)
