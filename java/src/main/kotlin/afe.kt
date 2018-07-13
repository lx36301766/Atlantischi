/**
 * Created on 13/07/2018.
 *
 * @author lx
 */
object afe {

    @JvmStatic
    fun main(args: Array<String>) {
        for (i in args.indices) {
            println(i)
            for (j in 1 until args.size) {
                println(j)
            }
        }
    }

}
