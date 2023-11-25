package com.marries.atoolkit.android.extensions

import android.annotation.SuppressLint
import android.view.View

@SuppressLint("WrongConstant")
fun View.spinVisibility() {
    visibility = View.GONE or View.VISIBLE xor visibility
}
