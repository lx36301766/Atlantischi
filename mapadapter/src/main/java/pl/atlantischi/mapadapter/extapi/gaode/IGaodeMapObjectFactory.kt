package pl.atlantischi.mapadapter.extapi.gaode

import pl.atlantischi.mapadapter.MapObjectFactory

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeMapObjectFactory : MapObjectFactory {

    val aMapUtils: IAMapUtils

    fun newMyLocationStyle(): IMyLocationStyle

    fun newLatLonPoint(latitude: Double, longitude: Double): ILatLonPoint

}
