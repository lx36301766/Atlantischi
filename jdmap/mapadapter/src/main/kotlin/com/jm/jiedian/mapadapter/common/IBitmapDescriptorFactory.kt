package com.jm.jiedian.mapadapter.common

import android.graphics.Bitmap
import android.view.View

/**
 * Created on 13/06/2018.

 * @author lx
 */

interface IBitmapDescriptorFactory {

    companion object {
        const val HUE_RED = 0.0f
        const val HUE_ORANGE = 30.0f
        const val HUE_YELLOW = 60.0f
        const val HUE_GREEN = 120.0f
        const val HUE_CYAN = 180.0f
        const val HUE_AZURE = 210.0f
        const val HUE_BLUE = 240.0f
        const val HUE_VIOLET = 270.0f
        const val HUE_MAGENTA = 300.0f
        const val HUE_ROSE = 330.0f
    }

    fun defaultMarker(): IBitmapDescriptor

    fun defaultMarker(hue: Float): IBitmapDescriptor

    fun fromAsset(asset: String): IBitmapDescriptor

    fun fromFile(file: String): IBitmapDescriptor

    fun fromPath(path: String): IBitmapDescriptor

    fun fromResource(resId: Int): IBitmapDescriptor

    fun fromBitmap(bitmap: Bitmap): IBitmapDescriptor

    fun fromView(view: View): IBitmapDescriptor

}
