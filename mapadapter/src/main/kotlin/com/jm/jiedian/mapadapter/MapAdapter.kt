package com.jm.jiedian.mapadapter

import android.location.Location
import android.view.View
import android.view.ViewStub
import com.jm.jiedian.mapadapter.mapapi.*
import com.jm.jiedian.mapadapter.mapapi.graphics.*

/**
 * Created on 05/06/2018.

 * @author lx
 */

interface MapAdapter {

    interface CancelableCallback {
        fun onFinish()

        fun onCancel()
    }

    interface InfoWindowAdapter {
        fun getInfoWindow(marker: IMarker): View
        fun getInfoContents(marker: IMarker): View
    }

    val mapObjectFactory: MapObjectFactory

    fun initMapView(mapViewStub: ViewStub, onMapReadyCallback: () -> Unit)


    val cameraPosition: ICameraPosition

    var maxZoomLevel: Float

    var minZoomLevel: Float

    val myLocation: Location?

    fun moveCamera(cameraUpdate: ICameraUpdate)

    fun animateCamera(cameraUpdate: ICameraUpdate)

    fun animateCamera(cameraUpdate: ICameraUpdate, callback: CancelableCallback)

    fun animateCamera(cameraUpdate: ICameraUpdate, duration: Long, callback: CancelableCallback)

    fun stopAnimation()

    fun addPolyline(polylineOptions: IPolyline.Options): IPolyline

    fun addPolygon(polygonOptions: IPolygon.Options): IPolygon

    fun addCircle(circleOptions: ICircle.Options): ICircle

    fun addMarker(markerOptions: IMarker.Options): IMarker

    fun addGroundOverlay(groundOverlayOptions: IGroundOverlay.Options): IGroundOverlay

    fun addTileOverlay(tileOverlayOptions: ITileOverlay.Options): ITileOverlay

    fun clear()

    var mapType: Int

    var isTrafficEnabled: Boolean

    var isIndoorEnabled: Boolean

    var isBuildingsEnabled: Boolean

    var isMyLocationEnabled: Boolean

    fun setOnMarkerClickListener(onMarkerClickListener: (marker: IMarker) -> Boolean)

    fun setOnMapClickListener(onMapClickListener: (latlng: ILatLng) -> Unit)

    fun setOnMapLongClickListener(onMapLongClickListener: (latlng: ILatLng) -> Unit)

    fun setOnCameraChangeListener(onCameraChangeListener: (cameraPosition: ICameraPosition, isFinished: Boolean) -> Unit)

    fun setInfoWindowAdapter(adapter: InfoWindowAdapter)

    fun setOnMyLocationChangeListener(onMyLocationChangeListener: (location: Location) -> Unit)

}
