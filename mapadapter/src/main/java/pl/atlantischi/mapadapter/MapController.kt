package pl.atlantischi.mapadapter

import android.annotation.SuppressLint
import android.app.Activity
import android.support.annotation.IntDef
import android.support.v4.app.Fragment
import pl.atlantischi.mapadapter.gaode.GaodeMapAdapter
import pl.atlantischi.mapadapter.google.GoogleMapAdapter

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

class MapController private constructor() {

    @IntDef(ADAPTER_TYPE_GOOGLE.toLong(), ADAPTER_TYPE_GAODE.toLong())
    private annotation class AdapterType

    companion object {

        val instance = SingletonHolder.holder

        const val ADAPTER_TYPE_GOOGLE = 1
        const val ADAPTER_TYPE_GAODE = 2

        fun initialize(@AdapterType adapterType: Int, activity: Activity) {
            initializeInner(adapterType)
            instance.iMapAdapter.initialize(activity)
        }

        fun initialize(@AdapterType adapterType: Int, fragment: Fragment) {
            initializeInner(adapterType)
            instance.iMapAdapter.initialize(fragment)
        }

        private fun initializeInner(@AdapterType adapterType: Int) {
            @SuppressLint("SwitchIntDef")
            when(adapterType) {
                ADAPTER_TYPE_GOOGLE -> instance.iMapAdapter = GoogleMapAdapter()
                ADAPTER_TYPE_GAODE -> instance.iMapAdapter = GaodeMapAdapter()
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
