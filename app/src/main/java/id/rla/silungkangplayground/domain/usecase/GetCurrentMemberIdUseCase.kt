package id.rla.silungkangplayground.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetCurrentMemberIdUseCase {
    operator fun invoke(): Flow<String>
}