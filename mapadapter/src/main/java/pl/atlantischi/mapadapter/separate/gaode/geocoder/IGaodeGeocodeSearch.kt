package pl.atlantischi.mapadapter.separate.gaode.geocoder

/**
 * Created on 21/06/2018.

 * @author lx
 */

interface IGaodeGeocodeSearch {

    interface IOnGeocodeSearchListener {

        fun onRegeocodeSearched(regeocodeResult: IGaodeRegeocodeResult, resultID: Int)

        fun onGeocodeSearched(geocodeResult: IGaodeGeocodeResult, resultID: Int)
    }

    fun setOnGeocodeSearchListener(onGeocodeSearchListener: IOnGeocodeSearchListener)

    fun getFromLocationAsyn(query: IGaodeRegeocodeQuery)

}
