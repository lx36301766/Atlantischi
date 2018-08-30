package pl.atlantischi.kotlinandroid.delegate

import android.app.Activity
import android.view.View
import android.widget.ImageView
import pl.atlantischi.kotlinandroid.R
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created on 29/08/2018.

 * @author lx
 */

class ReadDelegate : ReadOnlyProperty<User, String> {

    override operator fun getValue(thisRef: User, property: KProperty<*>): String {
        return thisRef.map[property.name] as? String ?: "null"
    }

}

class ReadWriteDelegate : ReadWriteProperty<User, String> {

    override operator fun getValue(thisRef: User, property: KProperty<*>): String {
        return thisRef.map[property.name] as? String ?: "null"
    }

    override operator fun setValue(thisRef: User, property: KProperty<*>, value: String) {
        thisRef.map[property.name] = value
    }

}
