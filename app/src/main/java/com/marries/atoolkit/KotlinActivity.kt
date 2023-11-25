package com.marries.atoolkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marries.atoolkit.android.debug.ADebugPack
import com.marries.atoolkit.android.extensions.Settings
import com.marries.atoolkit.android.extensions.toast
import com.marries.atoolkit.common.reflect.AReflect
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        ADebugPack.addFloatingButtonGroup(this).build {
            addButton("AppDebug状态") { baseContext.toast(isAppDebug.toString()) }
            addButton("是否为深色模式") { baseContext.toast(isSystemInDarkTheme.toString()) }
            addButton("包名") { baseContext.toast(packageName) }
            addButton("testFlow") {
                lifecycleScope.launch {
                    (1..3).asFlow().collect { value -> toast("$value") }
                }
            }
            addButton("testReflect") {
                toast(
                    AReflect.withClass(LogTest::class.java).construct().invoke("get", true, false).get<Boolean>()
                )
            }
            addButton("testArray") {
                val array = arrayOf(1, 2, 3, 4, 5).apply { reverse(0, 2) }
                toast(array.contentToString())
            }
            addButton("testGetProp") {
                toast(Settings.getProperty("ro.product.name", "11"))
            }
            addButton("testArray") {
                val array = arrayOf(1, 2, 3, 4, 5)
                array.reverse(0, 2)
                toast(array.contentToString())
            }
        }
    }
}