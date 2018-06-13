package pl.atlantischi.mapadapter.callback

/**
 * Created on 11/06/2018.

 * @author lx
 */

interface ICameraPosition {

    val target: ILatLng

    val zoom: Float

    val tilt: Float

    val bearing: Float

}
