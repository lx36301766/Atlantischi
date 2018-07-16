package com.jm.jiedian.mapadapter

import com.jm.jiedian.mapadapter.mapapi.*
import com.jm.jiedian.mapadapter.mapapi.graphics.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

interface MapObjectFactory {

    val uiSettings: IUISettings

    val bitmapDescriptorFactory: IBitmapDescriptorFactory

    val cameraUpdateFactory: ICameraUpdateFactory

    fun newLatlng(latitude: Double, longitude: Double): ILatLng

    fun newCameraPositionBuilder(): ICameraPosition.Builder

    fun newLatLngBoundBuilder(): ILatLngBounds.Builder

    fun newPolylineOptions(): IPolyline.Options

    fun newPolygonOptions(): IPolygon.Options

    fun newCircleOptions(): ICircle.Options

    fun newMarkerOptions(): IMarker.Options

    fun newGroundOverlayOptions(): IGroundOverlay.Options

    fun newTileOverlayOptions(): ITileOverlay.Options

}
