package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.uistate

import id.rla.silungkangplayground.domain.model.MemberVoucherInfo


data class VoucherUiState(
    val memberVoucherInfo: MemberVoucherInfo? = null,
    val isLoading:Boolean = false
)