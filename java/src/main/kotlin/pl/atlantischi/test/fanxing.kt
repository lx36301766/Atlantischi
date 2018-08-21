package pl.atlantischi.test

import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.CoroutineContext

fun <T> ensure(seq: T) where T: CharSequence, T: Appendable {

    var c: Collection<*>

    print(isA<String>("aba"))
    print(isA<String>(123))
}

inline fun <reified T> isA(value: Any) = value is T

class FilePath(val path: String) : AbstractCoroutineContextElement(FilePath) {

    companion object : CoroutineContext.Key<FilePath>

}

public class FilePath2(val path: String) : AbstractCoroutineContextElement(FilePath) {

    companion object Foo: Runnable {

        @JvmStatic
        override fun run() {
        }

    }

}
