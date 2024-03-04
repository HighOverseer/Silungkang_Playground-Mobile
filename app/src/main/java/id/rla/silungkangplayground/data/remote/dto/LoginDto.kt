package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(

	@field:SerializedName("phone_id")
	val phoneId: Int? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("default_member_id")
	val defaultMemberId:String? = null

	/*@field:SerializedName("default_member")
	val defaultMember:MemberAccountDto?*/
)
