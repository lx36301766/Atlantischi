package pl.atlantischi.mapadapter.internal.gaode

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.amap.api.maps.TextureMapView
import pl.atlantischi.mapadapter.MapViewLifecycleImpl
import pl.atlantischi.mapadapter.R

/**
 * Created on 07/06/2018.

 * @author lx
 */
internal class GaodeMapViewLifecycleImpl : MapViewLifecycleImpl {

    private var onMapViewFoundCallback: (TextureMapView) -> Unit

    private lateinit var mapView: TextureMapView

    constructor(a: Activity, onMapViewFound: (TextureMapView) -> Unit) : super(a) {
        onMapViewFoundCallback = onMapViewFound
    }

    constructor(f: Fragment, onMapViewFound: (TextureMapView) -> Unit) : super(f) {
        onMapViewFoundCallback = onMapViewFound
    }

    override fun onActivityCreated(a: Activity, savedInstanceState: Bundle?) {
        mapView = a.findViewById(R.id.gaode_map_view) as TextureMapView
        onMapViewFoundCallback.invoke(mapView)
        super.onActivityCreated(a, savedInstanceState)
    }

    override fun onFragmentViewCreated(fm: FragmentManager?, f: Fragment?, v: View?, savedInstanceState: Bundle?) {
        mapView = v?.findViewById(R.id.gaode_map_view) as TextureMapView
        onMapViewFoundCallback.invoke(mapView)
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
    }

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