package id.rla.silungkangplayground.presentation.feature.mainpage

import id.rla.silungkangplayground.domain.model.MemberAccount

data class MainPageUiState(
    val isLoading:Boolean = false,
    val currentMemberAccountId:String?= null,
    val listMemberAccount:List<MemberAccount> = emptyList()
)