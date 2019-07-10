package com.jm.jiedian.mapadapter.priv.gaode

import com.jm.jiedian.mapadapter.common.IBitmapDescriptor
import com.jm.jiedian.mapadapter.common.graphics.IPolyline

/**
 * Created on 20/06/2018.

 * @author lx
 */

interface IGaodePolyline : IPolyline {

    interface Options : IPolyline.Options {

        fun useGradient(use: Boolean): IPolyline.Options

        fun colorValues(colors: List<Int>): IPolyline.Options

        fun transparency(value: Float): IPolyline.Options

        fun setCustomTextureList(allBitmap: List<IBitmapDescriptor>): IPolyline.Options

        fun setCustomTextureIndex(textureIndexs: List<Int>): IPolyline.Options

        fun setUseTexture(use: Boolean): IPolyline.Options

    }

}
