package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CekInMemberDto (
    @field:SerializedName("list_member")
    val listMember:List<MemberAccountDto>? = null,

    @field:SerializedName("notif")
    val sendFeedbackEvent:Boolean? = null,
)