package com.jm.jiedian.mapadapter.internal

import com.jm.jiedian.mapadapter.mapapi.ILatLng

/**
 * Created on 19/06/2018.

 * @author lx
 */

class MapUtils {

    companion object {

        @JvmStatic
        fun calculateLineDistance(src: ILatLng, dest: ILatLng): Float {
            return try {
                val var2 = 0.01745329251994329
                var var4 = src.longitude
                var var6 = src.latitude
                var var8 = dest.longitude
                var var10 = dest.latitude
                var4 *= 0.01745329251994329
                var6 *= 0.01745329251994329
                var8 *= 0.01745329251994329
                var10 *= 0.01745329251994329
                val var12 = Math.sin(var4)
                val var14 = Math.sin(var6)
                val var16 = Math.cos(var4)
                val var18 = Math.cos(var6)
                val var20 = Math.sin(var8)
                val var22 = Math.sin(var10)
                val var24 = Math.cos(var8)
                val var26 = Math.cos(var10)
                val var28 = DoubleArray(3)
                val var29 = DoubleArray(3)
                var28[0] = var18 * var16
                var28[1] = var18 * var12
                var28[2] = var14
                var29[0] = var26 * var24
                var29[1] = var26 * var20
                var29[2] = var22
                val var30 = Math.sqrt((var28[0] - var29[0]) * (var28[0] - var29[0]) + (var28[1] - var29[1])
                        * (var28[1] - var29[1]) + (var28[2] - var29[2]) * (var28[2] - var29[2]))
                (Math.asin(var30 / 2.0) * 1.27420015798544E7).toFloat()
            } catch (var32: Throwable) {
                var32.printStackTrace()
                0.0f
            }
        }
    }

}
