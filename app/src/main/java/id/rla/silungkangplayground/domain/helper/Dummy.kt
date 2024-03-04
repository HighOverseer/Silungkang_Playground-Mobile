package id.rla.silungkangplayground.domain.helper

import id.rla.silungkangplayground.BuildConfig
import id.rla.silungkangplayground.domain.model.EventPlayground
import id.rla.silungkangplayground.domain.model.OperationalHours
import id.rla.silungkangplayground.domain.model.PlaygroundModel
import id.rla.silungkangplayground.domain.model.TicketDayPrice
import id.rla.silungkangplayground.domain.model.TicketInfo
import id.rla.silungkangplayground.domain.model.Voucher
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.OfferedVoucher
import id.rla.silungkangplayground.domain.model.VoucherType

object Dummy {

    fun getVouchers():List<Voucher>{
        return List(10){ position ->
            if (position % 2 == 1){
                Voucher(
                    VoucherType.CAFE,
                    "100.000",
                    "30/11/2023"
                )
            }else{
                Voucher(
                    VoucherType.PLAYGROUND,
                    "250.000",
                    "30/11/2023"
                )
            }

        }
    }

    fun getVoucherExchangeOption():List<OfferedVoucher>{
        return listOf(
            OfferedVoucher(
                1,
                "100.000",
                "15",
                VoucherType.PLAYGROUND
            ),
            OfferedVoucher(
                2,
                "250.000",
                "30",
                VoucherType.PLAYGROUND
            ),
            OfferedVoucher(
                3,
                "100.000",
                "15",
                VoucherType.CAFE
            )
        )
    }

/*    suspend fun getCardMember(applicationContext: Context):List<CardMember>{
        return withContext(Dispatchers.Default){
            val qrCodes = listOf(
                async { "lfnslknflsndfl".generateQrCode(applicationContext) },
                async { "sdfsfsdfsdfsf".generateQrCode(applicationContext) },
                async { "sdfdsfsfknknjk".generateQrCode(applicationContext) }
            ).awaitAll()

            listOf(
                CardMember(
                    1,
                    qrCodes[0],
                    "Fajar",
                ),
                CardMember(
                    1,
                    qrCodes[1],
                    "Fajar",
                ),
                CardMember(
                    1,
                    qrCodes[2],
                    "Fajar",
                )
            )
        }

    }*/

    fun getVoucherHistoryItem():List<MemberHistoryItem>{
        return listOf(
            MemberHistoryItem(
                1,
                "Penukaran voucher 100.000",
                "15/11/2023"
            ),
            MemberHistoryItem(
                2,
                "Penambahan point dari scan tiket masuk",
                "02/11/2023"
            ),
            MemberHistoryItem(
                3,
                "Penukaran voucher 250.000",
                "29/10/2023"
            )
        )
    }

    fun getListEventSlider():List<String>{
        return listOf(
            "${BuildConfig.BASE_URL}view/assets/images/Tahfidz.png",
            "${BuildConfig.BASE_URL}view/assets/images/Tahfidz.png",
            "${BuildConfig.BASE_URL}view/assets/images/Tahfidz.png",
        )
    }

    fun getListEvent():List<EventPlayground>{
        return List(5){
            EventPlayground(
                "Tahfiz Untuk Anak",
                "${BuildConfig.BASE_URL}view/assets/images/Tahfidz.png",
                "24/01/2024",
                "14:00"
            )
        }
    }

    fun getPlaygroundModel():List<PlaygroundModel>{
        return listOf(
            OperationalHours("10.00","21.00"),
            getTicketInfoSchedule()
        )
    }

    private fun getTicketInfoSchedule():TicketInfo{
        return TicketInfo(
            additionalInfo = "Harga tiket sudah termasuk 1 orang pendamping",
            ticketDayPrices = listOf(
                TicketDayPrice(
                    "Senin - Kamis",
                     "50.000",
                ),
                TicketDayPrice(
                    "Jumat - Ahad",
                    "60.000",
                ),
                TicketDayPrice(
                    "Hari Libur",
                    "60.000",
                )
            ),
        )
    }

}