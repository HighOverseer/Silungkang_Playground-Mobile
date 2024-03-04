package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.usecase.SendFeedbackUseCase
import javax.inject.Inject

class SendFeedbackInteractor @Inject constructor(
    private val repository: Repository
):SendFeedbackUseCase {
    override suspend fun invoke(rating: Int, content: String): Resource<Boolean> {
        return repository.sendFeedback(rating, content)
    }
}