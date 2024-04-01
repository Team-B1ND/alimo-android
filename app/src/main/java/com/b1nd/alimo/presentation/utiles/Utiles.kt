package com.b1nd.alimo.presentation.utiles

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.animation.Animation
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest
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

class BackgroundColorTransform(private val backgroundColor: Int) : BitmapTransformation() {
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        // 새 Bitmap을 생성하고 캔버스에 원하는 배경색을 적용합니다.
        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColor)

        // 원본 이미지를 새로운 Bitmap 위에 그립니다.
        canvas.drawBitmap(toTransform, 0f, 0f, null)
        return bitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(("BackgroundColorTransform:$backgroundColor").toByteArray(Key.CHARSET))
    }
}

fun isDebuggable(context: Context): Boolean {
    var debuggable = false;
    val pm = context.getPackageManager();
    try {
        val appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
        appInfo.flags
        ApplicationInfo.FLAG_DEBUGGABLE
        (appInfo.flags == ApplicationInfo.FLAG_DEBUGGABLE)
        debuggable = (0 == (appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE))
    } catch (e: PackageManager.NameNotFoundException) {
        /* debuggable variable will remain false */
    }
    return debuggable;
}