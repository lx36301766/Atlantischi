package pl.atlantischi.kotlinandroid.delegate

import kotlin.properties.Delegates

/**
 * Created on 29/08/2018.

 * @author lx
 */

class User(val map: MutableMap<String, Any?>) {

    //标准委托
    val lazyValue: String by lazy {
        println("computed!")
        "Hello"
    }

    //可观察属性 Observable
    var nameObservable: String by Delegates.observable("default_name") { _, old, new ->
        println("$old -> $new")
    }

    //属性储存在映射中
    val name: String by map
    val age: Int     by map

    //属性委托给类
    var email: String by ReadWriteDelegate()

}

//局部委托属性
//memoizedFoo 变量只会在第一次访问时计算。 如果 someCondition 失败，那么该变量根本不会计算。
fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)
    if (memoizedFoo.someCondition() && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}

class Foo{
    fun isValid() : Boolean = false
    fun doSomething() : Unit {}
    fun someCondition() : Boolean = false
}

fun main(args: Array<String>) {
    val user = User(mutableMapOf(
            "name" to "John Doe",
            "age" to 25,
            "email" to "xxx@163.com"
    ))
    println("name=${user.name}, age=${user.age}")

    user.nameObservable = "kkk"


}
