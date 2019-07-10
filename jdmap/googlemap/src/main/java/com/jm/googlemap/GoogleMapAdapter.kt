package com.jm.googlemap

import android.app.Activity
import android.location.Location
import android.support.annotation.RequiresPermission
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewStub
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.Marker
import com.jm.googlemap.common.GoogleCameraPosition
import com.jm.googlemap.common.GoogleCameraUpdate
import com.jm.googlemap.common.GoogleLatLng
import com.jm.googlemap.common.graphics.*
import com.jm.jiedian.mapadapter.MapAdapter
import com.jm.jiedian.mapadapter.R
import com.jm.jiedian.mapadapter.common.*
import com.jm.jiedian.mapadapter.common.graphics.*

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

internal class GoogleMapAdapter(f: Fragment? = null, a: Activity? = null): MapAdapter  {

//    private lateinit var mapView: MapView

    private lateinit var googleMap: GoogleMap

    private var mapViewLifecycleImpl: GoogleMapViewLifecycleImpl = GoogleMapViewLifecycleImpl(f, a)

    override fun initMapView(mapViewStub: ViewStub, onMapReadyCallback: () -> Unit) {
        mapViewStub.layoutResource = R.layout.view_google_map
        val mapView = mapViewStub.inflate() as MapView
        mapViewLifecycleImpl.mapView = mapView
        MapsInitializer.initialize(mapView.context.applicationContext)
        mapView.getMapAsync { map ->
            googleMap = map
            mapObjectFactory.googleMap = googleMap
            onMapReadyCallback.invoke()
        }
    }

    override var mapObjectFactory = GoogleMapObjectFactory()


    override val cameraPosition: ICameraPosition
        get() = GoogleCameraPosition(googleMap.cameraPosition)

    override var maxZoomLevel:Float
        get() = googleMap.maxZoomLevel
        set(value) {
            //not support
        }

    override var minZoomLevel:Float
        get() = googleMap.minZoomLevel
        set(value) {
            //not support
        }

    override val myLocation
        get() = googleMap.myLocation

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

    override var mapType: Int
        get() = googleMap.mapType
        set(value) {
            googleMap.mapType = value
        }

    override var isTrafficEnabled: Boolean
        get() = googleMap.isTrafficEnabled
        set(value) {
            googleMap.isTrafficEnabled = value
        }

    override var isIndoorEnabled: Boolean
        get() = googleMap.isIndoorEnabled
        set(value) {
            googleMap.isIndoorEnabled = value
        }

    override var isBuildingsEnabled: Boolean
        get() = googleMap.isBuildingsEnabled
        set(value) {
            googleMap.isBuildingsEnabled = value
        }

    override var isMyLocationEnabled: Boolean
        get() = googleMap.isMyLocationEnabled
        set(value) {
            @RequiresPermission(
                    anyOf = arrayOf("android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION")
            )
            googleMap.isMyLocationEnabled = value
        }

    override fun setOnMarkerClickListener(onMarkerClickListener: (marker: IMarker) -> Boolean) {
        googleMap.setOnMarkerClickListener {
            onMarkerClickListener(GoogleMarker(it))
        }
    }

    override fun setOnMapClickListener(onMapClickListener: (latlng: ILatLng) -> Unit) {
        googleMap.setOnMapClickListener {
            onMapClickListener(GoogleLatLng(it))
        }
    }

    override fun setOnMapLongClickListener(onMapLongClickListener: (latlng: ILatLng) -> Unit) {
        googleMap.setOnMapLongClickListener {
            onMapLongClickListener(GoogleLatLng(it))
        }
    }

    override fun setOnCameraChangeListener(onCameraChangeListener: (cameraPosition: ICameraPosition, isFinished: Boolean) -> Unit) {
        googleMap.apply {
            val callback : (isFinished: Boolean) -> Unit = {
                onCameraChangeListener.invoke(GoogleCameraPosition(cameraPosition), it)
            }
            setOnCameraMoveStartedListener { callback(false) }
            setOnCameraMoveListener { callback(false) }
            setOnCameraIdleListener { callback(true) }
            setOnCameraMoveCanceledListener { callback(true) }
        }
    }

    override fun setInfoWindowAdapter(adapter: MapAdapter.InfoWindowAdapter) {
        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {

            override fun getInfoContents(marker: Marker): View {
                return adapter.getInfoContents(GoogleMarker(marker))
            }

            override fun getInfoWindow(marker: Marker): View {
                return adapter.getInfoWindow(GoogleMarker(marker))
            }
        })
    }

    override fun setOnMyLocationChangeListener(onMyLocationChangeListener: (location: Location) -> Unit) {
        googleMap.setOnMyLocationChangeListener { location ->
            onMyLocationChangeListener.invoke(location)
        }
    }

}
