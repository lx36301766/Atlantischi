package pl.atlantischi.mapadapter.internal.gaode.priv.geocoder

import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import pl.atlantischi.mapadapter.separate.gaode.geocoder.IGaodeGeocodeSearch
import pl.atlantischi.mapadapter.separate.gaode.geocoder.IGaodeRegeocodeQuery

/**
 * Created on 21/06/2018.

 * @author lx
 */

internal class GaodeGeocodeSearch(val geocodeSearch: GeocodeSearch) : IGaodeGeocodeSearch {

    override fun setOnGeocodeSearchListener(onGeocodeSearchListener: IGaodeGeocodeSearch.IOnGeocodeSearchListener) {
        geocodeSearch.setOnGeocodeSearchListener(object: GeocodeSearch.OnGeocodeSearchListener{

            override fun onRegeocodeSearched(regeocodeResult: RegeocodeResult, resultID: Int) {
                onGeocodeSearchListener.onRegeocodeSearched(GaodeRegeocodeResult(regeocodeResult), resultID)
            }

            override fun onGeocodeSearched(geocodeResult: GeocodeResult, resultID: Int) {
                onGeocodeSearchListener.onGeocodeSearched(GaodeGeocodeResult(geocodeResult), resultID)
            }
        })
    }

    override fun getFromLocationAsyn(query: IGaodeRegeocodeQuery) {
        val grq = query as GaodeRegeocodeQuery
        geocodeSearch.getFromLocation(grq.regeocodeQuery)
    }

}
