package pl.atlantischi.mapadapter.internal.gaode.delegate

import android.graphics.Bitmap
import android.view.View
import com.amap.api.maps.model.BitmapDescriptorFactory
import pl.atlantischi.mapadapter.mapapi.IBitmapDescriptor
import pl.atlantischi.mapadapter.mapapi.IBitmapDescriptorFactory

/**
 * Created on 13/06/2018.

 * @author lx
 */

internal class GaodeBitmapDescriptorFactory: IBitmapDescriptorFactory {

    override fun defaultMarker(): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.defaultMarker())
    }

    override fun defaultMarker(hue: Float): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.defaultMarker(hue))
    }

    override fun fromAsset(asset: String): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.fromAsset(asset))
    }

    override fun fromFile(file: String): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.fromFile(file))
    }

    override fun fromPath(path: String): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.fromPath(path))
    }

    override fun fromResource(resId: Int): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.fromResource(resId))
    }

    override fun fromBitmap(bitmap: Bitmap): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(bitmap))
    }

    override fun fromView(view: View): IBitmapDescriptor {
        return GaodeBitmapDescriptor(BitmapDescriptorFactory.fromView(view))
    }

}
