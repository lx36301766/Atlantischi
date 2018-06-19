package pl.atlantischi.mapadapter.internal.gaode

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.ViewStub
import com.amap.api.maps.AMap
import com.amap.api.maps.TextureMapView
import com.amap.api.maps.model.CameraPosition
import pl.atlantischi.mapadapter.MapAdapter
import pl.atlantischi.mapadapter.MapObjectFactory
import pl.atlantischi.mapadapter.R
import pl.atlantischi.mapadapter.internal.gaode.delegate.*
import pl.atlantischi.mapadapter.mapapi.*
import pl.atlantischi.mapadapter.mapapi.graphics.*
import pl.atlantischi.mapadapter.internal.gaode.delegate.graphics.*

/**
 * Created on 05/06/2018.

 * @author lx
 */


internal class GaodeMapAdapter : MapAdapter {

    private lateinit var mapView: TextureMapView

    private lateinit var aMap: AMap

    private var mapViewLifecycleDelegateImpl: GaodeMapViewLifecycleImpl

    constructor(activity: Activity) {
        mapViewLifecycleDelegateImpl = GaodeMapViewLifecycleImpl(activity, mapViewFoundCallback)
    }

    constructor(fragment: Fragment) {
        mapViewLifecycleDelegateImpl = GaodeMapViewLifecycleImpl(fragment, mapViewFoundCallback)
    }

    private val mapViewFoundCallback: (TextureMapView) -> Unit = {
        mapView = it
        aMap = mapView.map
        mapObjectFactory = GaodeMapObjectFactory(aMap)
    }

    override lateinit var mapObjectFactory: MapObjectFactory
        private set

    override fun setMapViewStub(viewStub: ViewStub) {
        viewStub.layoutResource = R.layout.view_gaode_map
        viewStub.inflate()
    }



    override val cameraPosition: ICameraPosition = GaodeCameraPosition(aMap.cameraPosition)

    override val maxZoomLevel = aMap.maxZoomLevel

    override val minZoomLevel = aMap.minZoomLevel

    override fun moveCamera(cameraUpdate: ICameraUpdate) {
        val gau = cameraUpdate as GaodeCameraUpdate
        aMap.moveCamera(gau.cameraUpdate)
    }

    override fun animateCamera(cameraUpdate: ICameraUpdate) {
        val gau = cameraUpdate as GaodeCameraUpdate
        aMap.animateCamera(gau.cameraUpdate)
    }

    override fun animateCamera(cameraUpdate: ICameraUpdate, callback: MapAdapter.CancelableCallback) {
        val gau = cameraUpdate as GaodeCameraUpdate
        aMap.animateCamera(gau.cameraUpdate, object: AMap.CancelableCallback {
            override fun onFinish() {
                callback.onFinish()
            }

            override fun onCancel() {
                callback.onCancel()
            }
        })
    }

    override fun animateCamera(cameraUpdate: ICameraUpdate, duration: Long, callback: MapAdapter.CancelableCallback) {
        val gau = cameraUpdate as GaodeCameraUpdate
        aMap.animateCamera(gau.cameraUpdate, duration, object: AMap.CancelableCallback {
            override fun onFinish() {
                callback.onFinish()
            }

            override fun onCancel() {
                callback.onCancel()
            }
        })
    }

    override fun stopAnimation() {
        aMap.stopAnimation()
    }

    override fun addPolyline(polylineOptions: IPolyline.Options): IPolyline {
        val gpo = polylineOptions as GaodePolyline.Options
        return GaodePolyline(aMap.addPolyline(gpo.options))
    }

    override fun addPolygon(polygonOptions: IPolygon.Options): IPolygon {
        val gpo = polygonOptions as GaodePolygon.Options
        return GaodePolygon(aMap.addPolygon(gpo.options))
    }

    override fun addCircle(circleOptions: ICircle.Options): ICircle {
        val gco = circleOptions as GaodeCircle.Options
        return GaodeCircle(aMap.addCircle(gco.options))
    }

    override fun addMarker(markerOptions: IMarker.Options): IMarker {
        val gmo = markerOptions as GaodeMarker.Options
        return GaodeMarker(aMap.addMarker(gmo.options))
    }

    override fun addGroundOverlay(groundOverlayOptions: IGroundOverlay.Options): IGroundOverlay {
        val goo = groundOverlayOptions as GaodeGroundOverlay.Options
        return GaodeGroundOverlay(aMap.addGroundOverlay(goo.options))
    }

    override fun addTileOverlay(tileOverlayOptions: ITileOverlay.Options): ITileOverlay {
        val gto = tileOverlayOptions as GaodeTileOverlay.Options
        return GaodeTileOverlay(aMap.addTileOverlay(gto.options))
    }

    override fun clear() {
        aMap.clear()
    }

    override var mapType = aMap.mapType
        set(value) {
            aMap.mapType = value
        }

    override var isTrafficEnabled = aMap.isTrafficEnabled
        set(value) {
            aMap.isTrafficEnabled = value
        }

    override var isIndoorEnabled = false //getter not support
        set(value) {
            aMap.showIndoorMap(value)
        }

    override var isBuildingsEnabled = false //getter not support
        set(value) {
            aMap.showBuildings(value)
        }

    override var isMyLocationEnabled = aMap.isMyLocationEnabled
        set(value) {
            aMap.isMyLocationEnabled = value
        }

    override fun setOnMarkerClickListener(onMarkerClickListener: (marker: IMarker) -> Boolean) {
        aMap.setOnMarkerClickListener {
            onMarkerClickListener.invoke(GaodeMarker(it))
        }
    }

    override fun setOnMapClickListener(onMapClickListener: (latlng: ILatLng) -> Unit) {
        aMap.setOnMapClickListener {
            onMapClickListener.invoke(GaodeLatLng(it))
        }
    }

    override fun setOnMapLongClickListener(onMapLongClickListener: (latlng: ILatLng) -> Unit) {
        aMap.setOnMapLongClickListener {
            onMapLongClickListener.invoke(GaodeLatLng(it))
        }
    }

    override fun setOnCameraChangeListener(onCameraChangeListener: (cameraPosition: ICameraPosition, isFinished: Boolean) -> Unit) {
        aMap.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {

            override fun onCameraChange(cameraPosition: CameraPosition) {
                onCameraChangeListener.invoke(GaodeCameraPosition(cameraPosition), false)
            }

            override fun onCameraChangeFinish(cameraPosition: CameraPosition) {
                onCameraChangeListener.invoke(GaodeCameraPosition(cameraPosition), true)
            }

        })
    }

}
