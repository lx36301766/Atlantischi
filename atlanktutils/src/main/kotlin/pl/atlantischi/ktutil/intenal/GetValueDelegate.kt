package pl.atlantischi.ktutil.intenal

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created on 30/08/2018.

 * @author lx
 */

class GetValueDelegate<in R, out T>(val getValue: (R)-> T) : ReadOnlyProperty<R, T> {

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        return getValue(thisRef)
    }

}
