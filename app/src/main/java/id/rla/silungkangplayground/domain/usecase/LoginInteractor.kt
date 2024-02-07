package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.data.Repository
import kotlin.math.log

class LoginInteractor(
    private val repository: Repository
):LoginUseCase {
    override suspend fun invoke(memberId: String, password: String):Resource<StringRes>{
        return repository.login(memberId, password)
    }

}