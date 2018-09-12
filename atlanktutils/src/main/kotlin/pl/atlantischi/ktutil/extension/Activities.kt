package pl.atlantischi.ktutil.extension

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import java.util.*

/**
 * Created on 28/08/2018.

 * @author lx
 */

//inline fun <reified T : View> Activity.findView(id: Int) = findViewById(id) as T

//fun <V : View> Activity.bindView(id: Int) = GetValueDelegate<Activity, V?> { findViewById(id) }

fun <V : View> Activity.bindView(id: Int) = lazy<V?> { findViewById(id) }

@JvmOverloads
inline fun <reified T : Activity> Activity.launchActivityForResult(
        flags: Int? = null,
        data: Uri? = null,
        categories: String? = null,
        bundle: Bundle? = null,
//        noinline callback: ((Int, Intent)-> Unit)? = null,
        noinline intentInterceptor: ((Intent)-> Unit)? = null
) {
    val intent = Intent(this, T::class.java)
    flags?.let { intent.flags = it }
    data?.let { intent.data = it }
    categories?.let { intent.addCategory(it)}
    bundle?.let { intent.putExtras(it) }
    //TODO random only code
    val randomRequestCode = Random().nextInt(100000)
//    callback?.let{ resultCallbacks[randomRequestCode] = it }
    intentInterceptor?.invoke(intent)
    startActivityForResult(intent, randomRequestCode)
}
