package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MemberHistoryResponse(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,


)
