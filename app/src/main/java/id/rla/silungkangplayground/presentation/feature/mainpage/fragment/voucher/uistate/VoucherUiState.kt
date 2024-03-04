package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.uistate

import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.domain.model.OfferedVoucher


data class VoucherUiState(
    val memberVoucherInfo: MemberVoucherInfo? = null,
    val isLoadingDetailUser:Boolean = false,
    val isLoadingOfferedVouchers:Boolean = false,
    val offeredVouchers:List<OfferedVoucher> = emptyList()
)