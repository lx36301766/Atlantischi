package pl.atlantischi.mapadapter

import android.app.Activity
import android.support.v4.app.Fragment

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

    fun initialize(activity: Activity) {
        iMap = GoogleMapAdapter()
        iMap.initialize(activity)
    }

    fun initialize(fragment: Fragment) {
        iMap = GoogleMapAdapter()
        iMap.initialize(fragment)
    }

}
