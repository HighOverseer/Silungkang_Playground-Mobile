package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MemberAccountDto(
    @field:SerializedName("member_id")
    val memberId:String? = null,

    @field:SerializedName("member_name")
    val memberName:String? = null,
)