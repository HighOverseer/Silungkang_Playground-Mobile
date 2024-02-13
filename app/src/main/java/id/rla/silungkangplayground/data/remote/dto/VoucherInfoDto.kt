package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VoucherInfoDto(

	@field:SerializedName("voucher_target")
	val voucherTarget: String? = null,

	@field:SerializedName("voucher_type")
	val voucherType: String? = null,

	@field:SerializedName("voucher_count")
	val voucherCount: Int? = null,

	@field:SerializedName("tanggal_expired")
	val tanggalExpired: String? = null,

	@field:SerializedName("point")
	val point: String? = null
)
