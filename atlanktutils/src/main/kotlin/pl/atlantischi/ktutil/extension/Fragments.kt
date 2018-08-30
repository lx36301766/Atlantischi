package pl.atlantischi.ktutil.extension

import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created on 30/08/2018.

 * @author lx
 */

fun <V : View> android.app.Fragment.bindView(id: Int): ReadOnlyProperty<android.app.Fragment, V?> {
    return object : ReadOnlyProperty<android.app.Fragment, V?> {
        override fun getValue(thisRef: android.app.Fragment, property: KProperty<*>): V? {
            return thisRef.view?.findViewById(id)
        }
    }
}

fun <V : View> android.support.v4.app.Fragment.bindView(id: Int): ReadOnlyProperty<android.support.v4.app.Fragment, V?> {
    return object : ReadOnlyProperty<android.support.v4.app.Fragment, V?> {
        override fun getValue(thisRef: android.support.v4.app.Fragment, property: KProperty<*>): V? {
            return thisRef.view?.findViewById(id)
        }
    }
}
