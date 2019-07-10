package com.jm.jiedian.mapadapter

import android.app.Activity
import android.support.v4.app.Fragment
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeMapObjectFactory
import java.lang.Exception
import java.lang.reflect.Constructor

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

class MapController private constructor() {

    companion object {

        val ADAPTER_NAMES = arrayOf(
                "com.jm.googlemap.GoogleMapAdapter",
                "com.jm.gaodemap.GaodeMapAdapter"
        )

        @JvmStatic
        val instance = SingletonHolder.holder

        @JvmStatic
        fun initialize(fragment: Fragment) {
            _initialize(fragment, null)
        }

        @JvmStatic
        fun initialize(activity: Activity) {
            _initialize(null, activity)
        }

        private fun _initialize(fragment: Fragment? = null, activity: Activity? =null) {
//            when (mapType) {
////                MAP_TYPE_GOOGLE -> instance.mapAdapter = GoogleMapAdapter(fragment, activity)
////                MAP_TYPE_GAODE -> instance.mapAdapter = GaodeMapAdapter(fragment, activity)
//                else -> throw IllegalArgumentException("error mapType : $mapType")
//            }
            var clazz: Class<*>? = null
            for (name in ADAPTER_NAMES) {
                try {
                    clazz = Class.forName(name)
                    break
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (clazz == null) {
                throw IllegalStateException("Can't find map adapter class")
            }
            val params: Array<Class<out Any>> = arrayOf<Class<*>>(Fragment::class.java, Activity::class.java)
            val constructor: Constructor<*> = clazz.getConstructor(*params)
            instance.mapAdapter = constructor.newInstance(fragment, activity) as MapAdapter
        }

    }

    private lateinit var mapAdapter: MapAdapter

    private object SingletonHolder {
        val holder = MapController()
    }

    private fun ensureAdapterInitialized(): MapAdapter {
        if (!::mapAdapter.isInitialized) {
            throw IllegalStateException("please call MapController.initialize first")
        }
        return mapAdapter
    }

    val defaultAdapter by lazy { ensureAdapterInitialized() }

    val mapObjectFactory
        get() = defaultAdapter.mapObjectFactory

    val gaodeMapObjectFactory: IGaodeMapObjectFactory?
        get() = mapObjectFactory as? IGaodeMapObjectFactory

}
