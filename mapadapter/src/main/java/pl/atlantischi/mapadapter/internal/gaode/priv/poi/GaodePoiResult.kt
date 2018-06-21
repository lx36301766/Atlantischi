package pl.atlantischi.mapadapter.internal.gaode.priv.poi

import com.amap.api.services.poisearch.PoiResult
import pl.atlantischi.mapadapter.separate.gaode.poi.IGaodePoiItem
import pl.atlantischi.mapadapter.separate.gaode.poi.IGaodePoiResult

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
