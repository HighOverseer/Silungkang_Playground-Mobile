package id.rla.silungkangplayground.domain.data

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.model.CekInData
import id.rla.silungkangplayground.domain.model.MemberAccount
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.domain.model.OfferedVoucher
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun login(
        phoneNumber: String,
        password: String
    ): Resource<StringRes>
    suspend fun getDetailMemberVoucher():Resource<MemberVoucherInfo>
    suspend fun getMemberHistory():Resource<List<MemberHistoryItem>>
    suspend fun getCardMember():Resource<List<CardMember>>
    suspend fun checkInMember(memberId:String):Resource<CekInData>
    fun getCurrentMemberId(): Flow<String>
    suspend fun logout():Resource<Unit>
    suspend fun changeCurrentMemberAccount(memberId:String)
    suspend fun getOfferedVouchers():Resource<List<OfferedVoucher>>
    suspend fun exchangePoint(voucherTypeId:Int):Resource<StringRes>
    fun isUserHasAlreadyLoggin():Flow<Boolean>
    suspend fun sendFeedback(rating:Int, content:String):Resource<Boolean>

}