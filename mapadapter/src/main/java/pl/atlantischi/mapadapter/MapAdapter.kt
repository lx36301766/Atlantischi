package pl.atlantischi.mapadapter

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

class MapAdapter private constructor() {

    lateinit var iMap: IMap

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = MapAdapter()
    }

    val defaultAdapter
        get() = iMap

    fun init() {
        iMap.initialize()
    }

}
