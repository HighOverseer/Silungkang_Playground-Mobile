package id.rla.silungkangplayground.data.remote.network

import id.rla.silungkangplayground.data.remote.dto.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("member_id")
        memberId:String,
        @Field("password")
        password:String
    ): Call<LoginResponse>

}