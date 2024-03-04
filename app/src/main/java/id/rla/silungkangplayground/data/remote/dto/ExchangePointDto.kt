package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ExchangePointDto(

	@field:SerializedName("history")
	val history: Int? = null,

	@field:SerializedName("affected")
	val affected: Int? = null
)
