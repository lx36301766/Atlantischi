package pl.atlantischi.kotlinandroid.operators

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created on 29/08/2018.

 * @author lx
 */

/********************* 一元运算符 *********************/

//前缀操作符
operator fun Operators.unaryPlus() = Operators(x) // +a
operator fun Operators.unaryMinus() = Operators(-x) // -a
operator fun Operators.not() = Operators(-x) // !a

//递增与递减
operator fun Operators.inc() = Operators(x + 1) // a++ ++a
operator fun Operators.dec() = Operators(x - 1) // a-- --a


/********************* 二元运算符 *********************/

//算术运算符
operator fun Operators.plus(other: Operators) = Operators(x + other.x) // a + b
operator fun Operators.minus(other: Operators) = Operators(x - other.x) // a - b
operator fun Operators.times(other: Operators) = Operators(x * other.x) // a * b
operator fun Operators.div(other: Operators) = Operators(x / other.x) // a / b
operator fun Operators.rem(other: Operators) = Operators(x % other.x) // a % b
operator fun Operators.rangeTo(other: Operators) = listOf(this, other)// a..b

//赋值算术运算符
//PS：当同一类型的和上面的算术运算符同时定义时，调用处会报错, 例如 += 会匹配到 plus 和 plusAssign 两个函数
operator fun Operators.plusAssign(other: Operators) { x + other.x } // a += b
operator fun Operators.minusAssign(other: Operators) { x - other.x } // a -= b
operator fun Operators.timesAssign(other: Operators) { x * other.x } // a *= b
operator fun Operators.divAssign(other: Operators) { x / other.x } // a /= b
operator fun Operators.remAssign(other: Operators) { x % other.x } // a %= b

//比较操作符
operator fun Operators.compareTo(other: Operators) = x.compareTo(other.x) // a > b , a < b , a >= b , a <= b

//in操作符
operator fun Operators.contains(other: Operators) = false// a in b , a !in b

//相等与不等操作符 (不能用扩展函数方式定义，必须定义在类内部)
//override operator fun equals(other: Any?) = false //a == b , a != b

//索引访问操作符
operator fun Operators.get(i: Int) = list[i] // a[i]
operator fun Operators.get(i: Int, j: Int) = arrayOf(list[i], list[j]) // a[i, j]
operator fun Operators.set(i: Int, b: String) { list[i] = b } // a[i] = b
operator fun Operators.set(i: Int, j: Int, b: String) { // a[i, j] = b
    list[i] = b
    list[j] = b
}


/********************* 特殊运算符 *********************/

//调用操作符
operator fun Operators.invoke() = println() // a()
operator fun Operators.invoke(i: Int) = println(i) // a(i)
operator fun Operators.invoke(i: Int, j: Int) = println(i + j) // a(i, j)

//TODO
operator fun Operators.hasNext() = false
operator fun Operators.next() = null
operator fun Operators.iterator() = {}

//解构申明运算符
operator fun Operators.component1() = x //val (x, y, z) = Operators(10)
operator fun Operators.component2() = y
operator fun Operators.component3() = z

//属性委托操作符
operator fun Operators.getValue(thisRef: Any?, property: KProperty<*>): String {
    return "$thisRef, thank you for delegating '${property.name}' to me!"
}
operator fun Operators.setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    println("$value has been assigned to '${property.name}' in $thisRef.")
}
//operator fun Operators.provideDelegate(
//        thisRef: MyUI,
//        prop: KProperty<*>
//): ReadOnlyProperty<MyUI, T> {
////    checkProperty(thisRef, prop.name)
//    // 创建委托
////    return ResourceDelegate()
//}





/********************* 测试类 *********************/

class Operators(
        var x:Int
) {

    val y : Int = 55
    val z : Int = 5

    //相等与不等操作符
    override operator fun equals(other: Any?) = false


    override fun hashCode(): Int = x.hashCode()

    var list = mutableListOf("aa", "bb", "cc")
    var maps = mutableMapOf<Int, Any?>(1 to "s", 2 to 3)
}

fun main(args: Array<String>) {

    var o1 = Operators(5)
    val o2 = Operators(3)

    val a1 = +o1
    val a2 = -o1
    val a3 = !o1

    val a4 = ++o1 + o1++
    val a5 = o1-- - --o1

    val b1 = o1 + o2
    val b2 = o1 - o2
    val b3 = o1 * o2
    val b4 = o1 / o2
    val b5 = o1 % o2
    val b6 = o1..o2
    for (i in b6) {
    }

    //compile error
//    o1 += o2

    val c1 = o1 in o2
    val c2 = o1 !in o2

    val d1 = o1[1]
    val d2 = o1[1, 2]
    o1[1] = "dd"
    o1[1, 2] = "dd"

    o1()
    o1(2)
    o1(2, 3)

    val e1 = o1 == o2
    val e2 = o1 != o2
    val e3 = o1 > o2
    val e4 = o1 < o2
    val e5 = o1 >= o2
    val e6 = o1 <= o2

    val (f1, f2, f3) = Operators(10)
}
