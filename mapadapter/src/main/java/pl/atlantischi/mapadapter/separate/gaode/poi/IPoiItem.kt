package pl.atlantischi.mapadapter.separate.gaode.poi

import pl.atlantischi.mapadapter.separate.gaode.ILatLonPoint

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IPoiItem {

    val latLonPoint: ILatLonPoint

    val title: String

    val snippet: String

}
