package pl.atlantischi.mapadapter.separate.gaode.poi

import pl.atlantischi.mapadapter.separate.gaode.IGaodeLatLonPoint

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodePoiItem {

    val gaodeLatLonPoint: IGaodeLatLonPoint

    val title: String

    val snippet: String

}
