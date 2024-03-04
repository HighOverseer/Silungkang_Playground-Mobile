package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.model.OfferedVoucher
import id.rla.silungkangplayground.domain.usecase.GetOfferedVouchersUseCase
import javax.inject.Inject

class GetOfferedVoucherInteractor @Inject constructor(
    private val repository: Repository
): GetOfferedVouchersUseCase {
    override suspend fun invoke(): Resource<List<OfferedVoucher>> {
        return repository.getOfferedVouchers()
    }
}