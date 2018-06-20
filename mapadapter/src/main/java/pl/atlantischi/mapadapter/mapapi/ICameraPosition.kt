package pl.atlantischi.mapadapter.mapapi

/**
 * Created on 11/06/2018.

 * @author lx
 */

interface ICameraPosition {

    val target: ILatLng

    val zoom: Float

    val tilt: Float

    val bearing: Float

    interface Builder {

        fun target(latlng: ILatLng): Builder

        fun zoom(zoom: Float): Builder

        fun tilt(tilt: Float): Builder

        fun bearing(bearing: Float): Builder

        fun build(): ICameraPosition

    }

}
