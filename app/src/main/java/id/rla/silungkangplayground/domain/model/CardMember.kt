package id.rla.silungkangplayground.domain.model

import android.graphics.Bitmap

data class CardMember(
    val memberId:String,
    val bitmap: Bitmap,
    val owner:String
)