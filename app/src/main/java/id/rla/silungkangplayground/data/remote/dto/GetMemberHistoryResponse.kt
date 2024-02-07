package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetMemberHistoryResponse(

	@field:SerializedName("data")
	val data: List<MemberHistoryResponse>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

