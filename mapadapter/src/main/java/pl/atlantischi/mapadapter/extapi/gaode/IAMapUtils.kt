package pl.atlantischi.mapadapter.extapi.gaode

import pl.atlantischi.mapadapter.mapapi.ILatLng

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IAMapUtils {

    fun calculateLineDistance(src: ILatLng, dest: ILatLng)

}
