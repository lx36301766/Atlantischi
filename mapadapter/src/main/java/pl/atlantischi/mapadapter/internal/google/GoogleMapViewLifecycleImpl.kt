package pl.atlantischi.mapadapter.internal.google

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.google.android.gms.maps.MapView
import pl.atlantischi.mapadapter.MapViewLifecycleImpl
import pl.atlantischi.mapadapter.R

/**
 * Created on 07/06/2018.

 * @author lx
 */
internal class GoogleMapViewLifecycleImpl : MapViewLifecycleImpl {

    private var onMapViewFoundCallback: (MapView) -> Unit

    private lateinit var mapView: MapView

    constructor(a: Activity, onMapViewFound: (MapView) -> Unit) : super(a) {
        onMapViewFoundCallback = onMapViewFound
    }

    constructor(f: Fragment, onMapViewFound: (MapView) -> Unit) : super(f) {
        onMapViewFoundCallback = onMapViewFound
    }

    override fun onActivityCreated(a: Activity, savedInstanceState: Bundle?) {
        mapView = a.findViewById(R.id.google_map_view) as MapView
        onMapViewFoundCallback.invoke(mapView)
        super.onActivityCreated(a, savedInstanceState)
    }

    override fun onFragmentViewCreated(fm: FragmentManager?, f: Fragment?, v: View?, savedInstanceState: Bundle?) {
        mapView = v?.findViewById(R.id.google_map_view) as MapView
        onMapViewFoundCallback.invoke(mapView)
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
    }

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