package id.rla.silungkangplayground.domain.model

data class OfferedVoucher(
    val typeId:Int,
    val value:String,
    val costPoint:String,
    val type: VoucherType
)