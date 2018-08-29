package pl.atlantischi.ktutil

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import java.util.*

/**
 * Created on 28/08/2018.

 * @author lx
 */

@JvmOverloads
inline fun <reified T : Activity> Activity.launchActivityForResult(
        flags: Int? = null,
        data: Uri? = null,
        categories: String? = null,
        bundle: Bundle? = null,
        noinline interceptor: ((Intent)-> Unit)? = null,
        noinline d: ((Intent)-> Unit)? = null
) {
    val intent = Intent(this, T::class.java)
    flags?.let { intent.flags = it }
    data?.let { intent.data = it }
    categories?.let { intent.addCategory(it)}
    bundle?.let { intent.putExtras(it) }
    interceptor?.invoke(intent)
    startActivityForResult(intent, UUID.randomUUID().version())

}

fun Activity.activityResult(requestCode: Int, resultCode: Int, data: Intent) {

}
