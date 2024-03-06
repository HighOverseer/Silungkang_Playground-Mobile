package id.rla.silungkangplayground.domain.data

import androidx.paging.PagingData
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.model.CekInData
import id.rla.silungkangplayground.domain.model.EventPlayground
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
    fun isUserHasAlreadyLoggedIn():Flow<Boolean>
    suspend fun sendFeedback(rating:Int, content:String):Resource<Boolean>
    fun getEventPlaygroundInPaging():Flow<PagingData<EventPlayground>>

    companion object{
        const val PAGING_PAGE_SIZE = 10
    }

}