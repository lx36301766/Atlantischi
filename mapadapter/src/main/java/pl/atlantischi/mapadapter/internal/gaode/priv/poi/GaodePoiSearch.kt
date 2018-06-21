package pl.atlantischi.mapadapter.internal.gaode.priv.poi

import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import pl.atlantischi.mapadapter.separate.gaode.poi.IGaodePoiSearch

/**
 * Created on 21/06/2018.

 * @author lx
 */

internal class GaodePoiSearch(val poiSearch: PoiSearch) : IGaodePoiSearch {

    class Query(val query: PoiSearch.Query) : IGaodePoiSearch.IQuery

    override fun setOnPoiSearchListener(poiSearchListener: IGaodePoiSearch.IOnPoiSearchListener) {
        poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {

            override fun onPoiItemSearched(poiItem: PoiItem, errorCode: Int) {
                poiSearchListener.onPoiItemSearched(GaodePoiItem(poiItem), errorCode)
            }

            override fun onPoiSearched(pageResult: PoiResult, errorCode: Int) {
                poiSearchListener.onPoiSearched(GaodePoiResult(pageResult), errorCode)
            }
        })
    }

    override fun setQuery(query: IGaodePoiSearch.IQuery) {
        val gq = query as Query
        poiSearch.query = gq.query
    }

    override fun searchPOIAsyn() {
        poiSearch.searchPOIAsyn()
    }

}
