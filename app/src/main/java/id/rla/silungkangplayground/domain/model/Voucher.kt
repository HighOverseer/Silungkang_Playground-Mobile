package id.rla.silungkangplayground.domain.model

data class Voucher(
    val type: VoucherType,
    val value:String,
    val expireDate:String
)