package id.rla.silungkangplayground.domain.data

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo

interface Repository {

    suspend fun login(
        memberId: String,
        password: String
    ): Resource<StringRes>

    suspend fun getDetailMemberVoucher():Resource<MemberVoucherInfo>

}