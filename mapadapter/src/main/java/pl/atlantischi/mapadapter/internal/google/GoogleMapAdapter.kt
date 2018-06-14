package pl.atlantischi.mapadapter.internal.google

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.ViewStub
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLngBounds
import pl.atlantischi.mapadapter.IMapAdapter
import pl.atlantischi.mapadapter.R
import pl.atlantischi.mapadapter.params.MarkerOptionsParameters
import pl.atlantischi.mapadapter.callback.*
import pl.atlantischi.mapadapter.internal.google.delegate.*

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

internal class GoogleMapAdapter: IMapAdapter, OnMapReadyCallback  {

    private lateinit var mapView: MapView

    private lateinit var googleMap: GoogleMap

    private var mapViewLifecycleDelegateImpl: GoogleMapViewLifecycleImpl

    private lateinit var uiSetting: IUISettings

    private lateinit var bitmapDescriptorFactory: IBitmapDescriptorFactory

    constructor(activity: Activity) {
        mapViewLifecycleDelegateImpl = GoogleMapViewLifecycleImpl(activity, mapViewFoundCallback)
    }

    constructor(fragment: Fragment) {
        mapViewLifecycleDelegateImpl = GoogleMapViewLifecycleImpl(fragment, mapViewFoundCallback)
    }

    private val mapViewFoundCallback: (MapView) -> Unit = {
        mapView = it
        mapView.getMapAsync(this@GoogleMapAdapter)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        uiSetting = GoogleUISetting(googleMap.uiSettings)
        bitmapDescriptorFactory = GoogleBitmapDescriptorFactory()
    }

    override fun setMapViewStub(viewStub: ViewStub) {
        viewStub.layoutResource = R.layout.view_google_map
        viewStub.inflate()
    }

    override fun getUISetting(): IUISettings {
        return uiSetting
    }

    override fun getBitmapDescriptorFactory(): IBitmapDescriptorFactory {
        return bitmapDescriptorFactory
    }

    override fun addMarker(markerOptionsParameters: MarkerOptionsParameters): IMarker {
        return GoogleMarker(googleMap.addMarker(GoogleMarkerOptions.build(markerOptionsParameters)))
    }

    override fun setOnMarkerClickListener(onMarkerClickListener: (marker: IMarker) -> Boolean) {
        googleMap.setOnMarkerClickListener {
            onMarkerClickListener.invoke(GoogleMarker(it))
        }
    }

    override fun newLatLngBoundBuiler(): ILatLngBounds.Builder {
        return GoogleLatLngBounds.Builder(LatLngBounds.Builder())
    }

    override fun setOnMapClickListener(onMapClickListener: (latlng: ILatLng) -> Unit) {
        googleMap.setOnMapClickListener {
            onMapClickListener.invoke(GoogleLatLng(it))
        }
    }

    override fun setOnMapLongClickListener(onMapLongClickListener: (latlng: ILatLng) -> Unit) {
        googleMap.setOnMapLongClickListener {
            onMapLongClickListener.invoke(GoogleLatLng(it))
        }
    }

    override fun setOnCameraChangeListener(onCameraChangeListener: (cameraPosition: ICameraPosition, isFinished: Boolean) -> Unit) {
        with(googleMap) {
            val callback : (isFinished: Boolean) -> Unit = {
                onCameraChangeListener.invoke(GoogleCameraPosition(cameraPosition), it)
            }
            setOnCameraMoveStartedListener { callback.invoke(false) }
            setOnCameraMoveListener { callback.invoke(false) }
            setOnCameraIdleListener { callback.invoke(true) }
            setOnCameraMoveCanceledListener { callback.invoke(true) }
        }
    }

}
