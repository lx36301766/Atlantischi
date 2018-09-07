package pl.atlantischi.ktutil.extension

import android.view.View
import pl.atlantischi.ktutil.intenal.GetValueDelegate

/**
 * Created on 30/08/2018.

 * @author lx
 */

fun <V : View> android.support.v4.app.Fragment.bindView(id: Int)
        = GetValueDelegate<android.support.v4.app.Fragment, V?> { view?.findViewById(id) }

fun <V : View> android.app.Fragment.bindView(id: Int)
        = GetValueDelegate<android.app.Fragment, V?> { view?.findViewById(id) }
