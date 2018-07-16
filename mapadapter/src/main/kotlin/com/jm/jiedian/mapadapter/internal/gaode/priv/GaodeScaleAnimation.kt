package com.jm.jiedian.mapadapter.internal.gaode.priv

import com.amap.api.maps.model.animation.ScaleAnimation
import com.jm.jiedian.mapadapter.separate.gaode.IGaodeScaleAnimation

/**
 * Created on 20/06/2018.

 * @author lx
 */

internal class GaodeScaleAnimation(scaleAnimation: ScaleAnimation) : GaodeAnimation(scaleAnimation), IGaodeScaleAnimation