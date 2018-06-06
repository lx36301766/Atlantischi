package pl.atlantischi.mapadapter

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

/**
 * Created on 05/06/2018.

 * @author lx
 */

interface IMap {

    fun initialize(activity: Activity)

    fun initialize(fragment: Fragment)

}
