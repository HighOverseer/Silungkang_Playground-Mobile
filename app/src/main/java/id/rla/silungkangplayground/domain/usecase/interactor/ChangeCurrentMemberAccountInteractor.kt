package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.usecase.ChangeCurrentMemberAccountUseCase
import javax.inject.Inject

class ChangeCurrentMemberAccountInteractor @Inject constructor(
    private val repository: Repository
): ChangeCurrentMemberAccountUseCase {
    override suspend fun invoke(memberId: String) {
        repository.changeCurrentMemberAccount(memberId)
    }
}