package pl.atlantischi.mapadapter.internal.gaode

import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.animation.ScaleAnimation
import com.amap.api.services.core.LatLonPoint
import pl.atlantischi.mapadapter.separate.gaode.IGaodeMapObjectFactory
import pl.atlantischi.mapadapter.separate.gaode.IGaodeLatLonPoint
import pl.atlantischi.mapadapter.separate.gaode.IGaodeMyLocationStyle
import pl.atlantischi.mapadapter.mapapi.*
import pl.atlantischi.mapadapter.mapapi.graphics.*
import pl.atlantischi.mapadapter.internal.gaode.delegate.*
import pl.atlantischi.mapadapter.internal.gaode.delegate.graphics.*
import pl.atlantischi.mapadapter.internal.gaode.priv.GaodeAMapUtils
import pl.atlantischi.mapadapter.internal.gaode.priv.GaodeLatLonPoint
import pl.atlantischi.mapadapter.internal.gaode.priv.GaodeMyLocationStyle
import pl.atlantischi.mapadapter.internal.gaode.priv.GaodeScaleAnimation
import pl.atlantischi.mapadapter.separate.gaode.IGaodeScaleAnimation

/**
 * Created on 15/06/2018.

 * @author lx
 */

internal class GaodeMapObjectFactory(aMap: AMap) : IGaodeMapObjectFactory {

    override val uiSetting: IUISettings = GaodeUISetting(aMap.uiSettings)

    override val bitmapDescriptorFactory: IBitmapDescriptorFactory = GaodeBitmapDescriptorFactory()

    override val cameraUpdateFactory: ICameraUpdateFactory = GaodeCameraUpdateFactory()

    override fun newLatlng(latitude: Double, longitude: Double): ILatLng {
        return GaodeLatLng(LatLng(latitude, longitude))
    }

    override fun newCameraPositionBuilder(): ICameraPosition.Builder {
        return GaodeCameraPosition.Builder()
    }

    override fun newLatLngBoundBuilder(): ILatLngBounds.Builder {
        return GaodeLatLngBounds.Builder()
    }

    override fun newPolylineOptions(): IPolyline.Options {
        return GaodePolyline.Options()
    }

    override fun newPolygonOptions(): IPolygon.Options {
        return GaodePolygon.Options()
    }

    override fun newCircleOptions(): ICircle.Options {
        return GaodeCircle.Options()
    }

    override fun newMarkerOptions(): IMarker.Options {
        return GaodeMarker.Options()
    }

    override fun newGroundOverlayOptions(): IGroundOverlay.Options {
        return GaodeGroundOverlay.Options()
    }

    override fun newTileOverlayOptions(): ITileOverlay.Options {
        return GaodeTileOverlay.Options()
    }



    /******************************* private api ***************************************/

    override val aMapUtils = GaodeAMapUtils()

    override fun newMyLocationStyle(): IGaodeMyLocationStyle {
        return GaodeMyLocationStyle()
    }

    override fun newLatLonPoint(latitude: Double, longitude: Double): IGaodeLatLonPoint {
        return GaodeLatLonPoint(LatLonPoint(latitude, longitude))
    }

    override fun newScaleAnimation(fromX: Float, toX: Float, fromY: Float, toY: Float): IGaodeScaleAnimation {
        return GaodeScaleAnimation(ScaleAnimation(fromX, toX, fromY, toY))
    }

}
