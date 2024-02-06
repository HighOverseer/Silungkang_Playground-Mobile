package id.rla.silungkangplayground.domain.model

data class MemberVoucherInfo(
    val point: String,
    val listVoucher:List<Voucher>,
    val activeVoucherCount: Int,
)