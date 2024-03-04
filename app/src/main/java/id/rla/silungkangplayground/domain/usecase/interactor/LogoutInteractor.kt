package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.usecase.LogoutUseCase
import javax.inject.Inject

class LogoutInteractor @Inject constructor(
    private val repository: Repository
): LogoutUseCase {
    override suspend fun invoke(): Resource<Unit> {
        return repository.logout()
    }
}