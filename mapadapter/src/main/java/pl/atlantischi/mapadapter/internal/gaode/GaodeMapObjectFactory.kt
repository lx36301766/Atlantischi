package pl.atlantischi.mapadapter.internal.gaode

import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import pl.atlantischi.mapadapter.MapObjectFactory
import pl.atlantischi.mapadapter.callback.*
import pl.atlantischi.mapadapter.callback.graphics.*
import pl.atlantischi.mapadapter.internal.gaode.delegate.*
import pl.atlantischi.mapadapter.internal.gaode.delegate.graphics.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

class GaodeMapObjectFactory(aMap: AMap) : MapObjectFactory {

    override val uiSetting: IUISettings = GaodeUISetting(aMap.uiSettings)

    override val bitmapDescriptorFactory: IBitmapDescriptorFactory = GaodeBitmapDescriptorFactory()

    override val cameraUpdateFactory: ICameraUpdateFactory = GaodeCameraUpdateFactory()

    override val cameraPosition: ICameraPosition = GaodeCameraPosition(aMap.cameraPosition)

    override fun newLatlng(latitude: Double, longitude: Double): ILatLng {
        return GaodeLatLng(LatLng(latitude, longitude))
    }

    override fun newLatLngBoundBuilder(): ILatLngBounds.Builder {
        return GaodeLatLngBounds.Builder(LatLngBounds.Builder())
    }

    override fun newPolylineOptions(): IPolylineOptions {
        return GaodePolylineOptions()
    }

    override fun newPolygonOptions(): IPolygonOptions {
        return GaodePolygonOptions()
    }

    override fun newCircleOptions(): ICircleOptions {
        return GaodeCircleOptions()
    }

    override fun newMarkerOptions(): IMarkerOptions {
        return GaodeMarkerOptions()
    }

    override fun newGroundOverlayOptions(): IGroundOverlayOptions {
        return GaodeGroundOverlayOptions()
    }

    override fun newTileOverlayOptions(): ITileOverlayOptions {
        return GaodeTileOverlayOptions()
    }

}
