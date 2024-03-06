package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.usecase.CheckIsUserHasAlreadyLoggedInUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsUserHasAlreadyLoggedInInteractor @Inject constructor(
    private val repository: Repository
):CheckIsUserHasAlreadyLoggedInUseCase {
    override fun invoke(): Flow<Boolean> {
        return repository.isUserHasAlreadyLoggedIn()
    }
}