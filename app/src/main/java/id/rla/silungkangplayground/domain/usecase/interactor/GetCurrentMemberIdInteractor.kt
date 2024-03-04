package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.usecase.GetCurrentMemberIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentMemberIdInteractor @Inject constructor(
    private val repository: Repository
): GetCurrentMemberIdUseCase {
    override fun invoke(): Flow<String> {
        return repository.getCurrentMemberId()
    }
}