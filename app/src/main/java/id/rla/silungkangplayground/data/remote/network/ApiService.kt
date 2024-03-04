package id.rla.silungkangplayground.data.remote.network

import id.rla.silungkangplayground.data.remote.dto.CardMemberDto
import id.rla.silungkangplayground.data.remote.dto.CekInMemberDto
import id.rla.silungkangplayground.data.remote.dto.ExchangePointDto
import id.rla.silungkangplayground.data.remote.dto.LoginDto
import id.rla.silungkangplayground.data.remote.dto.MemberAccountDto
import id.rla.silungkangplayground.data.remote.dto.OfferedVoucherDto
import id.rla.silungkangplayground.data.remote.dto.Response
import id.rla.silungkangplayground.data.remote.dto.VoucherHistoryDto
import id.rla.silungkangplayground.data.remote.dto.VoucherInfoDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("handphone_nomor")
        phoneNumber:String,
        @Field("password")
        password:String
    ): Response<LoginDto>

    @FormUrlEncoded
    @POST("spg/member/getMemberVoucher")
    suspend fun getMemberVoucherInfo(
        @Field("phone_id")
        phoneId:Int
    ):Response<VoucherInfoDto>

    @FormUrlEncoded
    @POST("spg/memberHistory/getAll")
    suspend fun getMemberHistory(
        @Field("phone_id")
        phoneId: Int
    ):Response<List<VoucherHistoryDto>>


    @FormUrlEncoded
    @POST("spg/member/getCardMember")
    suspend fun getCardMember(
        @Field("phone_id")
        phoneId: Int
    ):Response<List<CardMemberDto>>

    @FormUrlEncoded
    @POST("spg/member/cekMemberId")
    suspend fun checkInMember(
        @Field("phone_id")
        phoneId: Int,
        @Field("member_id")
        memberId:String
    ):Response<CekInMemberDto>

    @GET("spg/voucher/getVouchers")
    suspend fun getVouchers():Response<List<OfferedVoucherDto>>

    @FormUrlEncoded
    @POST("spg/point/buyVoucher")
    suspend fun exchangePoint(
        @Field("phoneId")
        phoneId:Int,
        @Field("memberId")
        memberId: String,
        @Field("voucherId")
        vouhcerId: Int
    ):Response<ExchangePointDto>

    @FormUrlEncoded
    @POST("spg/review/postReview")
    suspend fun sendFeedback(
        @Field("phone_id")
        phoneId:Int,
        @Field("rating")
        rating: Int,
        @Field("kritik_saran")
        content: String
    ):Response<Boolean>



}