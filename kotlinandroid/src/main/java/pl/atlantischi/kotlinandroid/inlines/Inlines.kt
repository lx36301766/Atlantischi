package pl.atlantischi.kotlinandroid.inlines

/**
 * Created on 29/08/2018.

 * @author lx
 */


fun foo_normal(callback: () -> Unit) {

}

inline fun foo_inline(callback: () -> Int) {

}

//noinlined 参数不进行内联，保留一般函数的特征，且不允许非局部返回
inline fun foo_noinline(inlined: () -> Unit, noinline noinlined: () -> Int) {

}

//crossinline 参数内联, 但不允许非局部返回
inline fun foo_crossinline(inlined: () -> Unit, crossinline crossinline: () -> Int) {

}

fun foo () {

    foo_normal {
        //不支持直接返回 foo，只能局部返回
//        return
        return@foo_normal
    }

    foo_inline {
        if (true) {
            //返回 foo
            return
        }
        //返回 foo_inline
        return@foo_inline 111
    }

    foo_noinline( {} ) {
        if (true) {
            //error
            //return
        }
        //返回 foo_inline
        return@foo_noinline 111
    }

    foo_crossinline({}) {
        if (true) {
//            error
//            return
        }
//        返回 foo_inline
        return@foo_crossinline 12
    }
}