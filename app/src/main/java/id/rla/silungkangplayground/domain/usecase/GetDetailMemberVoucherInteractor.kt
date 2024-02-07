package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo

class GetDetailMemberVoucherInteractor(
    private val repository: Repository
):GetDetailMemberVoucherUseCase {
    override suspend fun invoke(): Resource<MemberVoucherInfo> {
        return repository.getDetailMemberVoucher()
    }

}