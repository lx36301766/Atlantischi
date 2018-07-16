package com.jm.jiedian.mapadapter

import android.app.Activity
import android.support.annotation.IntDef
import android.support.v4.app.Fragment
import com.jm.jiedian.mapadapter.internal.gaode.GaodeMapAdapter
import com.jm.jiedian.mapadapter.internal.google.GoogleMapAdapter
import com.jm.jiedian.mapadapter.mapapi.IBitmapDescriptorFactory
import com.jm.jiedian.mapadapter.mapapi.ICameraUpdateFactory
import com.jm.jiedian.mapadapter.mapapi.IUISettings
import com.jm.jiedian.mapadapter.separate.gaode.IGaodeMapObjectFactory

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

class MapController private constructor() {

    @IntDef(ADAPTER_TYPE_GAODE, ADAPTER_TYPE_GOOGLE)
    private annotation class AdapterType

    companion object {

        const val ADAPTER_TYPE_GOOGLE: Long = 1
        const val ADAPTER_TYPE_GAODE: Long = 2

        @JvmStatic
        val instance = SingletonHolder.holder

        @JvmStatic
        fun initialize(@AdapterType adapterType: Long, fragment: Fragment? = null, activity: Activity? =null) {
            when (adapterType) {
                ADAPTER_TYPE_GOOGLE -> instance.mapAdapter = GoogleMapAdapter(fragment, activity)
                ADAPTER_TYPE_GAODE -> instance.mapAdapter = GaodeMapAdapter(fragment, activity)
                else -> throw IllegalArgumentException("error adapterType : $adapterType")
            }
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

    val uiSettings: IUISettings
        get() = mapObjectFactory.uiSettings

    val bitmapDescriptorFactory: IBitmapDescriptorFactory
        get() = mapObjectFactory.bitmapDescriptorFactory

    val cameraUpdateFactory: ICameraUpdateFactory
        get() = mapObjectFactory.cameraUpdateFactory

    val gaodeMapObjectFactory: IGaodeMapObjectFactory?
        get() = mapObjectFactory as? IGaodeMapObjectFactory

}
