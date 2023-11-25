@file:Suppress("unused")

package com.marries.atoolkit

import android.app.Application
import android.content.Context
import androidx.startup.Initializer

internal class AppInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        instance = context as Application
    }

    override fun dependencies() = emptyList<Class<Initializer<*>>>()

    companion object {
        internal lateinit var instance: Application private set
    }
}
