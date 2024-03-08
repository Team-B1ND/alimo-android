package com.b1nd.alimo.presentation.utiles

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun <T> Fragment.collectFlow(
    state: Flow<T>,
    action: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            state.collectLatest {
                action(it)
            }
        }
    }
}

fun <T> Fragment.collectStateFlow(
    state: StateFlow<T>,
    action: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            state.collectLatest {
                action(it)
            }
        }
    }
}

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

internal fun ViewModel.launchMain(action: () -> Unit) {
    viewModelScope.launch(Dispatchers.Main) {
        action()
    }
}

internal fun ViewModel.launchIO(action: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        action()
    }
}

internal fun Event.onSuccessEvent(onMessage: (String) -> Unit) {
    when(this) {
        is Event.Success -> {
            onMessage(this.message)
        }
        else -> {}
    }
}

internal fun ImageView.loadImage(url: String, onLoad: (String) -> (Unit) = {}) {
    Glide.with(this)
        .load(url)
        .listener(createLogListener(onLoad))
        .centerCrop()
        .into(this)
}

internal fun ImageView.loadNotCropImage(url: String, onLoad: (String) -> (Unit) = {}) {
    Glide.with(this)
        .load(url)
        .listener(createLogListener(onLoad))
        .into(this)
}

private fun createLogListener(onLoad: (String) -> (Unit) = {}): RequestListener<Drawable> {
    return object : RequestListener<Drawable> {

        // Image Load 실패 시 CallBack
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>,
            isFirstResource: Boolean,
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>?,
            dataSource: DataSource,
            isFirstResource: Boolean,
        ): Boolean {
            if (resource is BitmapDrawable) {
                val bitmap = resource.bitmap
                onLoad("${bitmap.width}:${bitmap.height}")
                Log.d(
                    "Glide", String.format(
                        "bitmap %,d btyes, size: %d x %d",
                        bitmap.byteCount,		// 리사이징된 이미지 바이트
                        bitmap.width,			// 이미지 넓이
                        bitmap.height			// 이미지 높이
                    )
                )
            }
            return false
        }
    }
}

fun Fragment.startAnimationWithShow(view: View, id: Int) {
    view.visibility = View.VISIBLE  //애니메이션 전에 뷰를 보이게 한다
    view.startAnimation(AnimationUtils.loadAnimation(requireContext(), id)) //애니메이션 설정&시작
}

//사라지기
fun Fragment.startAnimationWithHide(view: View, id: Int) {
    val exitAnim = AnimationUtils.loadAnimation(requireContext(), id)    //애니메이션 설정
    exitAnim.setAnimationListener(HideAnimListener(view))   //리스너를 통해 애니메이션이 끝나면 뷰를 감춘다
    view.startAnimation(exitAnim) //애니메이션 시작
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.startActivityWithFinishAll(activity: Class<*>) {
    val intent = Intent(context!!.applicationContext, activity)
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    startActivity(intent)
    this.requireActivity().finishAffinity()
}


fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ImageView.setImageResourceAndClearTint(@DrawableRes id: Int) {
    this.setImageResource(id)
    this.imageTintList = null
}

fun Int.toConvertBytes(): String = convertBytes(this)

fun Fragment.getResourceString(@StringRes id: Int) =
    requireContext().getString(id)

fun LocalDateTime.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분")
    return this.format(formatter).replace("PM", "오후").replace("AM", "오전")
}