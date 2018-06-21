package pl.atlantischi.mapadapter.separate.gaode.poi

/**
 * Created on 21/06/2018.

 * @author lx
 */

interface IGaodePoiSearch {

    interface IQuery

    interface IOnPoiSearchListener {

        fun onPoiSearched(pageResult: IGaodePoiResult, errorCode: Int)

        fun onPoiItemSearched(poiItem: IGaodePoiItem, errorCode: Int)
    }

    fun setOnPoiSearchListener(poiSearchListener: IOnPoiSearchListener)

    fun setQuery(query: IQuery)

    fun searchPOIAsyn()

}
