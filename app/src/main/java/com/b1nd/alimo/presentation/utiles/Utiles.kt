package com.b1nd.alimo.presentation.utiles

import android.view.View
import android.view.animation.Animation
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HideAnimListener(private val view: View): Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {

    }

    //애니메이션이 끝나면 뷰를 감춘다
    override fun onAnimationEnd(p0: Animation?) {
        view.visibility = View.GONE
    }

    override fun onAnimationRepeat(p0: Animation?) {

    }

}

fun getTimeString(extension: String): String =
    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss")) + "." + extension

fun convertBytes(size: Int): String {
    val kb: Double = 1000.0
    val mb = kb * kb
    val gb = mb * kb
    val tb = gb * kb

    val decimalFormat = DecimalFormat("#.##")

    return when {
        size < kb -> "$size B"
        size < mb -> "${decimalFormat.format(size / kb)}KB"
        size < gb -> "${decimalFormat.format(size / mb)}MB"
        size < tb -> "${decimalFormat.format(size / gb)}GB"
        else -> "${decimalFormat.format(size / tb)}TB"
    }
}
