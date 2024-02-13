package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Response <T>(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("data")
    val data: T? = null
)