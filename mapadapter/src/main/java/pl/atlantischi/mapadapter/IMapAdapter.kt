package pl.atlantischi.mapadapter

import android.view.ViewStub

/**
 * Created on 05/06/2018.

 * @author lx
 */

interface IMapAdapter {

    fun setMapViewStub(viewStub: ViewStub)

    fun getUISetting(): IUISettings

//    fun getMarkerConfig(): IMarkerConfig

}
