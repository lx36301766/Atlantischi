package pl.atlantischi.mapadapter.separate.gaode

import pl.atlantischi.mapadapter.mapapi.ILatLng

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeAMapUtils {

    fun calculateLineDistance(src: ILatLng, dest: ILatLng)

}
