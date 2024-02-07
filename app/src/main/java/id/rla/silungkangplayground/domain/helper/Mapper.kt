package id.rla.silungkangplayground.domain.helper

import id.rla.silungkangplayground.data.remote.dto.GetMemberHistoryResponse
import id.rla.silungkangplayground.data.remote.dto.MemberHistoryResponse
import id.rla.silungkangplayground.data.remote.dto.MemberVoucherInfoResponse
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.domain.model.Voucher
import id.rla.silungkangplayground.domain.model.VoucherType
import id.rla.silungkangplayground.presentation.util.getCurrentDate
import id.rla.silungkangplayground.presentation.util.getCurrentDateInString
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Locale

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

    fun mapMemberHistoryDtoToDomain(
        response: GetMemberHistoryResponse
    ):List<MemberHistoryItem>{

        val data = response.data ?: return emptyList()

        val PATTERN_DATE_RESPONSE = "yyyy-MM-dd"
        val PATTERN_DATE_DOMAIN = "dd/MM/yyyy"

        val formatDateResponse = SimpleDateFormat(PATTERN_DATE_RESPONSE, Locale.getDefault())
        val formatDateDomain = SimpleDateFormat(PATTERN_DATE_DOMAIN, Locale.getDefault())

        return data.map {
            val date = it.createdAt?.let { dateString ->
                val dateWithoutTime = dateString.substring(0, 10)

                formatDateResponse.parse(dateWithoutTime)

            } ?: getCurrentDate()

            val dateDomainString = formatDateDomain.format(date)

            MemberHistoryItem(
                it.id?.toLong() ?: -1L,
                it.keterangan ?: "null",
                dateDomainString
            )
        }
    }


}