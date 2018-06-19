package pl.atlantischi.mapadapter

import android.view.ViewStub
import pl.atlantischi.mapadapter.callback.*
import pl.atlantischi.mapadapter.callback.graphics.*

/**
 * Created on 05/06/2018.

 * @author lx
 */

interface MapAdapter {

    interface CancelableCallback {
        fun onFinish()

        fun onCancel()
    }

    val mapObjectFactory: MapObjectFactory

    fun setMapViewStub(viewStub: ViewStub)


    val maxZoomLevel: Float

    val minZoomLevel: Float

    fun moveCamera(cameraUpdate: ICameraUpdate)

    fun animateCamera(cameraUpdate: ICameraUpdate)

    fun animateCamera(cameraUpdate: ICameraUpdate, callback: CancelableCallback)

    fun animateCamera(cameraUpdate: ICameraUpdate, duration: Long, callback: CancelableCallback)

    fun stopAnimation()

    fun addPolyline(polylineOptions: IPolylineOptions): IPolyline

    fun addPolygon(polygonOptions: IPolygonOptions): IPolygon

    fun addCircle(circleOptions: ICircleOptions): ICircle

    fun addMarker(markerOptions: IMarkerOptions): IMarker

    fun addGroundOverlay(groundOverlayOptions: IGroundOverlayOptions): IGroundOverlay

    fun addTileOverlay(tileOverlayOptions: ITileOverlayOptions): ITileOverlay

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

}
