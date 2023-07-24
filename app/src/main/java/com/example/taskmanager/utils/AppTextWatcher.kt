package com.example.taskmanager.utils

import android.text.Editable
import android.text.TextWatcher

class AppTextWatcher (val onSuccess:(Editable?) -> Unit): TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onSuccess(s as Editable?)
    }

    override fun afterTextChanged(s: Editable?) {}
}