package pl.atlantischi.mapadapter

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.ViewStub

/**
 * Created on 05/06/2018.

 * @author lx
 */

interface IMapAdapter {

    fun initialize(activity: Activity)

    fun initialize(fragment: Fragment)

    fun setMapViewStub(viewStub: ViewStub)

}
