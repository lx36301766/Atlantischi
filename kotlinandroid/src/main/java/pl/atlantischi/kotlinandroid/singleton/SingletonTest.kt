package pl.atlantischi.kotlinandroid.singleton

import android.app.Activity

/**
 * Created on 21/08/2018.

 * @author lx
 */

fun main(args: Array<String>) {

    Singleton1.foo()
    Singleton2.instance.foo()
    Singleton3.instance.foo()
    Singleton4.instance.foo()
    Singleton5.instance.foo()

    SingletonWithContext.getInstance(Activity()).foo()

}
