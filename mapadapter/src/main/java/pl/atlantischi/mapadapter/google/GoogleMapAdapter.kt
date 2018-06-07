package pl.atlantischi.mapadapter.google

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.ViewStub
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import pl.atlantischi.mapadapter.IMapAdapter
import pl.atlantischi.mapadapter.R

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */


class GoogleMapAdapter: IMapAdapter, OnMapReadyCallback  {

    private lateinit var mapView: MapView

    private lateinit var googleMap: GoogleMap

    private var mapViewLifecycleDelegate: GoogleMapViewLifecycleDelegate

    constructor(activity: Activity) {
        mapViewLifecycleDelegate = GoogleMapViewLifecycleDelegate(activity, mapViewFoundCallback)
    }

    constructor(fragment: Fragment) {
        mapViewLifecycleDelegate = GoogleMapViewLifecycleDelegate(fragment, mapViewFoundCallback)
    }

    private val mapViewFoundCallback: (MapView) -> Unit = {
        mapView = it
        mapView.getMapAsync(this@GoogleMapAdapter)
    }

    override fun onMapReady(map: GoogleMap) {
//        val cd = LatLng(30.545162, 104.061024)
//        map.addMarker(MarkerOptions().position(cd).title("Marker in CD"))
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cd, 12f))
        googleMap = map
    }

    override fun setMapViewStub(viewStub: ViewStub) {
        viewStub.layoutResource = R.layout.view_google_map
        viewStub.inflate()
    }

}
