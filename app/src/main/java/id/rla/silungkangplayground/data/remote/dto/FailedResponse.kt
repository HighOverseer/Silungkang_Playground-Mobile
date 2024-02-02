package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FailedResponse(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("rows")
	val rows: List<Any>? = null,

	@field:SerializedName("status")
	val status: String? = null
)
