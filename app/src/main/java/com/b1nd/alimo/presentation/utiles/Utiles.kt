package com.b1nd.alimo.presentation.utiles

import android.view.View
import android.view.animation.Animation
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


