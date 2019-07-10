package com.jm.googlemap.common

import android.graphics.Bitmap
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.jm.jiedian.mapadapter.common.IBitmapDescriptor
import com.jm.jiedian.mapadapter.common.IBitmapDescriptorFactory

/**
 * Created on 13/06/2018.

 * @author lx
 */

internal class GoogleBitmapDescriptorFactory: IBitmapDescriptorFactory {

    override fun defaultMarker(): IBitmapDescriptor {
        return GoogleBitmapDescriptor(BitmapDescriptorFactory.defaultMarker())
    }

    override fun defaultMarker(hue: Float): IBitmapDescriptor {
        return GoogleBitmapDescriptor(BitmapDescriptorFactory.defaultMarker(hue))
    }

    override fun fromAsset(asset: String): IBitmapDescriptor {
        return GoogleBitmapDescriptor(BitmapDescriptorFactory.fromAsset(asset))
    }

    override fun fromFile(file: String): IBitmapDescriptor {
        return GoogleBitmapDescriptor(BitmapDescriptorFactory.fromFile(file))
    }

    override fun fromPath(path: String): IBitmapDescriptor {
        return GoogleBitmapDescriptor(BitmapDescriptorFactory.fromPath(path))
    }

    override fun fromResource(resId: Int): IBitmapDescriptor {
        return GoogleBitmapDescriptor(BitmapDescriptorFactory.fromResource(resId))
    }

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
        return view.drawingCache?.copy(Bitmap.Config.ARGB_8888, false)
    }

}
