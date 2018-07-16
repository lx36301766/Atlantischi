package com.jm.jiedian.mapadapter.internal.gaode.priv.route

import com.amap.api.services.route.WalkPath
import com.amap.api.services.route.WalkStep
import com.jm.jiedian.mapadapter.separate.gaode.route.IGaodeWalkPath
import com.jm.jiedian.mapadapter.separate.gaode.route.IGaodeWalkStep

/**
 * Created on 22/06/2018.

 * @author lx
 */

internal class GaodeWalkPath(var path: WalkPath) : IGaodeWalkPath {

    override var distance: Float
        get() = path.distance
        set(value) {
            path.distance = value
        }

    override var duration: Long
        get() = path.duration
        set(value) {
            path.duration = value
        }

    override var steps: List<IGaodeWalkStep>
        get() {
            val list = mutableListOf<IGaodeWalkStep>()
            path.steps.forEach { list.add(GaodeWalkStep(it)) }
            return list
        }
        set(value) {
            val list = mutableListOf<WalkStep>()
            value.forEach {
                val path = it as GaodeWalkStep
                list.add(path.step)
            }
            path.steps = list
        }

}
