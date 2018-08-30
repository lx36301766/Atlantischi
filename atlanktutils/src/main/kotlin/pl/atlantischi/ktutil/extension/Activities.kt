package pl.atlantischi.ktutil.extension

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created on 28/08/2018.

 * @author lx
 */

inline fun <reified T : View> Activity.findView(id: Int) = findViewById(id) as T

fun <V : View> Activity.bindView(id: Int): ReadOnlyProperty<Activity, V?> {
    return object : ReadOnlyProperty<Activity, V?> {
        override fun getValue(thisRef: Activity, property: KProperty<*>): V? {
            return thisRef.findViewById(id)
        }
    }
}

val resultCallbacks = mutableMapOf<Int, (Int, Intent)-> Unit>()

@JvmOverloads
inline fun <reified T : Activity> Activity.launchActivityForResult(
        flags: Int? = null,
        data: Uri? = null,
        categories: String? = null,
        bundle: Bundle? = null,
        noinline callback: ((Int, Intent)-> Unit)? = null,
        noinline intentInterceptor: ((Intent)-> Unit)? = null
) {
    val intent = Intent(this, T::class.java)
    flags?.let { intent.flags = it }
    data?.let { intent.data = it }
    categories?.let { intent.addCategory(it)}
    bundle?.let { intent.putExtras(it) }
    //TODO random only code
    val randomRequestCode = Random().nextInt(100000)
    callback?.let{ resultCallbacks[randomRequestCode] = it }
    intentInterceptor?.invoke(intent)
    startActivityForResult(intent, randomRequestCode)
}

fun Activity.dispatchActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    resultCallbacks.remove(requestCode)?.invoke(resultCode, data)
}