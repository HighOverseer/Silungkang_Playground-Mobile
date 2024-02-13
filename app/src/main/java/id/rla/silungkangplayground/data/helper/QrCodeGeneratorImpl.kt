package id.rla.silungkangplayground.data.helper

import android.graphics.Bitmap
import android.util.DisplayMetrics
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import id.rla.silungkangplayground.presentation.util.toDp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QrCodeGeneratorImpl @Inject constructor(
    private val displayMetrics: DisplayMetrics
):QrCodeGenerator{


    override suspend fun generate(
        data:String,
        width:Int,
        height:Int
        ): Bitmap {
        return withContext(Dispatchers.Default) {

            val multiFormatWriter = MultiFormatWriter()
            ensureActive()

            val bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, width.toDp(displayMetrics), height.toDp(displayMetrics))

            val barEncoder = BarcodeEncoder()
            ensureActive()


            barEncoder.createBitmap(bitMatrix)
        }
    }


}