package pl.atlantischi.mapadapter.internal.google

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import pl.atlantischi.mapadapter.MapObjectFactory
import pl.atlantischi.mapadapter.mapapi.*
import pl.atlantischi.mapadapter.mapapi.graphics.*
import pl.atlantischi.mapadapter.internal.google.delegate.*
import pl.atlantischi.mapadapter.internal.google.delegate.graphics.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

internal class GoogleMapObjectFactory(googleMap: GoogleMap) : MapObjectFactory {

    override val uiSetting: IUISettings = GoogleUISetting(googleMap.uiSettings)

    override val bitmapDescriptorFactory: IBitmapDescriptorFactory = GoogleBitmapDescriptorFactory()

    override val cameraUpdateFactory: ICameraUpdateFactory = GoogleCameraUpdateFactory()

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
