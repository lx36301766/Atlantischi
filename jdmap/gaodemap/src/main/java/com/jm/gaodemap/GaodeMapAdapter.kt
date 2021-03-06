package com.jm.gaodemap

import android.app.Activity
import android.location.Location
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewStub
import com.amap.api.maps.AMap
import com.amap.api.maps.MapsInitializer
import com.amap.api.maps.TextureMapView
import com.amap.api.maps.model.AMapGestureListener
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.Marker
import com.jm.gaodemap.common.GaodeCameraPosition
import com.jm.gaodemap.common.GaodeCameraUpdate
import com.jm.gaodemap.common.GaodeLatLng
import com.jm.gaodemap.common.graphics.*
import com.jm.gaodemap.priv.GaodeMyLocationStyle
import com.jm.jiedian.mapadapter.MapAdapter
import com.jm.jiedian.mapadapter.R
import com.jm.jiedian.mapadapter.common.ICameraPosition
import com.jm.jiedian.mapadapter.common.ICameraUpdate
import com.jm.jiedian.mapadapter.common.ILatLng
import com.jm.jiedian.mapadapter.common.graphics.*
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeAMapGestureListener
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeMapAdapter
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeMyLocationStyle

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */

internal class GaodeMapAdapter(f: Fragment? = null, a: Activity? = null) : IGaodeMapAdapter {

//    private lateinit var mapView: TextureMapView

    private lateinit var aMap: AMap

    private var mapViewLifecycleImpl: GaodeMapViewLifecycleImpl = GaodeMapViewLifecycleImpl(f, a)

    override fun initMapView(mapViewStub: ViewStub, onMapReadyCallback: () -> Unit) {
        mapViewStub.layoutResource = R.layout.view_gaode_map
        val mapView = mapViewStub.inflate() as TextureMapView
        mapViewLifecycleImpl.mapView = mapView
        MapsInitializer.initialize(mapView.context.applicationContext)
        aMap = mapView.map
        mapObjectFactory.aMap = aMap
        onMapReadyCallback.invoke()
    }

    override var mapObjectFactory = GaodeMapObjectFactory()


    override val cameraPosition: ICameraPosition
        get() = GaodeCameraPosition(aMap.cameraPosition)

    override var maxZoomLevel:Float
        get() = aMap.maxZoomLevel
        set(value) {
            aMap.maxZoomLevel = value
        }

    override var minZoomLevel:Float
        get() = aMap.minZoomLevel
        set(value) {
            aMap.minZoomLevel = value
        }

    override val myLocation
        get() = aMap.myLocation

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

    override var mapType: Int
        get() = aMap.mapType
        set(value) {
            aMap.mapType = value
        }

    override var isTrafficEnabled: Boolean
        get() = aMap.isTrafficEnabled
        set(value) {
            aMap.isTrafficEnabled = value
        }

    override var isIndoorEnabled: Boolean
        get() = false //getter not support
        set(value) {
            aMap.showIndoorMap(value)
        }

    override var isBuildingsEnabled: Boolean
        get() = false //getter not support
        set(value) {
            aMap.showBuildings(value)
        }

    override var isMyLocationEnabled: Boolean
        get() = aMap.isMyLocationEnabled
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

    override fun setInfoWindowAdapter(adapter: MapAdapter.InfoWindowAdapter) {
        aMap.setInfoWindowAdapter(object : AMap.InfoWindowAdapter {

            override fun getInfoContents(marker: Marker): View {
                return adapter.getInfoContents(GaodeMarker(marker))
            }

            override fun getInfoWindow(marker: Marker): View {
                return adapter.getInfoWindow(GaodeMarker(marker))
            }
        })
    }

    override fun setOnMyLocationChangeListener(onMyLocationChangeListener: (location: Location) -> Unit) {
        aMap.setOnMyLocationChangeListener { location ->
            onMyLocationChangeListener.invoke(location)
        }
    }

    /***************************** private api ***********************************/

    override fun setAMapGestureListener(listener: IGaodeAMapGestureListener) {
        aMap.setAMapGestureListener(object: AMapGestureListener {

            override fun onDown(x: Float, y: Float) {
                listener.onDown(x, y)
            }

            override fun onDoubleTap(x: Float, y: Float) {
                listener.onDoubleTap(x, y)
            }

            override fun onFling(velocityX: Float, velocityY: Float) {
                listener.onFling(velocityX, velocityY)
            }

            override fun onSingleTap(x: Float, y: Float) {
                listener.onSingleTap(x, y)
            }

            override fun onScroll(distanceX: Float, distanceY: Float) {
                listener.onScroll(distanceX, distanceY)
            }

            override fun onMapStable() {
                listener.onMapStable()
            }

            override fun onUp(x: Float, y: Float) {
                listener.onUp(x, y)
            }

            override fun onLongPress(x: Float, y: Float) {
                listener.onLongPress(x, y)
            }
        })
    }

    override fun setMyLocationStyle(gaodeMyLocationStyle: IGaodeMyLocationStyle) {
        val gms = gaodeMyLocationStyle as GaodeMyLocationStyle
        aMap.myLocationStyle = gms.myLocationStyle
    }

    override fun setMapCustomEnable(enable: Boolean) {
        aMap.setMapCustomEnable(enable)
    }

    override fun setCustomMapStylePath(path: String) {
        aMap.setCustomMapStylePath(path)
    }

    override fun getMapScreenMarkers(): List<IMarker> {
        val markers = aMap.mapScreenMarkers
        val gaodeMarkList = mutableListOf<GaodeMarker>()
        for (marker in markers) {
            gaodeMarkList.add(GaodeMarker(marker))
        }
        return gaodeMarkList
    }

}
