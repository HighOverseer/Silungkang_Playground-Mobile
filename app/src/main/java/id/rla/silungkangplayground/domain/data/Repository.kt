package id.rla.silungkangplayground.domain.data

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo

interface Repository {

    suspend fun login(
        phoneNumber: String,
        password: String
    ): Resource<StringRes>

    suspend fun getDetailMemberVoucher():Resource<MemberVoucherInfo>

    suspend fun getMemberHistory():Resource<List<MemberHistoryItem>>

    suspend fun getCardMember():Resource<List<CardMember>>

}