package id.rla.silungkangplayground.presentation.customview

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import id.rla.silungkangplayground.R

class CustomEditTextPassword : AppCompatEditText, View.OnTouchListener {
    //private lateinit var errorDrawable: Drawable
    private lateinit var passwordEyeDrawable: Drawable
    private val mPaint = Paint(Paint.FAKE_BOLD_TEXT_FLAG)

    private var isPasswordVisible = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        background = ResourcesCompat.getDrawable(resources, R.drawable.edit_text_bg, null)
        /*errorDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_error_24) as Drawable*/
        passwordEyeDrawable =
            ContextCompat.getDrawable(context, R.drawable.hide_password_eye) as Drawable

        setOnTouchListener(this)

        setHidePassButton(false)

        /*addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        showError()
                    } else {
                        hideError()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })*/
    }

    /*override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (compoundDrawables[0] != null) {
            mPaint.apply {
                textSize = 11F.toSp()
                color = ResourcesCompat.getColor(resources, R.color.black, null)
                typeface = ResourcesCompat.getFont(context, R.font.inter_medium)
            }
            val x = 46F.toDp()
            val y = 52F.toDp()
            canvas.drawText("Password must have more or atleast 8 character", x, y, mPaint)
        }


    }*/

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        //check if there is Hide Pass Icon
        if (compoundDrawables[2] != null) {
            val hidePassButtonStart: Float
            val hidePassButtonEnd: Float
            var isHidePassDrawableClicked = false

            //check if user click the Hide Pass Icon based on Layout Direction
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                hidePassButtonStart = (passwordEyeDrawable.intrinsicWidth + paddingStart).toFloat()
                if (event?.x != null) {
                    when {
                        event.x < hidePassButtonStart -> isHidePassDrawableClicked = true
                    }
                }
            } else {
                hidePassButtonEnd =
                    (width - paddingEnd - passwordEyeDrawable.intrinsicWidth).toFloat()
                if (event?.x != null) {
                    when {
                        event.x > hidePassButtonEnd -> isHidePassDrawableClicked = true
                    }
                }
            }

            //do an act when user does click on Hide Pass Icon
            if (isHidePassDrawableClicked) {
                when (event?.action) {
                    //when user is pressing the icon, set it visibility
                    MotionEvent.ACTION_DOWN -> {
                        if (isPasswordVisible) setHidePassButton(true) else setHidePassButton(false)
                        return true
                    }

                    // after user is pressing the icon, then user start to lift their finger-
                    // set password and the icon visibility
                    MotionEvent.ACTION_UP -> {
                        transformationMethod = if (isPasswordVisible) {
                            setHidePassButton(false)
                            PasswordTransformationMethod()
                        } else {
                            setHidePassButton(true)
                            null
                        }
                        if (!text.isNullOrEmpty()) setSelection(text.toString().length)

                        return true
                    }

                    else -> return false
                }
            } else return false

        }
        return false
    }


    private fun setHidePassButton(isVisible: Boolean) {
        if (isVisible) {
            passwordEyeDrawable =
                ContextCompat.getDrawable(context, R.drawable.show_password_eye) as Drawable
            isPasswordVisible = true
        } else {
            passwordEyeDrawable =
                ContextCompat.getDrawable(context, R.drawable.hide_password_eye) as Drawable
            isPasswordVisible = false
        }

        setEditTextDrawable(endOfTheText = passwordEyeDrawable)

        /*if (compoundDrawables[0] == null) {
            setEditTextDrawable(endOfTheText = passwordEyeDrawable)
        } else {
            setEditTextDrawable(startOfTheText = errorDrawable, endOfTheText = passwordEyeDrawable)
        }*/

    }


    /*private fun showError() {
        if (compoundDrawables[2] == null) {
            setEditTextDrawable(startOfTheText = errorDrawable)
        } else {
            setEditTextDrawable(startOfTheText = errorDrawable, endOfTheText = passwordEyeDrawable)
        }

    }

    private fun hideError() {
        if (compoundDrawables[2] == null) {
            setEditTextDrawable()
        } else {
            setEditTextDrawable(endOfTheText = passwordEyeDrawable)
        }
    }*/

    //function for setting Hide Pass Icon visibility
    private fun setEditTextDrawable(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    private fun Float.toDp(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            resources.displayMetrics
        )
    }

    private fun Float.toSp(): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, resources.displayMetrics)
    }

}