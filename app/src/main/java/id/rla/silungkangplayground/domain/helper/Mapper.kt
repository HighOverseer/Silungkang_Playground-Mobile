package id.rla.silungkangplayground.domain.helper

import id.rla.silungkangplayground.data.helper.QrCodeGenerator
import id.rla.silungkangplayground.data.remote.dto.CardMemberDto
import id.rla.silungkangplayground.data.remote.dto.VoucherHistoryDto
import id.rla.silungkangplayground.data.remote.dto.VoucherInfoDto
import id.rla.silungkangplayground.domain.common.Constants.PATTERN_DATE_DOMAIN
import id.rla.silungkangplayground.domain.common.Constants.PATTERN_DATE_RESPONSE
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.domain.model.Voucher
import id.rla.silungkangplayground.domain.model.VoucherType
import id.rla.silungkangplayground.presentation.util.getCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Locale

object Mapper {
    suspend fun mapMemberVoucherInfoDtoToDomain(voucherInfoDto: VoucherInfoDto?):MemberVoucherInfo
     = withContext(Dispatchers.Default){
         if (voucherInfoDto == null) throw Exception("Data Tidak Ditemukan..")

        val listVoucher = mapResponseToVoucher(voucherInfoDto.voucherTarget, voucherInfoDto.voucherType, voucherInfoDto.tanggalExpired)

        MemberVoucherInfo(
            point = voucherInfoDto.point ?: "0",
            listVoucher = listVoucher,
            activeVoucherCount = voucherInfoDto.voucherCount ?: 0
        )
    }

    private suspend fun mapResponseToVoucher(
        voucherTarget:String?,
        voucherType:String?,
        voucherExpiredDate:String?
    ):List<Voucher>
    = withContext(Dispatchers.Default){
        if (voucherTarget.isNullOrBlank() || voucherType.isNullOrBlank() || voucherExpiredDate.isNullOrBlank()) {
            return@withContext emptyList()
        }

        val listVoucherValue = voucherType.split(",").map {
            ensureActive() //check if coroutine are not cancelled

            val valueWithoutPeriod = it.split(".").onEach {  }.first()


            StringBuilder(valueWithoutPeriod).insert(3, ".").toString()
        }
        val listVoucherTarget = voucherTarget.split(",")

        val listVoucherExpiredDate = voucherExpiredDate.split(",")

        val isAllListHasSameLength = listOf(listVoucherValue, listVoucherTarget, listVoucherExpiredDate).distinct().size == 1
        if (isAllListHasSameLength) return@withContext emptyList()

        val listVoucher = MutableList(listVoucherValue.size){ i ->
            ensureActive()

            val fixVoucherType = VoucherType.entries.first { it.code == listVoucherTarget[i] }

            Voucher(
                 fixVoucherType,
                listVoucherValue[i],
                listVoucherExpiredDate[i]
            )
        }
        listVoucher
    }

    suspend fun mapMemberHistoryDtoToDomain(
        list: List<VoucherHistoryDto>?
    ):List<MemberHistoryItem>
    = withContext(Dispatchers.Default){

        val data = list ?: return@withContext emptyList()

        val formatDateResponse = SimpleDateFormat(PATTERN_DATE_RESPONSE, Locale.getDefault())
        val formatDateDomain = SimpleDateFormat(PATTERN_DATE_DOMAIN, Locale.getDefault())

        data.map {
            ensureActive()

            if (it.id == null || it.keterangan == null || it.createdAt == null || it.memberName == null) {
                return@withContext emptyList()
            }

            val date = it.createdAt.let { dateString ->
                val dateWithoutTime = dateString.substring(0, 10)

                formatDateResponse.parse(dateWithoutTime)

            } ?: getCurrentDate()

            val dateDomainString = formatDateDomain.format(date)

            val information = "${it.memberName} ${it.keterangan}"

            MemberHistoryItem(
                it.id.toLong(),
                information,
                dateDomainString
            )
        }
    }

    suspend fun mapCardMemberDtoToDomain(
        qrCodeGenerator: QrCodeGenerator,
        list:List<CardMemberDto>?
    ):List<CardMember>{
        return withContext(Dispatchers.Default){
            if (list == null) return@withContext emptyList()

            list.map {
                ensureActive()

                if (it.memberId == null || it.memberName == null) return@withContext emptyList()

                val qrCodeBitmap = qrCodeGenerator.generate(it.memberId)

                CardMember(
                    it.memberId,
                    qrCodeBitmap,
                    it.memberName
                )
            }
        }

    }


}