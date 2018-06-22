package pl.atlantischi.mapadapter.internal.gaode.priv.route

import com.amap.api.services.route.*
import pl.atlantischi.mapadapter.separate.gaode.route.IGaodeRouteSearch
import com.amap.api.services.route.BusRouteResult

/**
 * Created on 22/06/2018.

 * @author lx
 */

internal class GaodeRouteSearch(var routeSearch: RouteSearch): IGaodeRouteSearch {

    class FromAndTo(var fromAndTo: RouteSearch.FromAndTo) : IGaodeRouteSearch.IFromAndTo

    class WalkRouteQuery(var query: RouteSearch.WalkRouteQuery) : IGaodeRouteSearch.IWalkRouteQuery

    override fun setRouteSearchListener(listener: IGaodeRouteSearch.IOnRouteSearchListener) {
        routeSearch.setRouteSearchListener(object: RouteSearch.OnRouteSearchListener{

            override fun onBusRouteSearched(busRouteResult: BusRouteResult, errorCode: Int) {
                listener.onBusRouteSearched(GaodeBusRouteResult(busRouteResult), errorCode)
            }

            override fun onDriveRouteSearched(driveRouteResult: DriveRouteResult, errorCode: Int){
                listener.onDriveRouteSearched(GaodeDriveRouteResult(driveRouteResult), errorCode)

            }

            override fun onWalkRouteSearched(walkRouteResult: WalkRouteResult, errorCode: Int){
                listener.onWalkRouteSearched(GaodeWalkRouteResult(walkRouteResult), errorCode)

            }

            override fun onRideRouteSearched(rideRouteResult: RideRouteResult, errorCode: Int){
                listener.onRideRouteSearched(GaodeRideRouteResult(rideRouteResult), errorCode)

            }
        })
    }

    override fun calculateWalkRouteAsyn(query: IGaodeRouteSearch.IWalkRouteQuery) {
        val gwrq = query as WalkRouteQuery
        routeSearch.calculateWalkRouteAsyn(gwrq.query)
    }

}
