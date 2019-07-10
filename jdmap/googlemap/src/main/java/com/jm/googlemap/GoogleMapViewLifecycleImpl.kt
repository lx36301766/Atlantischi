package com.jm.googlemap

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.android.gms.maps.MapView
import com.jm.jiedian.mapadapter.MapViewLifecycleImpl

/**
 * Created on 07/06/2018.

 * @author lx
 */

internal class GoogleMapViewLifecycleImpl(f: Fragment? = null, a: Activity? = null) : MapViewLifecycleImpl(f, a) {

    lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
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