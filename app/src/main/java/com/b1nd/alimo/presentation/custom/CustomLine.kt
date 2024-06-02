package com.b1nd.alimo.presentation.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.b1nd.alimo.R


class CustomLine(context: Context?, Attrif: AttributeSet) : View(context, Attrif) {
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
        rectF?.let {
//            val radius = it.height() / 6 // 호의 반지름
            // y 길이 만큼의 선 그리기 (90도 호를 그릴 공간 제외)
            val rr = 15f * resources.displayMetrics.density
            path.moveTo(2f, 0f)
            path.lineTo(2f, it.bottom-rr)

            val arcRec = RectF(2f, it.bottom-rr, 2f+rr, it.bottom)
//            val arcRec = RectF(it.right - it.r    ight / 2, it.bottom - it.right / 2, it.right, it.bottom)
            path.arcTo(arcRec, 180f, -90f)

            path.lineTo(it.right, it.bottom)
            canvas.drawPath(path, paint)
        }
    }
}

//            canvas.drawLine(it.left, it.top, it.left+2, it.bottom - radius, paint)
// 오른쪽으로 90도 꺾이는 호 그리기
//            val arcRectF = RectF(it.left, it.bottom - 2 * radius, it.left + 2 * radius, it.bottom)
//            canvas.drawArc(arcRectF, 180f, -90f, false, paint)

// 남은 x 크기 만큼의 선 그리기
//            canvas.drawLine(it.left + radius, it.bottom-2, it.right, it.bottom+2, paint)