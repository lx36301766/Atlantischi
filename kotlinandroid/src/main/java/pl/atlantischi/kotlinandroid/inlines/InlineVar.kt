package pl.atlantischi.kotlinandroid.inlines

import java.util.*

/**
 * Created on 29/08/2018.

 * @author lx
 */

class Foo {}

inline var foo: Foo
    get() = Foo()
    set(v) {}

val foo1: Foo
    inline get() = Foo()

var foo2: Foo
    get() = Foo()
    inline set(value) {
    }

var Any.foo : Long
    get() = Date().time
    set(value) {
        //Cannot access field here since extension property cannot have backing field
        //Do something with `obj`
    }