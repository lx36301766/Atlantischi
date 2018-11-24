package pl.atlantischi.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created on 2018/11/8.
 *
 * @author lx
 */

class LifecycleJava8Demo : DefaultLifecycleObserver {

    companion object {
        private val TAG = "LifecycleJava8Demo"
    }

    override fun onCreate(owner: LifecycleOwner) {
        Log.d(TAG, "LifecycleJava8Demo,  onCreate")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d(TAG, "LifecycleJava8Demo,  onDestroy")
    }

}
