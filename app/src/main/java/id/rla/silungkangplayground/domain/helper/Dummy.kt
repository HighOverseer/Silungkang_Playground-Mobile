package id.rla.silungkangplayground.domain.helper

import android.content.Context
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.model.EventPlayground
import id.rla.silungkangplayground.domain.model.OperationalHours
import id.rla.silungkangplayground.domain.model.PlaygroundModel
import id.rla.silungkangplayground.domain.model.TicketDayPrice
import id.rla.silungkangplayground.domain.model.TicketInfo
import id.rla.silungkangplayground.domain.model.Voucher
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.VoucherType
import id.rla.silungkangplayground.presentation.util.generateQrCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            "https://s3-alpha-sig.figma.com/img/7987/8d2b/86fd8f01386c39c920f0065dccf28ed8?Expires=1707696000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=TjsjknSP03Fcf8y1iiPPiG1uZftt8UfvrcoIM1ABO2IJo6oN-F0k3O4lYvcLlJWTq1wsHv021KsmJeImFG5vL8HbtqN2p0TD5JgwdqudhjS86RdMG2-Z17sLNL4ZXwr2EYjLKCjFmuUQVueJ5cDakNWQnwliB9o5TLrptkzGIB1woCHTUXBTPL2S1n2vf-yXIjLAT6gADRCnw6X8iwjwD~tkRvUQWEP0VeZ5ufZbchbvCypvn4lvjBJsHJpQ81IcpICUJqB4wDkDRJ3pOjrHD7IB6co5vCg0~U1TsUmnJtIACKn9DFOwEOV0NeL3T4FekPo0A6CUMr8i-HvdCMaTgQ__",
            "https://s3-alpha-sig.figma.com/img/7987/8d2b/86fd8f01386c39c920f0065dccf28ed8?Expires=1707696000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=TjsjknSP03Fcf8y1iiPPiG1uZftt8UfvrcoIM1ABO2IJo6oN-F0k3O4lYvcLlJWTq1wsHv021KsmJeImFG5vL8HbtqN2p0TD5JgwdqudhjS86RdMG2-Z17sLNL4ZXwr2EYjLKCjFmuUQVueJ5cDakNWQnwliB9o5TLrptkzGIB1woCHTUXBTPL2S1n2vf-yXIjLAT6gADRCnw6X8iwjwD~tkRvUQWEP0VeZ5ufZbchbvCypvn4lvjBJsHJpQ81IcpICUJqB4wDkDRJ3pOjrHD7IB6co5vCg0~U1TsUmnJtIACKn9DFOwEOV0NeL3T4FekPo0A6CUMr8i-HvdCMaTgQ__",
            "https://s3-alpha-sig.figma.com/img/7987/8d2b/86fd8f01386c39c920f0065dccf28ed8?Expires=1707696000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=TjsjknSP03Fcf8y1iiPPiG1uZftt8UfvrcoIM1ABO2IJo6oN-F0k3O4lYvcLlJWTq1wsHv021KsmJeImFG5vL8HbtqN2p0TD5JgwdqudhjS86RdMG2-Z17sLNL4ZXwr2EYjLKCjFmuUQVueJ5cDakNWQnwliB9o5TLrptkzGIB1woCHTUXBTPL2S1n2vf-yXIjLAT6gADRCnw6X8iwjwD~tkRvUQWEP0VeZ5ufZbchbvCypvn4lvjBJsHJpQ81IcpICUJqB4wDkDRJ3pOjrHD7IB6co5vCg0~U1TsUmnJtIACKn9DFOwEOV0NeL3T4FekPo0A6CUMr8i-HvdCMaTgQ__"
        )
    }

    fun getListEvent():List<EventPlayground>{
        return List(5){
            EventPlayground(
                "Tahfiz Untuk Anak",
                "https://s3-alpha-sig.figma.com/img/7987/8d2b/86fd8f01386c39c920f0065dccf28ed8?Expires=1707696000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=TjsjknSP03Fcf8y1iiPPiG1uZftt8UfvrcoIM1ABO2IJo6oN-F0k3O4lYvcLlJWTq1wsHv021KsmJeImFG5vL8HbtqN2p0TD5JgwdqudhjS86RdMG2-Z17sLNL4ZXwr2EYjLKCjFmuUQVueJ5cDakNWQnwliB9o5TLrptkzGIB1woCHTUXBTPL2S1n2vf-yXIjLAT6gADRCnw6X8iwjwD~tkRvUQWEP0VeZ5ufZbchbvCypvn4lvjBJsHJpQ81IcpICUJqB4wDkDRJ3pOjrHD7IB6co5vCg0~U1TsUmnJtIACKn9DFOwEOV0NeL3T4FekPo0A6CUMr8i-HvdCMaTgQ__",
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