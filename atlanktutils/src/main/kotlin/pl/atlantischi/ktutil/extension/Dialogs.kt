package pl.atlantischi.ktutil.extension

import android.app.Dialog
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created on 30/08/2018.

 * @author lx
 */

fun <V : View> Dialog.bindView(id: Int): ReadOnlyProperty<Dialog, V?> {
    return object : ReadOnlyProperty<Dialog, V?> {
        override fun getValue(thisRef: Dialog, property: KProperty<*>): V? {
            return thisRef.findViewById(id)
        }
    }
}
