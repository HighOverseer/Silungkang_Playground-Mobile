package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.model.CardMember

interface GetCardMemberUseCase {
    suspend operator fun invoke():Resource<List<CardMember>>

}