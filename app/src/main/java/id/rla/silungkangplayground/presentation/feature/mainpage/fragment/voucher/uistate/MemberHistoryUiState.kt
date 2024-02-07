package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.uistate

import id.rla.silungkangplayground.domain.common.SingleEvent
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.model.MemberHistoryItem

data class MemberHistoryUiState(
    val listMemberHistory:List<MemberHistoryItem> = emptyList(),
    val toastMessage: SingleEvent<StringRes>? = null,
    val isLoading:Boolean = false
)