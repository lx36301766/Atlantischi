package com.jm.gaodemap

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.animation.ScaleAnimation
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.route.RouteSearch
import com.jm.gaodemap.common.*
import com.jm.gaodemap.common.graphics.*
import com.jm.gaodemap.priv.GaodeLatLonPoint
import com.jm.gaodemap.priv.GaodeMyLocationStyle
import com.jm.gaodemap.priv.GaodeScaleAnimation
import com.jm.gaodemap.priv.geocoder.GaodeGeocodeSearch
import com.jm.gaodemap.priv.geocoder.GaodeRegeocodeQuery
import com.jm.gaodemap.priv.location.GaodeAMapLocationClient
import com.jm.gaodemap.priv.location.GaodeAMapLocationClientOption
import com.jm.gaodemap.priv.poi.GaodePoiSearch
import com.jm.gaodemap.priv.route.GaodeRouteSearch
import com.jm.jiedian.mapadapter.common.*
import com.jm.jiedian.mapadapter.common.graphics.*
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeLatLonPoint
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeMapObjectFactory
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeMyLocationStyle
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeScaleAnimation
import com.jm.jiedian.mapadapter.priv.gaode.geocoder.IGaodeGeocodeSearch
import com.jm.jiedian.mapadapter.priv.gaode.geocoder.IGaodeRegeocodeQuery
import com.jm.jiedian.mapadapter.priv.gaode.location.IGaodeAMapLocationClient
import com.jm.jiedian.mapadapter.priv.gaode.location.IGaodeAMapLocationClientOption
import com.jm.jiedian.mapadapter.priv.gaode.poi.IGaodePoiSearch
import com.jm.jiedian.mapadapter.priv.gaode.route.IGaodeRouteSearch

/**
 * Created on 15/06/2018.

 * @author lx
 */

internal class GaodeMapObjectFactory : IGaodeMapObjectFactory {

    lateinit var aMap: AMap

    override val uiSettings: IUISettings
        get() = GaodeUISetting(aMap.uiSettings)

    override val bitmapDescriptorFactory: IBitmapDescriptorFactory
        get() = GaodeBitmapDescriptorFactory()

    override val cameraUpdateFactory: ICameraUpdateFactory
        get() = GaodeCameraUpdateFactory()

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

    override fun newPoiSearch(context: Context, query: IGaodePoiSearch.IQuery?): IGaodePoiSearch {
        if (query != null) {
            val gq = query as GaodePoiSearch.Query
            return GaodePoiSearch(PoiSearch(context, gq.query))
        }
        return GaodePoiSearch(PoiSearch(context, null))
    }

    override fun newPoiSearchQuery(query: String, ctgr: String, city: String): IGaodePoiSearch.IQuery {
        return GaodePoiSearch.Query(PoiSearch.Query(query, ctgr, city))
    }

    override fun newGeocodeSearch(context: Context): IGaodeGeocodeSearch {
        return GaodeGeocodeSearch(GeocodeSearch(context))
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
        return GaodeRouteSearch.WalkRouteQuery(RouteSearch.WalkRouteQuery(gFromAndTo.fromAndTo))
    }

    override fun newRegeocodeQuery(point: IGaodeLatLonPoint, radius: Float, latLonType: String): IGaodeRegeocodeQuery {
        val gPoint = point as GaodeLatLonPoint
        return GaodeRegeocodeQuery(RegeocodeQuery(gPoint.latLonPoint, radius, latLonType))
    }

}
