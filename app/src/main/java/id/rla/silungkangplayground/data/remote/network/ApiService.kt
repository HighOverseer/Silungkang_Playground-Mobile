package id.rla.silungkangplayground.data.remote.network

import id.rla.silungkangplayground.data.remote.dto.GetMemberHistoryResponse
import id.rla.silungkangplayground.data.remote.dto.LoginResponse
import id.rla.silungkangplayground.data.remote.dto.MemberVoucherInfoResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("handphone_nomor")
        phoneNumber:String,
        @Field("password")
        password:String
    ): LoginResponse

    @FormUrlEncoded
    @POST("spg/member/getMember")
    suspend fun getMemberVoucherInfo(
        @Field("memberId")
        memberId:String
    ):MemberVoucherInfoResponse

    @FormUrlEncoded
    @POST("spg/memberHistory/get")
    suspend fun getMemberHistory(
        @Field("member_id")
        memberId: String
    ):GetMemberHistoryResponse


}