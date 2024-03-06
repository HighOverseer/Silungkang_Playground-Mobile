package id.rla.silungkangplayground.domain.helper

import id.rla.silungkangplayground.BuildConfig
import id.rla.silungkangplayground.data.helper.QrCodeGenerator
import id.rla.silungkangplayground.data.local.EventEntity
import id.rla.silungkangplayground.data.remote.dto.CardMemberDto
import id.rla.silungkangplayground.data.remote.dto.EventDto
import id.rla.silungkangplayground.data.remote.dto.MemberAccountDto
import id.rla.silungkangplayground.data.remote.dto.OfferedVoucherDto
import id.rla.silungkangplayground.data.remote.dto.VoucherHistoryDto
import id.rla.silungkangplayground.data.remote.dto.VoucherInfoDto
import id.rla.silungkangplayground.domain.common.Constants.PATTERN_DATE_DOMAIN
import id.rla.silungkangplayground.domain.common.Constants.PATTERN_DATE_RESPONSE
import id.rla.silungkangplayground.domain.common.Event
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.model.EventPlayground
import id.rla.silungkangplayground.domain.model.MemberAccount
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.domain.model.OfferedVoucher
import id.rla.silungkangplayground.domain.model.Voucher
import id.rla.silungkangplayground.domain.model.VoucherType
import id.rla.silungkangplayground.presentation.util.getCurrentDate
import id.rla.silungkangplayground.presentation.util.getCurrentDateInString
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

            val valueWithoutPeriod = it.split(".").first()


            StringBuilder(valueWithoutPeriod).insert(3, ".").toString()
        }

        val listVoucherTarget = voucherTarget.split(",")

        val formatDateResponse = SimpleDateFormat(PATTERN_DATE_RESPONSE, Locale.getDefault())
        val formatDateDomain = SimpleDateFormat(PATTERN_DATE_DOMAIN, Locale.getDefault())
        val listVoucherExpiredDate = voucherExpiredDate.split(",").map {
            ensureActive()

            val date = formatDateResponse.parse(it)?: getCurrentDate()
            formatDateDomain.format(date)
        }

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
                    MemberAccount(
                        it.memberId,
                        it.memberName
                    ),
                    qrCodeBitmap
                )
            }
        }

    }

    suspend fun mapMemberAccountDtoToDomain(
        list: List<MemberAccountDto>?,
    ):List<MemberAccount>{
        return withContext(Dispatchers.Default){
            if (list.isNullOrEmpty()) throw Exception("Maaf, terjadi kesalahan..")

            list.map {
                ensureActive()

                MemberAccount(
                    it.memberId ?: "",
                    it.memberName ?:""
                )
            }
        }
    }

    suspend fun mapOfferedVoucherDtoToDomain(
        list:List<OfferedVoucherDto>?
    ):List<OfferedVoucher>{
        return withContext(Dispatchers.Default){
            if (list.isNullOrEmpty()) return@withContext emptyList()


            list.map {
                val voucherType = VoucherType.mapByCode[it.voucherTarget] ?: return@withContext emptyList()
                val valueWithoutPeriod = it.hargaRupiah?.split(".")?.first()?.run {
                    StringBuilder(this).insert(3, ".").toString()
                } ?: return@withContext emptyList()

                OfferedVoucher(
                    typeId = it.id ?: return@withContext emptyList(),
                    value = valueWithoutPeriod,
                    costPoint = it.hargaPoint?.toString() ?: "0",
                    type = voucherType
                )
            }
        }
    }

    suspend fun mapEventDtoToEventEntity(
        listEventDto: List<EventDto>,
    ):List<EventEntity>{
        return withContext(Dispatchers.Default){
            if (listEventDto.isEmpty()) return@withContext emptyList()

            val formatDateResponse = SimpleDateFormat(PATTERN_DATE_RESPONSE, Locale.getDefault())
            val formatDateDomain = SimpleDateFormat(PATTERN_DATE_DOMAIN, Locale.getDefault())
            listEventDto.map {
                ensureActive()


                var date = formatDateResponse.parse(it.eventStart ?: "2024-01-1") ?: getCurrentDate()
                val dateStart = formatDateDomain.format(date)

                date = formatDateResponse.parse(it.eventEnd ?: "2024-01-1") ?: getCurrentDate()
                val dateFinish = formatDateDomain.format(date)

                EventEntity(
                    id = it.eventId ?: return@withContext emptyList(),
                    eventName = it.eventName ?: "",
                    eventBanner = it.eventBanner ?: "view/assets/images/Tahfidz.png",
                    eventStart = dateStart,
                    eventEnd = dateFinish,
                    eventLink = it.eventLink ?: "https://silungkangplayground.id/"
                )
            }
        }
    }

    suspend fun mapEventEntityToDomain(
        eventEntity: EventEntity
    ):EventPlayground{
        return withContext(Dispatchers.Default){
            ensureActive()

            eventEntity.run{
                EventPlayground(
                    id = id,
                    dateStart = eventStart,
                    dateFinish = eventEnd,
                    title = eventName,
                    thumbnailUrl = if (eventBanner.isNotBlank()) "${BuildConfig.BASE_URL}$eventBanner" else "https://silungkangplayground.id/"
                )
            }

        }
    }


}