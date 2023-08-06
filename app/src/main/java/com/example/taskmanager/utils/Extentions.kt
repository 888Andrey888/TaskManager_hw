package com.example.taskmanager.utils

import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.taskmanager.R

fun ImageView.loadImage(url: String){
    Glide.with(this).load(url).into(this)
}

fun showToast(message: String, activity: FragmentActivity) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}

fun EditText.checkingForEmptyLine(): String{
    if (this.text.isNotEmpty()){
        return this.text.toString()
    }else{
        throw EditTextEmptyLineException(context.getString(R.string.this_field_must_not_be_empty))
    }
}