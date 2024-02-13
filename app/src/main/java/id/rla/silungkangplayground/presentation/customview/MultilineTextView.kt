package id.rla.silungkangplayground.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatTextView

class MultilineTextView:AppCompatTextView {
    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }

    private fun init(){
        setOnFocusChangeListener { _, hasFocus ->
            inputType = if (hasFocus){
                EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE.or(EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE).or(EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT)
            }else{
                EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS.or(EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE).or(EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE)
            }
        }
    }

}