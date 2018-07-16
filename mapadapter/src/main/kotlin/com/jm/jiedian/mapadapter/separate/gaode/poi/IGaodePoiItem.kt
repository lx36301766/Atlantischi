package com.jm.jiedian.mapadapter.separate.gaode.poi

import com.jm.jiedian.mapadapter.separate.gaode.IGaodeLatLonPoint

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodePoiItem {

    val gaodeLatLonPoint: IGaodeLatLonPoint

    val title: String

    val snippet: String

}
