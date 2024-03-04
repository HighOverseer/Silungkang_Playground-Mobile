package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo

interface ExchangePointUseCase {
    suspend operator fun invoke(voucherTypeId:Int): Resource<MemberVoucherInfo>
}