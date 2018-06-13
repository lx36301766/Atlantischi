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
            ADAPTER_TYPE_GAODE.toLong(),
            ADAPTER_TYPE_BAIDU.toLong(),
            ADAPTER_TYPE_TENCENT.toLong(),
            ADAPTER_TYPE_MAPBOX.toLong()
    )
    private annotation class AdapterType

    @SuppressLint("SwitchIntDef")
    companion object {

        val instance = SingletonHolder.holder

        const val ADAPTER_TYPE_GOOGLE = 1
        const val ADAPTER_TYPE_GAODE = 2
        const val ADAPTER_TYPE_BAIDU = 3
        const val ADAPTER_TYPE_TENCENT = 4
        const val ADAPTER_TYPE_MAPBOX = 5

        fun initialize(@AdapterType adapterType: Int, activity: Activity) {
            when(adapterType) {
                ADAPTER_TYPE_GOOGLE -> instance.iMapAdapter = GoogleMapAdapter(activity)
                ADAPTER_TYPE_GAODE -> instance.iMapAdapter = GaodeMapAdapter(activity)
                else -> throw IllegalArgumentException("error adapterType : $adapterType")
            }
        }

        fun initialize(@AdapterType adapterType: Int, fragment: Fragment) {
            when(adapterType) {
                ADAPTER_TYPE_GOOGLE -> instance.iMapAdapter = GoogleMapAdapter(fragment)
                ADAPTER_TYPE_GAODE -> instance.iMapAdapter = GaodeMapAdapter(fragment)
                else -> throw IllegalArgumentException("error adapterType : $adapterType")
            }
        }

    }

    private lateinit var iMapAdapter: IMapAdapter

    private object SingletonHolder {
        val holder = MapController()
    }

    val defaultAdapter by lazy {
        ensureInitialized()
        iMapAdapter
    }

    private fun ensureInitialized() {
        if (!::iMapAdapter.isInitialized) {
            throw IllegalStateException("please call MapController.initialize first")
        }
    }

}
