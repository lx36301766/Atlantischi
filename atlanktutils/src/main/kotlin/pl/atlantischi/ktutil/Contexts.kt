package pl.atlantischi.ktutil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle

/**
 * Created on 29/08/2018.

 * @author lx
 */

@JvmOverloads
inline fun <reified T : Activity> Context.launchActivity(
        flags: Int? = null,
        data: Uri? = null,
        categories: String? = null,
        bundle: Bundle? = null,
        noinline interceptor: ((Intent)-> Unit)? = null
) {
    val intent = Intent(this, T::class.java)
    flags?.let { intent.flags = it }
    data?.let { intent.data = it }
    categories?.let { intent.addCategory(it)}
    bundle?.let { intent.putExtras(it) }
    interceptor?.invoke(intent)
    startActivity(intent)
}

@JvmOverloads
fun Context.launchIntent(
        intent: Intent = Intent(),
        action: String? = null,
        flags: Int? = null,
        data: Uri? = null,
        categories: String? = null,
        bundle: Bundle? = null,
        intercept: (()-> Unit)? = null
) {
    flags?.let { intent.flags = it }
    action?.let { intent.action = it }
    data?.let { intent.data = it }
    categories?.let { intent.addCategory(it)}
    bundle?.let { intent.putExtras(it) }
    intercept?.invoke()
    startActivity(intent)
}

