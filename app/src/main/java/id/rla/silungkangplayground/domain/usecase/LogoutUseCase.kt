package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource

interface LogoutUseCase {
    suspend operator fun invoke(): Resource<Unit>
}