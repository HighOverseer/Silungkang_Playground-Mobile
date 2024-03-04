package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.model.OfferedVoucher

interface GetOfferedVouchersUseCase {
    suspend operator fun invoke(): Resource<List<OfferedVoucher>>
}