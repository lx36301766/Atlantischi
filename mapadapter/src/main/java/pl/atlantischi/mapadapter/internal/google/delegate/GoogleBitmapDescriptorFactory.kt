package pl.atlantischi.mapadapter.internal.google.delegate

import android.graphics.Bitmap
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import pl.atlantischi.mapadapter.callback.IBitmapDescriptor
import pl.atlantischi.mapadapter.callback.IBitmapDescriptorFactory

/**
 * Created on 13/06/2018.

 * @author lx
 */

internal class GoogleBitmapDescriptorFactory: IBitmapDescriptorFactory {

    override fun fromBitmap(bitmap: Bitmap): IBitmapDescriptor {
        return GoogleBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(bitmap))
    }

    override fun fromView(view: View): IBitmapDescriptor {
        val app = view.context.applicationContext
        val frameLayout = FrameLayout(app)
        frameLayout.addView(view)
        frameLayout.isDrawingCacheEnabled = true
        val bitmap = getBitmapFromView(frameLayout)
        return GoogleBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(bitmap))
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        view.destroyDrawingCache()
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = view.drawingCache
        return if (bitmap != null) bitmap.copy(Bitmap.Config.ARGB_8888, false) else null
    }

}
