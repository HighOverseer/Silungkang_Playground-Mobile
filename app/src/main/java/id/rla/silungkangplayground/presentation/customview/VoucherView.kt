package id.rla.silungkangplayground.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.card.MaterialCardView
import id.rla.silungkangplayground.R


class VoucherView : MaterialCardView {

    private lateinit var paint:Paint
    private val curvePath: Path = Path()



    private var outerToMiddleDiffCurve = 20f.toDp()
    private var middleToInnerDiffCurve = 2f.toDp()
    private var innerToPeakDiffCurve = 6f.toDp()

    private var heightDiffCurve = 12f.toDp()

    private val xStartingPosition
        get() = measuredWidth/5f

    private var curveScale:Float = 1f
        set(value) {
            outerToMiddleDiffCurve *= value
            middleToInnerDiffCurve *= value
            innerToPeakDiffCurve *= value

            heightDiffCurve *= value
            field = value
        }

    /*constructor(context: Context) : super(context) {
        init(attrs)
    }*/

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }



    private fun init(attrs: AttributeSet) {
        curveScale = getAttributeCurveScaleValue(attrs)


        radius = 2f.toDp()
        strokeWidth = 0
        setCardBackgroundColor(Color.TRANSPARENT)
        paint = Paint().apply {
            color = ResourcesCompat.getColor(resources, R.color.purple_50, null)
            isAntiAlias = true
        }
    }

    private fun getAttributeCurveScaleValue(attrs:AttributeSet):Float{
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.VoucherView,
            0, 0
        ).apply {
            return try {
                getFloat(R.styleable.VoucherView_curve_scale, 0.7f)
            }finally {
                recycle()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*path.moveTo(measuredWidth/5f-20, measuredHeight.toFloat())
  path.cubicTo(
      measuredWidth/5f,
      measuredHeight.toFloat(),
      measuredWidth/5f+6,
      measuredHeight.toFloat()-20,
      measuredWidth/5f+6+12,
      measuredHeight - 20f
  )

  //path.moveTo(145f, measuredHeight - 50f)
  path.cubicTo(
      measuredWidth/5f+6+12+12,
      measuredHeight - 20f,
      measuredWidth/5f+6+12+12+6,
      measuredHeight.toFloat(),
      measuredWidth/5f+6+12+12+6+20,
      measuredHeight.toFloat()
  )

  path.lineTo(measuredWidth/5f-20, measuredHeight.toFloat())*/


        /*path.moveTo(measuredWidth/5f-20, 0f)
        path.cubicTo(
            measuredWidth/5f,
             0f,
            measuredWidth/5f+6 ,
            20f,
            measuredWidth/5f+6+12 ,
            20f
        )

        path.cubicTo(
            measuredWidth/5f+6+12+12,
            20f,
            measuredWidth/5f+6+12+12+6,
            0f,
            measuredWidth/5f+6+12+12+6+20,
            0f
        )
        path.lineTo(measuredWidth/5f-20, 0f)*/

        setCurvePath(curvePath, CurvePosition.TOP)
        setCurvePath(curvePath, CurvePosition.BOTTOM)
        clipOutFromCanvas(canvas, curvePath)

        canvas.drawPaint(paint)

    }

    private fun setCurvePath(
        path: Path,
        position:CurvePosition
    ){
        val baseYPosition:Float
        val peakCurvePosition:Float
        when(position){
            CurvePosition.TOP -> {
                baseYPosition = 0f
                peakCurvePosition = heightDiffCurve
            }
            CurvePosition.BOTTOM -> {
                baseYPosition = measuredHeight.toFloat()
                peakCurvePosition = measuredHeight -  heightDiffCurve
            }
        }

        path.moveTo(xStartingPosition-outerToMiddleDiffCurve, baseYPosition)
        path.cubicTo(
            xStartingPosition,
            baseYPosition,
            xStartingPosition+middleToInnerDiffCurve ,
            peakCurvePosition,
            xStartingPosition+middleToInnerDiffCurve+innerToPeakDiffCurve ,
            peakCurvePosition
        )

        path.cubicTo(
            xStartingPosition+middleToInnerDiffCurve+ (2*innerToPeakDiffCurve),
            peakCurvePosition,
            xStartingPosition+middleToInnerDiffCurve+(2*innerToPeakDiffCurve)+middleToInnerDiffCurve,
            baseYPosition,
            xStartingPosition+middleToInnerDiffCurve+(2*innerToPeakDiffCurve)+middleToInnerDiffCurve+outerToMiddleDiffCurve,
            baseYPosition
        )
        path.lineTo(xStartingPosition-outerToMiddleDiffCurve, baseYPosition)

    }


    enum class CurvePosition{
        TOP, BOTTOM;
    }


    private fun Float.toDp():Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics)
    }

    private fun clipOutFromCanvas(canvas: Canvas?, path: Path) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas?.clipPath(path, Region.Op.DIFFERENCE)
        } else {
            canvas?.clipOutPath(path)
        }
    }


}