package id.rla.silungkangplayground.presentation.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import id.rla.silungkangplayground.domain.common.StringRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


/*inline fun <reified T:ViewModel>ViewModelStoreOwner.obtainViewModel(applicationContext: Context):T{
    val factory = ViewModelFactory.getInstance(applicationContext)
    return ViewModelProvider(this, factory)[T::class.java]
}*/

fun Context.showToast(message:String){
    return Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(stringRes: StringRes){
    return Toast.makeText(this, stringRes.getValue(this), Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(imageUrlOrUri: String) {
    Glide.with(context)
        .load(imageUrlOrUri)
        .into(this)

}

fun String.toDate(format: String): Date {
    val simpleDateFormat = SimpleDateFormat(
        format,
        Locale.getDefault()
    )
    return try {
        simpleDateFormat.parse(this) as Date
    } catch (e: Exception) {
        getCurrentDate()
    }
}

fun Date.toString(format: String): String {
    val simpleDateFormat = SimpleDateFormat(
        format,
        Locale.getDefault()
    )
    return try {
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        getCurrentDateInString("yyyy-MM-dd")!!
    }
}

fun getCurrentDateInString(format: String): String? {
    val simpleDateFormat = SimpleDateFormat(
        format,
        ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
    )
    val currentDate = getCurrentDate()
    return try {
        simpleDateFormat.format(currentDate)
    } catch (e: Exception) {
        null
    }
}

fun getCurrentDate(): Date = Calendar.getInstance().time

fun Int.toDp(displayMetrics: DisplayMetrics): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics)
        .toInt()
}

fun ImageView.loadImage(imageResId: Int) {
    Glide.with(context)
        .load(imageResId)
        .into(this)

}

fun ImageView.loadImage(imageBitmap: Bitmap) {
    Glide.with(context)
        .load(imageBitmap)
        .into(this)

}

suspend fun String.generateQrCode(applicationContext:Context):Bitmap{
    return withContext(Dispatchers.Default) {

        val multiFormatWriter = MultiFormatWriter()
        ensureActive()

        val displayMetrics = applicationContext.resources.displayMetrics
        val bitMatrix = multiFormatWriter.encode(this@generateQrCode, BarcodeFormat.QR_CODE, 200.toDp(displayMetrics), 200.toDp(displayMetrics))

        val barEncoder = BarcodeEncoder()
        ensureActive()

        barEncoder.createBitmap(bitMatrix)
    }
}


fun <T> LifecycleOwner.collectLatestOnLifeCycleStarted(stateFlow: StateFlow<T>, onCollectLatest: suspend (T) -> Unit){
    this.lifecycleScope.launch {
        this@collectLatestOnLifeCycleStarted.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            stateFlow.collectLatest(action = onCollectLatest)
        }
    }
}

fun <T> LifecycleOwner.collectChannelFlowOnLifecycleStarted(flow: Flow<T>, onCollect: suspend (T) -> Unit){
    this.lifecycleScope.launch {
        this@collectChannelFlowOnLifecycleStarted.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            withContext(Dispatchers.Main.immediate){
                flow.collect(onCollect)
            }
        }
    }
}

fun LifecycleOwner.doSomethingOnlyLifeCycleStarted(doSomething: suspend () -> Unit):Job{
    return this.lifecycleScope.launch {
        this@doSomethingOnlyLifeCycleStarted.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            doSomething()
        }
    }
}

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
    val  drawable = ContextCompat.getDrawable(context, drawableId)
    val bitmap = Bitmap.createBitmap(
        drawable?.intrinsicWidth ?: return null,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
