package pl.atlantischi.ktutil.extension

import android.app.Dialog
import android.view.View

/**
 * Created on 30/08/2018.

 * @author lx
 */

fun <V : View> Dialog.bindView(id: Int) = lazy<V?> { findViewById(id) }
