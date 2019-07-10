package com.jm.gaodemap.priv.poi

import com.amap.api.services.poisearch.PoiResult
import com.jm.jiedian.mapadapter.priv.gaode.poi.IGaodePoiItem
import com.jm.jiedian.mapadapter.priv.gaode.poi.IGaodePoiResult

/**
 * Created on 21/06/2018.

 * @author lx
 */

internal class GaodePoiResult(val poiResult : PoiResult): IGaodePoiResult {

    override val pois: List<IGaodePoiItem>
        get() {
            val list = mutableListOf<IGaodePoiItem>()
            for (poi in poiResult.pois) {
                list.add(GaodePoiItem(poi))
            }
            return list
        }

}
