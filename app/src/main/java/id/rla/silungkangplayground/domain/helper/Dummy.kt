package id.rla.silungkangplayground.domain.helper

import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.domain.model.CreativeCorner
import id.rla.silungkangplayground.domain.model.Event
import id.rla.silungkangplayground.domain.model.Voucher
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

    fun getListCreativeCorners():List<CreativeCorner>{
        return listOf(
            CreativeCorner(
                "Tahfiz untuk\nAnak",
                R.drawable.ic_tahfiz
            ), CreativeCorner(
                "Magenetik\nDrawing Board",
                R.drawable.ic_canvas
            ), CreativeCorner(
                "Puzzle\nTetris",
                R.drawable.ic_puzzle
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

    fun getListEvent():List<Event>{
        return List(5){
            Event(
                "Tahfiz Untuk Anak $it",
                "https://s3-alpha-sig.figma.com/img/7987/8d2b/86fd8f01386c39c920f0065dccf28ed8?Expires=1707696000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=TjsjknSP03Fcf8y1iiPPiG1uZftt8UfvrcoIM1ABO2IJo6oN-F0k3O4lYvcLlJWTq1wsHv021KsmJeImFG5vL8HbtqN2p0TD5JgwdqudhjS86RdMG2-Z17sLNL4ZXwr2EYjLKCjFmuUQVueJ5cDakNWQnwliB9o5TLrptkzGIB1woCHTUXBTPL2S1n2vf-yXIjLAT6gADRCnw6X8iwjwD~tkRvUQWEP0VeZ5ufZbchbvCypvn4lvjBJsHJpQ81IcpICUJqB4wDkDRJ3pOjrHD7IB6co5vCg0~U1TsUmnJtIACKn9DFOwEOV0NeL3T4FekPo0A6CUMr8i-HvdCMaTgQ__",
                "24/01/2024",
                "14:00"
            )
        }
    }

}