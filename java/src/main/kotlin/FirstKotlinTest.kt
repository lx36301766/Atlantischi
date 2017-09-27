import java.io.File

/**
 * Created on 04/07/2017.

 * @author lx
 */
fun main(args: Array<String>) {

    mainTest()
}

private fun mainTest() {
    println("max of 0 and 42 is ${maxOf(0, 42)}")

    val items = listOf("apple", "banana", "kiwi")
    for (item in items) {
        println(item)
    }

    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }
    println()
    val x = 10
    val y = 9
    if (x in 1..y + 1) {
        println("fits in range")
    }
    println()

    val list = listOf("a", "b", "c")

    println("list.lastIndex=${list.lastIndex}")
    println("list.indices=${list.indices}")
    println("list.size=${list.size}")
    println()

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range too")
    }

    println()
    for (x in 1..11 step 2) {
        print(x)
    }
    println()
    for (x in 9 downTo 0 step 3) {
        print(x)
    }

    println()
    println()
    when {
        "orange" in items -> println("juicy")
        "apple" in items -> println("apple is fine too")
    }
    println()

    val ff = listOf("banana", "avocado", "apple", "kiwi")
    ff
            .filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.toUpperCase() }
            .forEach { println(it) }

    println("sum=${sum(b = "23")}")

    val ii = listOf(1, 3, 7, 4)
    ii.filter { fff -> fff > 5 }

    println()
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    println("before-${map["a"]}")
    var a = map["a"]
    println("after-${map["a"]}")
    println()

    println("lazyValue=$lazyValue")

    val files = File("Test").listFiles()
    println(files?.size)
    println(files?.size ?: "empty")

    val data = Customer("lx", "163")
    //val email = data["email"] ?: throw IllegalStateException("Email is missing!")

    val myTurtle = get()
    if (myTurtle != null) {
        with(myTurtle) {
            //draw a 100 pix square
            penDown()
            for (i in 1..4) {
                forward(100.0)
                turn(90.0)
            }
            penUp()
        }
    }

    val num = 999_999
    var num2 = 666_666
    println("sum=${num + num2}")

    println()
    println()
    val asc = Array(5, { i -> (i * i).toString() })
    for (a in asc) {
        print("$a ")
    }



    println()
}

fun get(): Turtle? {
    return null
}


fun sum(a: Int = 5, b: String="15"): Int {
    return a + parseInt(b)
}

fun maxOf(a: Int, b: Int) = if (a > b) a else b

fun printProduct(arg1: String, arg2: String) {
    if (arg1 is String) {
        return
    }
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    // Using `x * y` yields error because they may hold nulls.
    if (x != null && y != null) {
        // x and y are automatically cast to non-nullable after null check
        println(x * y)
    } else {
        println("either '$arg1' or '$arg2' is not a number")
    }
}

fun parseInt(str: String): Int {
    // ...
    return Integer.parseInt(str)
}

val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

//singleton
object Resource {
    val name = "Name"
}

data class Customer(val name: String, val email: String)

fun transform(color: String): Int {
    return when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }
}

fun transform2(color: String): Int = when (color) {
    "Red" -> 0
    "Green" -> 1
    "Blue" -> 2
    else -> throw IllegalArgumentException("Invalid color param value")
}

fun test() {
    val result = try {
        sum()
    } catch (e: ArithmeticException) {
        throw IllegalStateException(e)
    }
    // Working with result
}

fun foo(param: Int): String{
    return if (param == 1) {
        "one"
    } else if (param == 2) {
        "two"
    } else {
        "three"
    }
}

fun arrayOfMinusOnes(size: Int): IntArray {
    return IntArray(size).apply { fill(-1) }
}

fun theAnswer() = 42
//equal
fun theAnswer2(): Int {
    return 42
}

abstract class Turtle {
    abstract fun penDown()
    abstract fun penUp()
    abstract fun turn(degrees: Double)
    abstract fun forward(pixels: Double)
}
