package com.jm.gaodemap.priv

import com.amap.api.maps.model.animation.ScaleAnimation
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeScaleAnimation

/**
 * Created on 20/06/2018.

 * @author lx
 */

internal class GaodeScaleAnimation(scaleAnimation: ScaleAnimation) : GaodeAnimation(scaleAnimation), IGaodeScaleAnimation