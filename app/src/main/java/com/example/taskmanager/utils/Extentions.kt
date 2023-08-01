package com.example.taskmanager.utils

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String){
    Glide.with(this).load(url).into(this)
}

fun showToast(message: String, activity: AppCompatActivity) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}

fun EditText.checkingForEmptyLine(): String{
    if (this.text.isNotEmpty()){
        return this.text.toString()
    }else{
        throw EditTextEmptyLineException("This field must not be empty")
    }
}