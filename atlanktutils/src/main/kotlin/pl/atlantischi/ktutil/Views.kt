package pl.atlantischi.ktutil

import android.app.Activity
import android.view.View

/**
 * Created on 29/08/2018.

 * @author lx
 */

inline fun <reified T : View> Activity.findView(id: Int) = findViewById(id) as T

fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }

fun View.isVisible(): Boolean = visibility == View.VISIBLE
