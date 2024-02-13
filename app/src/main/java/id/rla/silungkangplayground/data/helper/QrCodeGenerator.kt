package id.rla.silungkangplayground.data.helper

import android.graphics.Bitmap

interface QrCodeGenerator {
    suspend fun generate(data:String, width:Int = WIDTH_DEFAULT, height:Int = HEIGHT_DEFAULT):Bitmap

    companion object{
        private const val WIDTH_DEFAULT = 200
        private const val HEIGHT_DEFAULT = 200
    }
}