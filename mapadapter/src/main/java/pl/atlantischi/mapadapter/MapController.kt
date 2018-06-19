package pl.atlantischi.mapadapter

import android.annotation.SuppressLint
import android.app.Activity
import android.support.annotation.IntDef
import android.support.v4.app.Fragment
import pl.atlantischi.mapadapter.internal.gaode.GaodeMapAdapter
import pl.atlantischi.mapadapter.internal.google.GoogleMapAdapter

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

class MapController private constructor() {

    @IntDef(
            ADAPTER_TYPE_GOOGLE.toLong(),
            ADAPTER_TYPE_GAODE.toLong()
    )
    private annotation class AdapterType

    @SuppressLint("SwitchIntDef")
    companion object {

        val instance = SingletonHolder.holder

        const val ADAPTER_TYPE_GOOGLE = 1
        const val ADAPTER_TYPE_GAODE = 2
//        const val ADAPTER_TYPE_BAIDU = 3
//        const val ADAPTER_TYPE_TENCENT = 4

        fun initialize(@AdapterType adapterType: Int, activity: Activity) {
            when(adapterType) {
                ADAPTER_TYPE_GOOGLE -> instance.mapAdapter = GoogleMapAdapter(activity)
                ADAPTER_TYPE_GAODE -> instance.mapAdapter = GaodeMapAdapter(activity)
                else -> throw IllegalArgumentException("error adapterType : $adapterType")
            }
        }

        fun initialize(@AdapterType adapterType: Int, fragment: Fragment) {
            when(adapterType) {
                ADAPTER_TYPE_GOOGLE -> instance.mapAdapter = GoogleMapAdapter(fragment)
                ADAPTER_TYPE_GAODE -> instance.mapAdapter = GaodeMapAdapter(fragment)
                else -> throw IllegalArgumentException("error adapterType : $adapterType")
            }
        }

    }

    private lateinit var mapAdapter: MapAdapter

    private object SingletonHolder {
        val holder = MapController()
    }

    val defaultAdapter by lazy {
        ensureInitialized()
        mapAdapter
    }

    private fun ensureInitialized() {
        if (!::mapAdapter.isInitialized) {
            throw IllegalStateException("please call MapController.initialize first")
        }
    }

}

inline fun <T : Any, R> T?.notNull(block: (T) -> R): R? {
    return if (this == null) null else block(this)
}

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}
