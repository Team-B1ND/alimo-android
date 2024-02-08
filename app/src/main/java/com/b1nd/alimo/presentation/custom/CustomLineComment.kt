package com.b1nd.alimo.presentation.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.b1nd.alimo.R


class CustomLineComment(context: Context?, Attrif: AttributeSet) : View(context, Attrif) {
    private val paint = Paint()
    private val path = Path()
    private var rectF: RectF? = null

    init {
        paint.color = getContext().getColor(R.color.Gray300) // 원하는 색으로 변경 가능
        paint.strokeWidth = 2.5f * resources.displayMetrics.density // 선의 굵기 설정(dp)
        paint.style = Paint.Style.STROKE // 선 스타일 설정
        paint.isDither = true
        paint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF = RectF(0f, 0f, w.toFloat() - (getPaddingStart() + getPaddingEnd()), h.toFloat() - (getPaddingTop() + getPaddingBottom()))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("TAG", "onDraw: $rectF")
        rectF?.let {
//            val radius = it.height() / 6 // 호의 반지름
            path.moveTo(2f, 0f)
            path.lineTo(2f, it.bottom)
            canvas.drawPath(path, paint)
        }
    }
}