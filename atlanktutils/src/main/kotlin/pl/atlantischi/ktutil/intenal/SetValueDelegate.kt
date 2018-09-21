package pl.atlantischi.ktutil.intenal

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created on 30/08/2018.

 * @author lx
 */

class SetValueDelegate<in R, T>(private val delegate: (R)-> T) : ReadWriteProperty<R, T> {

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
    }

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        return delegate(thisRef)
    }

}
