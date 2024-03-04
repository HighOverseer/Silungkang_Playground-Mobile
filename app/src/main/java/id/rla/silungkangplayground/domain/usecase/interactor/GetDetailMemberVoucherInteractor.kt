package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.domain.usecase.GetDetailMemberVoucherUseCase
import javax.inject.Inject

class GetDetailMemberVoucherInteractor @Inject constructor(
    private val repository: Repository
): GetDetailMemberVoucherUseCase {
    override suspend fun invoke(): Resource<MemberVoucherInfo> {
        return repository.getDetailMemberVoucher()
    }

}