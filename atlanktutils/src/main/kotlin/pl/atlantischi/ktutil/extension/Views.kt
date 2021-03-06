package pl.atlantischi.ktutil.extension

import android.view.View

/**
 * Created on 29/08/2018.

 * @author lx
 */

fun <V : View> View.bindView(id: Int) = lazy { findViewById(id) as? V }

fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }

val View.isVisible: Boolean
    inline get() = visibility == View.VISIBLE
