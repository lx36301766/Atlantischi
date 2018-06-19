package pl.atlantischi.mapadapter.internal.google

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import pl.atlantischi.mapadapter.MapObjectFactory
import pl.atlantischi.mapadapter.callback.*
import pl.atlantischi.mapadapter.callback.graphics.*
import pl.atlantischi.mapadapter.internal.google.delegate.*
import pl.atlantischi.mapadapter.internal.google.delegate.graphics.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

class GoogleMapObjectFactory(googleMap: GoogleMap) : MapObjectFactory {

    override val uiSetting: IUISettings = GoogleUISetting(googleMap.uiSettings)

    override val bitmapDescriptorFactory: IBitmapDescriptorFactory = GoogleBitmapDescriptorFactory()

    override val cameraUpdateFactory: ICameraUpdateFactory = GoogleCameraUpdateFactory()

    override val cameraPosition: ICameraPosition = GoogleCameraPosition(googleMap.cameraPosition)

    override fun newLatlng(latitude: Double, longitude: Double): ILatLng {
        return GoogleLatLng(LatLng(latitude, longitude))
    }

    override fun newLatLngBoundBuilder(): ILatLngBounds.Builder {
        return GoogleLatLngBounds.Builder(LatLngBounds.Builder())
    }

    override fun newPolylineOptions(): IPolylineOptions {
        return GooglePolylineOptions()
    }

    override fun newPolygonOptions(): IPolygonOptions {
        return GooglePolygonOptions()
    }

    override fun newCircleOptions(): ICircleOptions {
        return GoogleCircleOptions()
    }

    override fun newMarkerOptions(): IMarkerOptions {
        return GoogleMarkerOptions()
    }

    override fun newGroundOverlayOptions(): IGroundOverlayOptions {
        return GoogleGroundOverlayOptions()
    }

    override fun newTileOverlayOptions(): ITileOverlayOptions {
        return GoogleTileOverlayOptions()
    }

}
