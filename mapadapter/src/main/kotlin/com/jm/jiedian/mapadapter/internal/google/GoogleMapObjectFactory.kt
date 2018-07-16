package com.jm.jiedian.mapadapter.internal.google

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.jm.jiedian.mapadapter.MapObjectFactory
import com.jm.jiedian.mapadapter.mapapi.*
import com.jm.jiedian.mapadapter.mapapi.graphics.*
import com.jm.jiedian.mapadapter.internal.google.delegate.*
import com.jm.jiedian.mapadapter.internal.google.delegate.graphics.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

internal class GoogleMapObjectFactory : MapObjectFactory {

    lateinit var googleMap: GoogleMap

    override val uiSettings: IUISettings
        get() = GoogleUISetting(googleMap.uiSettings)

    override val bitmapDescriptorFactory: IBitmapDescriptorFactory
        get() = GoogleBitmapDescriptorFactory()

    override val cameraUpdateFactory: ICameraUpdateFactory
        get() = GoogleCameraUpdateFactory()

    override fun newLatlng(latitude: Double, longitude: Double): ILatLng {
        return GoogleLatLng(LatLng(latitude, longitude))
    }

    override fun newCameraPositionBuilder(): ICameraPosition.Builder {
        return GoogleCameraPosition.Builder()
    }

    override fun newLatLngBoundBuilder(): ILatLngBounds.Builder {
        return GoogleLatLngBounds.Builder()
    }

    override fun newPolylineOptions(): IPolyline.Options {
        return GooglePolyline.Options()
    }

    override fun newPolygonOptions(): IPolygon.Options {
        return GooglePolygon.Options()
    }

    override fun newCircleOptions(): ICircle.Options {
        return GoogleCircle.Options()
    }

    override fun newMarkerOptions(): IMarker.Options {
        return GoogleMarker.Options()
    }

    override fun newGroundOverlayOptions(): IGroundOverlay.Options {
        return GoogleGroundOverlay.Options()
    }

    override fun newTileOverlayOptions(): ITileOverlay.Options {
        return GoogleTileOverlay.Options()
    }

}
