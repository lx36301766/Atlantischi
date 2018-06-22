package pl.atlantischi.mapadapter.separate.gaode.route

/**
 * Created on 22/06/2018.

 * @author lx
 */

interface IGaodeRouteSearch {

    interface IFromAndTo

    interface IWalkRouteQuery

    interface IOnRouteSearchListener {

        fun onBusRouteSearched(busRouteResult: IGaodeBusRouteResult, errorCode: Int)

        fun onDriveRouteSearched(driveRouteResult: IGaodeDriveRouteResult, errorCode: Int)

        fun onWalkRouteSearched(walkRouteResult: IGaodeWalkRouteResult, errorCode: Int)

        fun onRideRouteSearched(rideRouteResult: IGaodeRideRouteResult, errorCode: Int)
    }


    fun setRouteSearchListener(listener: IOnRouteSearchListener)

    fun calculateWalkRouteAsyn(query: IWalkRouteQuery)

}