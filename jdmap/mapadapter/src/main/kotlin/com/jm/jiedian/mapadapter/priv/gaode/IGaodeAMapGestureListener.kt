package com.jm.jiedian.mapadapter.priv.gaode

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeAMapGestureListener {

    fun onDown(x: Float, y: Float)

    fun onDoubleTap(x: Float, y: Float)

    fun onFling(velocityX: Float, velocityY: Float)

    fun onSingleTap(x: Float, y: Float)

    fun onScroll(distanceX: Float, distanceY: Float)

    fun onMapStable()

    fun onUp(x: Float, y: Float)

    fun onLongPress(x: Float, y: Float)

}
