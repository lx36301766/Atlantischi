package pl.atlantischi.ktutil.extension

import android.app.Activity
import android.content.Intent
import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created on 28/08/2018.

 * @author lx
 */

fun <V : View> Activity.bindView(id: Int) = lazy { findViewById(id) as? V }

class SetValueDelegate<in R, T>(private val delegate: (R)-> T) : ReadWriteProperty<R, T> {

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
    }

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        return delegate(thisRef)
    }

}

inline fun <reified T : Activity> Activity.startActivityForResult(
        noinline intentInterceptor: ((Intent)-> Unit)? = null
) {
    startActivityForResult(Intent(this, T::class.java).apply {
        intentInterceptor?.invoke(this)
    }, 0)
}
