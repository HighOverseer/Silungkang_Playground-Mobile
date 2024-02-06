package id.rla.silungkangplayground.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.presentation.factory.ViewModelFactory


inline fun <reified T:ViewModel>ViewModelStoreOwner.obtainViewModel(applicationContext: Context):T{
    val factory = ViewModelFactory.getInstance(applicationContext)
    return ViewModelProvider(this, factory)[T::class.java]
}

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

fun Int.toDp(displayMetrics: DisplayMetrics): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics)
        .toInt()
}

fun ImageView.loadImage(imageResId: Int) {
    Glide.with(context)
        .load(imageResId)
        .into(this)

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
