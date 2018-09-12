package pl.atlantischi.ktutil.extension

import android.support.v4.app.Fragment
import android.view.View

/**
 * Created on 30/08/2018.

 * @author lx
 */

fun <V : View> Fragment.bindView(id: Int) = lazy<V?> { view?.findViewById(id) }
