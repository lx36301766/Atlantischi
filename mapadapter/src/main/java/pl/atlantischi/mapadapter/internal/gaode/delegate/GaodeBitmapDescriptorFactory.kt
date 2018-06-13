package pl.atlantischi.mapadapter.internal.gaode.delegate

import android.graphics.Bitmap
import android.view.View
import com.amap.api.maps.model.BitmapDescriptorFactory
import pl.atlantischi.mapadapter.callback.IBitmapDescriptor
import pl.atlantischi.mapadapter.callback.IBitmapDescriptorFactory

/**
 * Created on 13/06/2018.

 * @author lx
 */

internal class GaodeBitmapDescriptorFactory: IBitmapDescriptorFactory {

    override fun fromBitmap(bitmap: Bitmap): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(bitmap))
    }

    override fun fromView(view: View): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.fromView(view))
    }

}
