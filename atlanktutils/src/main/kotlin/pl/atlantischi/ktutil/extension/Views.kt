package pl.atlantischi.ktutil.extension

import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created on 29/08/2018.

 * @author lx
 */

class ReadOnlyDelegate<R, T>(val getValue: (R)-> T) : ReadOnlyProperty<R, T> {

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        return getValue(thisRef)
    }

}

fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }

val View.isVisible: Boolean
    inline get() = visibility == View.VISIBLE

