package com.jm.jiedian.mapadapter.separate.gaode.route

/**
 * Created on 22/06/2018.

 * @author lx
 */

interface IGaodeWalkPath {

    var distance: Float

    var duration: Long

    var steps: List<IGaodeWalkStep>

}