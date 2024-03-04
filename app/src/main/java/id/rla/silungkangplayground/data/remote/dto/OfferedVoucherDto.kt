package id.rla.silungkangplayground.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OfferedVoucherDto(

	@field:SerializedName("harga_rupiah")
	val hargaRupiah: String? = null,

	@field:SerializedName("harga_point")
	val hargaPoint: Int? = null,

	@field:SerializedName("voucher_target")
	val voucherTarget: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
