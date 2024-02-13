package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.card_member

import id.rla.silungkangplayground.domain.model.CardMember

data class CardMemberUiState(
    val listCardMember:List<CardMember> = emptyList(),
    val isLoading:Boolean = false
)