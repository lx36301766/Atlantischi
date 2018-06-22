package pl.atlantischi.mapadapter.internal.gaode

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.animation.ScaleAnimation
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.route.RouteSearch
import pl.atlantischi.mapadapter.separate.gaode.IGaodeMapObjectFactory
import pl.atlantischi.mapadapter.separate.gaode.IGaodeLatLonPoint
import pl.atlantischi.mapadapter.separate.gaode.IGaodeMyLocationStyle
import pl.atlantischi.mapadapter.mapapi.*
import pl.atlantischi.mapadapter.mapapi.graphics.*
import pl.atlantischi.mapadapter.internal.gaode.delegate.*
import pl.atlantischi.mapadapter.internal.gaode.delegate.graphics.*
import pl.atlantischi.mapadapter.internal.gaode.priv.*
import pl.atlantischi.mapadapter.internal.gaode.priv.location.GaodeAMapLocationClient
import pl.atlantischi.mapadapter.internal.gaode.priv.location.GaodeAMapLocationClientOption
import pl.atlantischi.mapadapter.internal.gaode.priv.poi.GaodePoiSearch
import pl.atlantischi.mapadapter.internal.gaode.priv.route.GaodeRouteSearch
import pl.atlantischi.mapadapter.separate.gaode.IGaodeScaleAnimation
import pl.atlantischi.mapadapter.separate.gaode.location.IGaodeAMapLocationClient
import pl.atlantischi.mapadapter.separate.gaode.location.IGaodeAMapLocationClientOption
import pl.atlantischi.mapadapter.separate.gaode.poi.IGaodePoiSearch
import pl.atlantischi.mapadapter.separate.gaode.route.IGaodeRouteSearch

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

    override fun newAMapLocationClient(context: Context): IGaodeAMapLocationClient {
        return GaodeAMapLocationClient(AMapLocationClient(context))
    }

    override fun newAMapLocationClientOption(): IGaodeAMapLocationClientOption {
        return GaodeAMapLocationClientOption(AMapLocationClientOption())
    }

    override fun newPoiSearch(context: Context, query: IGaodePoiSearch.IQuery): IGaodePoiSearch {
        val gq = query as GaodePoiSearch.Query
        return GaodePoiSearch(PoiSearch(context, gq.query))
    }

    override fun newPoiSearchQuery(query: String, ctgr: String, city: String): IGaodePoiSearch.IQuery {
        return GaodePoiSearch.Query(PoiSearch.Query(query, ctgr, city))
    }

    override fun newRouteSearch(context: Context): IGaodeRouteSearch {
        return GaodeRouteSearch(RouteSearch(context))
    }

    override fun newRouteSearchFromAndTo(from: IGaodeLatLonPoint, to: IGaodeLatLonPoint): IGaodeRouteSearch.IFromAndTo {
        val gFrom = from as GaodeLatLonPoint
        val gTo = to as GaodeLatLonPoint
        return GaodeRouteSearch.FromAndTo(RouteSearch.FromAndTo(gFrom.latLonPoint, gTo.latLonPoint))
    }

    override fun newRouteSearchWalkRouteQuery(fromAndTo: IGaodeRouteSearch.IFromAndTo): IGaodeRouteSearch.IWalkRouteQuery {
        val gFromAndTo = fromAndTo as GaodeRouteSearch.FromAndTo
        return GaodeRouteSearch.WalkRouteQuery (RouteSearch.WalkRouteQuery (gFromAndTo.fromAndTo))
    }

}
