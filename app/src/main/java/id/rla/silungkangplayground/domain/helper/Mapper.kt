package id.rla.silungkangplayground.domain.helper

import id.rla.silungkangplayground.data.remote.dto.GetMemberHistoryResponse
import id.rla.silungkangplayground.data.remote.dto.MemberVoucherInfoResponse
import id.rla.silungkangplayground.domain.common.Constants.PATTERN_DATE_DOMAIN
import id.rla.silungkangplayground.domain.common.Constants.PATTERN_DATE_RESPONSE
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
    suspend fun mapMemberVoucherInfoDtoToDomain(response: MemberVoucherInfoResponse):MemberVoucherInfo
     = withContext(Dispatchers.Default){
        val listVoucher = mapResponseToVoucher(response.voucherTarget, response.voucherType)

        MemberVoucherInfo(
            point = response.point ?: "0",
            listVoucher = listVoucher,
            activeVoucherCount = response.voucherCount ?: 0
        )
    }

    private suspend fun mapResponseToVoucher(
        voucherTarget:String?,
        voucherType:String?
    ):List<Voucher>
    = withContext(Dispatchers.Default){
        if (voucherTarget.isNullOrBlank() || voucherType.isNullOrBlank()) return@withContext emptyList()

        val listVoucherValue = voucherType.split(",").map {
            ensureActive() //check if coroutine are not cancelled

            val valueWithoutPeriod = it.split(".").onEach {  }.first()


            StringBuilder(valueWithoutPeriod).insert(3, ".").toString()
        }
        val listVoucherTarget = voucherTarget.split(",")

        if (listVoucherTarget.size != listVoucherValue.size) return@withContext emptyList()

        val listVoucher = MutableList(listVoucherValue.size){ i ->
            ensureActive()

            Voucher(
                VoucherType.entries.first { it.code == listVoucherTarget[i] },
                listVoucherValue[i],
                "30/11/2023"
            )
        }
        listVoucher
    }

    suspend fun mapMemberHistoryDtoToDomain(
        response: GetMemberHistoryResponse
    ):List<MemberHistoryItem>
    = withContext(Dispatchers.Default){

        val data = response.data ?: return@withContext emptyList()

        val formatDateResponse = SimpleDateFormat(PATTERN_DATE_RESPONSE, Locale.getDefault())
        val formatDateDomain = SimpleDateFormat(PATTERN_DATE_DOMAIN, Locale.getDefault())

        data.map {
            ensureActive()

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

    /*suspend fun mapLoginResponseToDomain(loginResponse: LoginResponse):LoginInfo
    = withContext(Dispatchers.Default){
        ensureActive()

        //val memberIds = loginResponse.data?.memberIds?.split(",")?
        val message = loginResponse.message ?: "Login Berhasil!"
        //if(memberIds.isNullOrEmpty()) throw Exception("Member Id tidak ditemukan..")

        LoginInfo(
            memberIds = memberIds,
            message = DynamicString(message)
        )
    }*/


}