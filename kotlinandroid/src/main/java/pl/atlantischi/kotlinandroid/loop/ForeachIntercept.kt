package pl.atlantischi.kotlinandroid.loop

/**
 * Created on 27/08/2018.

 * @author lx
 */

internal class ForeachIntercept {

    private val mStringCache = mutableListOf<String>()

    fun foo() {

        //like java break
        run breaker@{
            mStringCache.forEach {
                if (it.length == 5) {
                    return@breaker
                }
            }
        }

        //like java continue
        mStringCache.forEach {
            if (it.length == 5) {
                return@forEach
            }
        }
        mStringCache.forEach continuer@{
            if (it.length == 5) {
                return@continuer
            }
        }
        mStringCache.forEach (fun(method: String) {
            if (method.length == 5) {
                return
            }
        })
    }

}
