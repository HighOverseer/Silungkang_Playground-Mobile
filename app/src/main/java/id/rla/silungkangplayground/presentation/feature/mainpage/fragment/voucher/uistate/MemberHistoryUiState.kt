package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.uistate

import id.rla.silungkangplayground.domain.model.MemberHistoryItem

data class MemberHistoryUiState(
    val listMemberHistory:List<MemberHistoryItem> = emptyList(),
    val isLoading:Boolean = false
)