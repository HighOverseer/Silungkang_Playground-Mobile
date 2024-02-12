package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes

interface LoginUseCase {
    suspend operator fun invoke(
        phoneNumber:String,
        password:String
    ):Resource<StringRes>

}