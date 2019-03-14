package pl.atlantischi.ktutil.extension

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import pl.atlantischi.ktutil.intenal.SystemServiceMap.SERVICE_NAMES
import java.io.File

/**
 * Created on 29/08/2018.

 * @author lx
 */


/**
 *  bind system service by name
 */
@Suppress("UNCHECKED_CAST")
fun <T> Context.bindSystemService(name: String) = lazy { getSystemService(name) as? T }

/**
 *  bind system service by Class
 */
@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun <T> Context.bindSystemService(serviceClass: Class<T>) = lazy {
    when {
        Build.VERSION.SDK_INT >= 23 -> getSystemService(serviceClass)
        SERVICE_NAMES.keys.contains(serviceClass) -> getSystemService(SERVICE_NAMES[serviceClass]) as? T
        else -> null
    }
}

/**
 *  bind directory file by name
 */
fun Context.bindDir(name: String, mode: Int = MODE_PRIVATE) = lazy<File> { getDir(name, mode) }

/**
 *  bind string resources from xml
 */
fun Context.bindStringRes(@StringRes id: Int) = lazy { getString(id) }

/**
 *  bind color resources from xml
 */
fun Context.bindColorRes(@ColorRes id: Int) = lazy { resources.getColor(id) }

/**
 *  bind drawable resources from xml
 */
fun Context.bindDrawableRes(@DrawableRes id: Int) = lazy { resources.getDrawable(id) }

/**
 *
 */
inline fun <reified T : Activity> Context.startActivity(noinline interceptor: (Intent.()-> Unit)? = null) {
    startActivity(Intent(this, T::class.java).apply { interceptor?.invoke(this) })
}

/**
 *
 */
fun Context.startIntent(interceptor: (Intent.()-> Unit)? = null) {
    startActivity(Intent().apply { interceptor?.invoke(this) })
}

///**
// *
// */
//inline fun <reified T : Activity> Context.startActivity (
//        flags: Int? = null,
//        data: Uri? = null,
//        type: String? = null,
//        categories: String? = null,
//        bundle: Bundle? = null,
//        noinline interceptor: (Intent.()-> Unit)? = null
//) {
//    val intent = Intent(this, T::class.java)
//    intent.apply {
//        flags?.let { this.flags = it }
//        data?.let { this.data = it }
//        type?.let { this.type = it }
//        categories?.let { addCategory(it)}
//        bundle?.let { putExtras(it) }
//        interceptor?.invoke(this)
//    }
//    startActivity(intent)
//}
//
///**
// *
// */
//fun Context.launchIntent(
//        action: String? = null,
//        flags: Int? = null,
//        data: Uri? = null,
//        type: String? = null,
//        categories: String? = null,
//        bundle: Bundle? = null,
//        interceptor: (Intent.()-> Unit)? = null
//) {
//    val intent = Intent()
//    intent.apply {
//        action?.let { this.action = it }
//        flags?.let { this.flags = it }
//        data?.let { this.data = it }
//        type?.let { this.type = it }
//        categories?.let { addCategory(it)}
//        bundle?.let { putExtras(it) }
//        interceptor?.invoke(this)
//    }
//    startActivity(intent)
//}
