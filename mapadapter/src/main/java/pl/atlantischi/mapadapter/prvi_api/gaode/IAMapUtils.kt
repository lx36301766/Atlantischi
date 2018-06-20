package pl.atlantischi.mapadapter.prvi_api.gaode

import pl.atlantischi.mapadapter.common_api.ILatLng

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IAMapUtils {

    fun calculateLineDistance(src: ILatLng, dest: ILatLng)

}
