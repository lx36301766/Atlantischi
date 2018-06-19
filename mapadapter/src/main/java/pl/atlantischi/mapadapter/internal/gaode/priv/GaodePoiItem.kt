package pl.atlantischi.mapadapter.internal.gaode.priv

import com.amap.api.services.core.PoiItem
import pl.atlantischi.mapadapter.extapi.gaode.poi.IPoiItem

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodePoiItem(poiItem: PoiItem) : IPoiItem {

    override val latLonPoint = GaodeLatLonPoint(poiItem.latLonPoint)

    override val title = poiItem.title

    override val snippet = poiItem.snippet

}
