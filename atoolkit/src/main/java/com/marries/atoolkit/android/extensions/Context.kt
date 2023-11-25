package com.marries.atoolkit.android.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.MainThread

@MainThread
fun Context.toast(message: Any, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, "$message", duration).show()
