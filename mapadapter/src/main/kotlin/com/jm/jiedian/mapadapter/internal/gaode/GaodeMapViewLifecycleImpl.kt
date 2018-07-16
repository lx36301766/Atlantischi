package com.jm.jiedian.mapadapter.internal.gaode

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.amap.api.maps.TextureMapView
import com.jm.jiedian.mapadapter.MapViewLifecycleImpl

/**
 * Created on 07/06/2018.

 * @author lx
 */
internal class GaodeMapViewLifecycleImpl(f: Fragment? = null, a: Activity? = null) : MapViewLifecycleImpl(f, a) {

    lateinit var mapView: TextureMapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}