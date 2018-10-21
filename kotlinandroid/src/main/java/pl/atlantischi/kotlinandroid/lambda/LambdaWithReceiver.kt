package pl.atlantischi.kotlinandroid.lambda

/**
 * Created on 27/08/2018.

 * @author lx
 *
 *  带参数的lambda使用
 *
 */


fun buildString(builderAction:(StringBuilder) -> Unit):String {
    val sb = StringBuilder()
    builderAction(sb)
    return sb.toString()
}

fun buildStringWithLambdaReceiver(builderAction: StringBuilder.() -> Unit):String {
    val sb = StringBuilder()
    builderAction(sb)
    return sb.toString()
}

fun main(args: Array<String>) {

    val s1 = buildString {
        it.append("abc")
        it.append("123")
    }

    val s2 = buildStringWithLambdaReceiver {
        append("abc")
        append("123")
    }

}
