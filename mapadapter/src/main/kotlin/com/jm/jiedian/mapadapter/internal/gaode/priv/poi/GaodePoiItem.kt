package com.jm.jiedian.mapadapter.internal.gaode.priv.poi

import com.amap.api.services.core.PoiItem
import com.jm.jiedian.mapadapter.internal.gaode.priv.GaodeLatLonPoint
import com.jm.jiedian.mapadapter.separate.gaode.poi.IGaodePoiItem

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodePoiItem(poiItem: PoiItem) : IGaodePoiItem {

    override val gaodeLatLonPoint = GaodeLatLonPoint(poiItem.latLonPoint)

    override val title = poiItem.title

    override val snippet = poiItem.snippet

}
