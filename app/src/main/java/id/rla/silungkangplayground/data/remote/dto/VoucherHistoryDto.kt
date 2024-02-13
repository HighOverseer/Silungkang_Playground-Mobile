package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VoucherHistoryDto(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("member_name")
	val memberName: String? = null,
)
