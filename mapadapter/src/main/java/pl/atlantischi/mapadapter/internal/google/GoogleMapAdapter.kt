package pl.atlantischi.mapadapter.internal.google

import android.app.Activity
import android.support.annotation.RequiresPermission
import android.support.v4.app.Fragment
import android.view.ViewStub
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import pl.atlantischi.mapadapter.MapAdapter
import pl.atlantischi.mapadapter.MapObjectFactory
import pl.atlantischi.mapadapter.R
import pl.atlantischi.mapadapter.callback.*
import pl.atlantischi.mapadapter.callback.graphics.*
import pl.atlantischi.mapadapter.internal.google.delegate.*
import pl.atlantischi.mapadapter.internal.google.delegate.graphics.*

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

internal class GoogleMapAdapter: MapAdapter, OnMapReadyCallback  {

    private lateinit var mapView: MapView

    private lateinit var googleMap: GoogleMap

    private var mapViewLifecycleDelegateImpl: GoogleMapViewLifecycleImpl

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
        mapObjectFactory = GoogleMapObjectFactory(googleMap)
    }

    override lateinit var mapObjectFactory: MapObjectFactory
        private set

    override fun setMapViewStub(viewStub: ViewStub) {
        viewStub.layoutResource = R.layout.view_google_map
        viewStub.inflate()
    }



    override val cameraPosition: ICameraPosition = GoogleCameraPosition(googleMap.cameraPosition)

    override val maxZoomLevel = googleMap.maxZoomLevel

    override val minZoomLevel = googleMap.minZoomLevel

    override fun moveCamera(cameraUpdate: ICameraUpdate) {
        val gau = cameraUpdate as GoogleCameraUpdate
        googleMap.moveCamera(gau.cameraUpdate)
    }

    override fun animateCamera(cameraUpdate: ICameraUpdate) {
        val gau = cameraUpdate as GoogleCameraUpdate
        googleMap.animateCamera(gau.cameraUpdate)
    }

    override fun animateCamera(cameraUpdate: ICameraUpdate, callback: MapAdapter.CancelableCallback) {
        val gau = cameraUpdate as GoogleCameraUpdate
        googleMap.animateCamera(gau.cameraUpdate, object: GoogleMap.CancelableCallback {
            override fun onFinish() {
                callback.onFinish()
            }

            override fun onCancel() {
                callback.onCancel()
            }
        })
    }

    override fun animateCamera(cameraUpdate: ICameraUpdate, duration: Long, callback: MapAdapter.CancelableCallback) {
        val gau = cameraUpdate as GoogleCameraUpdate
        googleMap.animateCamera(gau.cameraUpdate, duration.toInt(), object: GoogleMap.CancelableCallback {
            override fun onFinish() {
                callback.onFinish()
            }

            override fun onCancel() {
                callback.onCancel()
            }
        })
    }

    override fun stopAnimation() {
        googleMap.stopAnimation()
    }

    override fun addPolyline(polylineOptions: IPolyline.Options): IPolyline {
        val gpo = polylineOptions as GooglePolyline.Options
        return GooglePolyline(googleMap.addPolyline(gpo.options))
    }

    override fun addPolygon(polygonOptions: IPolygon.Options): IPolygon {
        val gpo = polygonOptions as GooglePolygon.Options
        return GooglePolygon(googleMap.addPolygon(gpo.options))
    }

    override fun addCircle(circleOptions: ICircle.Options): ICircle {
        val gco = circleOptions as GoogleCircle.Options
        return GoogleCircle(googleMap.addCircle(gco.options))
    }

    override fun addMarker(markerOptions: IMarker.Options): IMarker {
        val gmo = markerOptions as GoogleMarker.Options
        return GoogleMarker(googleMap.addMarker(gmo.options))
    }

    override fun addGroundOverlay(groundOverlayOptions: IGroundOverlay.Options): IGroundOverlay {
        val goo = groundOverlayOptions as GoogleGroundOverlay.Options
        return GoogleGroundOverlay(googleMap.addGroundOverlay(goo.options))
    }

    override fun addTileOverlay(tileOverlayOptions: ITileOverlay.Options): ITileOverlay {
        val gto = tileOverlayOptions as GoogleTileOverlay.Options
        return GoogleTileOverlay(googleMap.addTileOverlay(gto.options))
    }

    override fun clear() {
        googleMap.clear()
    }

    override var mapType = googleMap.mapType
        set(value) {
            googleMap.mapType = value
        }

    override var isTrafficEnabled = googleMap.isTrafficEnabled
        set(value) {
            googleMap.isTrafficEnabled = value
        }

    override var isIndoorEnabled = googleMap.isIndoorEnabled
        set(value) {
            googleMap.isIndoorEnabled = value
        }

    override var isBuildingsEnabled = googleMap.isBuildingsEnabled
        set(value) {
            googleMap.isBuildingsEnabled = value
        }

    override var isMyLocationEnabled = googleMap.isMyLocationEnabled
        set(value) {
            @RequiresPermission(
                    anyOf = arrayOf("android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION")
            )
            googleMap.isMyLocationEnabled = value
        }

    override fun setOnMarkerClickListener(onMarkerClickListener: (marker: IMarker) -> Boolean) {
        googleMap.setOnMarkerClickListener {
            onMarkerClickListener.invoke(GoogleMarker(it))
        }
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
