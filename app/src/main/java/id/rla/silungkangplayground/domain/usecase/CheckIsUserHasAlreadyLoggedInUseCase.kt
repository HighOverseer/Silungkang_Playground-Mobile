package id.rla.silungkangplayground.domain.usecase

import kotlinx.coroutines.flow.Flow

interface CheckIsUserHasAlreadyLoggedInUseCase {
    operator fun invoke(): Flow<Boolean>
}