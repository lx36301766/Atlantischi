package pl.atlantischi.mapadapter.separate.gaode

import pl.atlantischi.mapadapter.MapObjectFactory

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeMapObjectFactory : MapObjectFactory {

    val aMapUtils: IAMapUtils

    fun newMyLocationStyle(): IGaodeMyLocationStyle

    fun newLatLonPoint(latitude: Double, longitude: Double): IGaodeLatLonPoint

    fun newScaleAnimation(fromX: Float, toX: Float, fromY: Float, toY: Float): IGaodeScaleAnimation

}
