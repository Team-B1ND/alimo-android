package com.b1nd.alimo.utiles

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

internal fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
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