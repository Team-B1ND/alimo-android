package com.b1nd.alimo.feature.onboarding.parent.join.first

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class GenericTextWatcher(private val currentView: TextInputEditText, private val nextView: TextInputEditText?) : TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        if (editable.length == 1) {
            nextView?.requestFocus()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // 변경 전에 수행할 작업
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // 텍스트가 변경될 때 수행할 작업
    }
}

class GenericKeyEvent(private val currentView: EditText, private val previousView: TextInputEditText?) : View.OnKeyListener {
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.text.isEmpty()) {
            previousView?.text = null
            previousView?.requestFocus()
            return true
        }
        return false
    }
}