package pl.atlantischi.mapadapter.callback

import android.graphics.Bitmap
import android.view.View

/**
 * Created on 13/06/2018.

 * @author lx
 */

interface IBitmapDescriptorFactory {

    fun fromBitmap(bitmap: Bitmap): IBitmapDescriptor

    fun fromView(view: View): IBitmapDescriptor

}
