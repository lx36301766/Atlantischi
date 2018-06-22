package pl.atlantischi.mapadapter.separate.gaode

import android.content.Context
import pl.atlantischi.mapadapter.MapObjectFactory
import pl.atlantischi.mapadapter.separate.gaode.location.IGaodeAMapLocationClient
import pl.atlantischi.mapadapter.separate.gaode.location.IGaodeAMapLocationClientOption
import pl.atlantischi.mapadapter.separate.gaode.poi.IGaodePoiSearch
import pl.atlantischi.mapadapter.separate.gaode.route.IGaodeRouteSearch

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeMapObjectFactory : MapObjectFactory {

    val aMapUtils: IGaodeAMapUtils

    fun newMyLocationStyle(): IGaodeMyLocationStyle

    fun newLatLonPoint(latitude: Double, longitude: Double): IGaodeLatLonPoint

    fun newScaleAnimation(fromX: Float, toX: Float, fromY: Float, toY: Float): IGaodeScaleAnimation

    fun newAMapLocationClient(context: Context): IGaodeAMapLocationClient

    fun newAMapLocationClientOption(): IGaodeAMapLocationClientOption

    fun newPoiSearch(context: Context, query: IGaodePoiSearch.IQuery): IGaodePoiSearch

    fun newPoiSearchQuery(query: String, ctgr: String, city: String): IGaodePoiSearch.IQuery

    fun newRouteSearch(context: Context): IGaodeRouteSearch

    fun newRouteSearchFromAndTo(from: IGaodeLatLonPoint, to: IGaodeLatLonPoint): IGaodeRouteSearch.IFromAndTo

    fun newRouteSearchWalkRouteQuery(fromAndTo: IGaodeRouteSearch.IFromAndTo): IGaodeRouteSearch.IWalkRouteQuery

}
