package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource

interface SendFeedbackUseCase {
    suspend operator fun invoke(
        rating:Int,
        content:String
    ): Resource<Boolean>
}