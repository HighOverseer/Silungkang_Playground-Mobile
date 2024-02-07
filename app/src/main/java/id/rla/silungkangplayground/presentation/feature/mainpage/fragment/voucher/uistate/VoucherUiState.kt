package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.uistate

import id.rla.silungkangplayground.domain.common.SingleEvent
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo

data class VoucherUiState(
    val memberVoucherInfo: MemberVoucherInfo? = null,
    val toastMessage:SingleEvent<StringRes>? = null,
    val isLoading:Boolean = false
)