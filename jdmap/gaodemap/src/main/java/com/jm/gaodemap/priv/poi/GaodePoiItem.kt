package com.jm.gaodemap.priv.poi

import com.amap.api.services.core.PoiItem
import com.jm.gaodemap.priv.GaodeLatLonPoint
import com.jm.jiedian.mapadapter.priv.gaode.poi.IGaodePoiItem

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodePoiItem(poiItem: PoiItem) : IGaodePoiItem {

    override val gaodeLatLonPoint = GaodeLatLonPoint(poiItem.latLonPoint)

    override val title = poiItem.title

    override val snippet = poiItem.snippet

}
