

fun <T> ensure(seq: T) where T: CharSequence, T: Appendable {

    var c: Collection<*>

    print(isA<String>("aba"))
    print(isA<String>(123))
}

inline fun <reified T> isA(value: Any) = value is T

