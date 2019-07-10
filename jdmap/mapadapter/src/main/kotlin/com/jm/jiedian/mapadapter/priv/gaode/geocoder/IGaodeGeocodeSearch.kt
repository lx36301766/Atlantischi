package com.jm.jiedian.mapadapter.priv.gaode.geocoder

/**
 * Created on 21/06/2018.

 * @author lx
 */

interface IGaodeGeocodeSearch {

    companion object {
        const val GPS = "gps"
        const val AMAP = "autonavi"
    }

    interface IOnGeocodeSearchListener {

        fun onRegeocodeSearched(regeocodeResult: IGaodeRegeocodeResult, resultID: Int)

        fun onGeocodeSearched(geocodeResult: IGaodeGeocodeResult, resultID: Int)
    }

    fun setOnGeocodeSearchListener(onGeocodeSearchListener: IOnGeocodeSearchListener)

    fun getFromLocationAsyn(query: IGaodeRegeocodeQuery)

}
