package pl.atlantischi.mapadapter.prvi_api.gaode.poi

import pl.atlantischi.mapadapter.prvi_api.gaode.ILatLonPoint

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IPoiItem {

    val latLonPoint: ILatLonPoint

    val title: String

    val snippet: String

}
