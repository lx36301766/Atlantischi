package pl.atlantischi.kotlinandroid.delegate

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created on 29/08/2018.

 * @author lx
 */

class ReadDelegate : ReadOnlyProperty<Any?, String> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

}

class ReadWriteDelegate : ReadWriteProperty<User, String> {

    override operator fun getValue(thisRef: User, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    override operator fun setValue(thisRef: User, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }

}
