package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.domain.usecase.ExchangePointUseCase
import id.rla.silungkangplayground.domain.usecase.GetDetailMemberVoucherUseCase
import id.rla.silungkangplayground.domain.usecase.GetOfferedVouchersUseCase
import java.lang.Exception
import javax.inject.Inject

class ExchangePointInteractor @Inject constructor(
    private val repository: Repository,
    private val getDetailMemberVoucherUseCase: GetDetailMemberVoucherUseCase
):ExchangePointUseCase {
    override suspend fun invoke(voucherTypeId: Int): Resource<MemberVoucherInfo> {
        return when(val resource = repository.exchangePoint(voucherTypeId)){
            is Resource.Success -> {
                when(val res = getDetailMemberVoucherUseCase()){
                    is Resource.Success -> {
                        Resource.Success(res.data, resource.data)
                    }
                    else -> {
                        Resource.Failure(resource.data)
                        /*Resource.Success(
                            MemberVoucherInfo(
                                listVoucher = emptyList(),
                                point = "",
                                activeVoucherCount = 0
                            ),
                            resource.data
                        )*/
                    }
                }
            }
            is Resource.Failure -> Resource.Failure(resource.message)
            is Resource.Error -> Resource.Error(resource.e)
        }
    }
}