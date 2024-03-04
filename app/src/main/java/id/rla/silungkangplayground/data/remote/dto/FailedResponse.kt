package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FailedResponse<T>(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("rows")
	val rows: T? = null,

	@field:SerializedName("status")
	val status: String? = null
)
