package pl.atlantischi.mapadapter.prvi_api.gaode

import pl.atlantischi.mapadapter.MapAdapter

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeMapAdapter : MapAdapter {

    fun setAMapGestureListener(listener: IAMapGestureListener)

    fun setMyLocationStyle(myLocationStyle: IMyLocationStyle)

}