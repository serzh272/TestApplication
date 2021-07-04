package ru.serzh272.testapplication.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ru.serzh272.testapplication.R
import ru.serzh272.testapplication.extensions.dpToPx
import kotlin.math.min

class CircleImageView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {
    var borderWidth: Float = 4f
        set(value) {
            field = value
            invalidate()
        }
    var borderColor: Int = Color.RED
        set(value) {
            field = value
            invalidate()
        }

    var bgColor: Int = 0

    var initials: String? = "USD"
        set(value) {
            field = value
            invalidate()
        }
    private val pDst = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect: Rect
        get() = Rect(0, 0, width, height)


    init {
        context.obtainStyledAttributes(attrs, R.styleable.CircleImageView).apply {
            try {
                borderWidth = getDimension(R.styleable.CircleImageView_cv_borderWidth, context.dpToPx(4))
                borderColor = getColor(R.styleable.CircleImageView_cv_borderColor, Color.RED)
                bgColor = getColor(R.styleable.CircleImageView_cv_backgroundColor, Color.CYAN)
                initials = getString(R.styleable.CircleImageView_cv_initials) ?: initials
            } finally {
                recycle()
            }
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val size = min(viewRect.right, viewRect.height())
        pDst.color = bgColor
        pDst.style = Paint.Style.FILL
        canvas?.drawCircle(
            size.toFloat() / 2,
            size.toFloat() / 2,
            size.toFloat() / 2,
            pDst
        )
        pDst.color = borderColor
        pDst.style = Paint.Style.STROKE
        pDst.strokeWidth = borderWidth
        canvas?.drawCircle(
            size.toFloat() / 2,
            size.toFloat() / 2,
            size.toFloat() / 2 - borderWidth/2,
            pDst
        )
        textPaint.color = Color.BLACK
        textPaint.textSize = (height - borderWidth*2) / 2.5f
        val measureText = textPaint.measureText(initials) + context.dpToPx(8)
        if ( measureText > width - borderWidth*2){
            textPaint.textSize *= (width - borderWidth*2)/measureText
        }
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = Typeface.DEFAULT_BOLD
        val ht = textPaint.descent() + textPaint.ascent()
        canvas?.drawText(initials ?: "", width / 2f, height / 2 - ht / 2, textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minDim = measuredHeight.coerceAtMost(measuredWidth)
        setMeasuredDimension(minDim, minDim)
    }
}