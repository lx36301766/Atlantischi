package com.jm.jiedian.mapadapter.separate.gaode

import android.content.Context
import com.jm.jiedian.mapadapter.MapObjectFactory
import com.jm.jiedian.mapadapter.separate.gaode.geocoder.IGaodeGeocodeSearch
import com.jm.jiedian.mapadapter.separate.gaode.geocoder.IGaodeRegeocodeQuery
import com.jm.jiedian.mapadapter.separate.gaode.location.IGaodeAMapLocationClient
import com.jm.jiedian.mapadapter.separate.gaode.location.IGaodeAMapLocationClientOption
import com.jm.jiedian.mapadapter.separate.gaode.poi.IGaodePoiSearch
import com.jm.jiedian.mapadapter.separate.gaode.route.IGaodeRouteSearch

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeMapObjectFactory : MapObjectFactory {

    fun newMyLocationStyle(): IGaodeMyLocationStyle

    fun newLatLonPoint(latitude: Double, longitude: Double): IGaodeLatLonPoint

    fun newScaleAnimation(fromX: Float, toX: Float, fromY: Float, toY: Float): IGaodeScaleAnimation

    fun newAMapLocationClient(context: Context): IGaodeAMapLocationClient

    fun newAMapLocationClientOption(): IGaodeAMapLocationClientOption

    fun newPoiSearch(context: Context, query: IGaodePoiSearch.IQuery?): IGaodePoiSearch

    fun newPoiSearchQuery(query: String, ctgr: String, city: String): IGaodePoiSearch.IQuery

    fun newGeocodeSearch(context: Context): IGaodeGeocodeSearch

    fun newRouteSearch(context: Context): IGaodeRouteSearch

    fun newRouteSearchFromAndTo(from: IGaodeLatLonPoint, to: IGaodeLatLonPoint): IGaodeRouteSearch.IFromAndTo

    fun newRouteSearchWalkRouteQuery(fromAndTo: IGaodeRouteSearch.IFromAndTo): IGaodeRouteSearch.IWalkRouteQuery

    fun newRegeocodeQuery(point: IGaodeLatLonPoint, radius: Float, latLonType: String): IGaodeRegeocodeQuery

}
