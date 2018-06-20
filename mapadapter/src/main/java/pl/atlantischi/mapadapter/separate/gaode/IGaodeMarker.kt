package pl.atlantischi.mapadapter.separate.gaode

import pl.atlantischi.mapadapter.mapapi.graphics.IMarker

/**
 * Created on 20/06/2018.

 * @author lx
 */

interface IGaodeMarker : IMarker {

    fun setAnimation(animation: IGaodeAnimation)

    fun startAnimation(): Boolean

}
