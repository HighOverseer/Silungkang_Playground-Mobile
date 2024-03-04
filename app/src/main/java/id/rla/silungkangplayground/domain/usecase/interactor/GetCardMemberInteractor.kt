package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.usecase.GetCardMemberUseCase
import javax.inject.Inject

class GetCardMemberInteractor @Inject constructor(
    private val repository: Repository
) : GetCardMemberUseCase {
    override suspend fun invoke(): Resource<List<CardMember>> {
        return repository.getCardMember()
    }
}