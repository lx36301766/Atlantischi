package pl.atlantischi.kotlinandroid.delegate

import android.app.Activity
import android.view.View
import android.widget.ImageView
import pl.atlantischi.kotlinandroid.R
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created on 30/08/2018.

 * @author lx
 */

class MyActivity: Activity() {

    val image by lazy { findViewById<ImageView>(R.id.action_bar)}

    val image1 by findView<ImageView>(R.id.action_bar)

    val image2 by bindView<ImageView>(R.id.action_bar)

}

fun <V : View> Activity.findView(id: Int): ReadOnlyProperty<Activity, V> {
    return object : ReadOnlyProperty<Activity, V> {
        override fun getValue(thisRef: Activity, property: KProperty<*>): V {
            return thisRef.findViewById(id)
        }
    }
}


class ResourceDelegate<out V: View>(val id: Int) : ReadOnlyProperty<Activity, V> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): V {
        return thisRef.findViewById(id)
    }
}

class ResourceLoader<out V: View>(val id: Int) {
    operator fun provideDelegate(thisRef: Activity, prop: KProperty<*>): ReadOnlyProperty<Activity, V> {
        return ResourceDelegate(id)
    }
}

private fun <V : View> Activity.bindView(id: Int): ResourceLoader<V> {
    return ResourceLoader(id)
}
