package id.rla.silungkangplayground.domain.helper

import id.rla.silungkangplayground.data.remote.dto.MemberVoucherInfoResponse
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.domain.model.Voucher
import id.rla.silungkangplayground.domain.model.VoucherType
import java.lang.StringBuilder

object Mapper {
    fun mapMemberVoucherInfoDtoToDomain(response: MemberVoucherInfoResponse):MemberVoucherInfo{
        val listVoucher = mapResponseToVoucher(response.voucherTarget, response.voucherType)

        return MemberVoucherInfo(
            point = response.point ?: "0",
            listVoucher = listVoucher,
            activeVoucherCount = response.voucherCount ?: 0
        )
    }

    private fun mapResponseToVoucher(
        voucherTarget:String?,
        voucherType:String?
    ):List<Voucher>{
        if (voucherTarget.isNullOrBlank() || voucherType.isNullOrBlank()) return emptyList()

        val listVoucherValue = voucherType.split(",").map {
            val valueWithoutPeriod = it.split(".").first()

            StringBuilder(valueWithoutPeriod).insert(3, ".").toString()
        }
        val listVoucherTarget = voucherTarget.split(",")

        if (listVoucherTarget.size != listVoucherValue.size) return emptyList()

        val listVoucher = MutableList(listVoucherValue.size){ i ->

            Voucher(
                VoucherType.entries.first { it.code == listVoucherTarget[i] },
                listVoucherValue[i],
                "30/11/2023"
            )
        }
        return listVoucher

    }
}