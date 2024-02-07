package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo

interface GetDetailMemberVoucherUseCase {
    suspend operator fun invoke(): Resource<MemberVoucherInfo>
}