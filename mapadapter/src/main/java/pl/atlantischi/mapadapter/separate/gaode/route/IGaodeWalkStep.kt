package pl.atlantischi.mapadapter.separate.gaode.route

import pl.atlantischi.mapadapter.separate.gaode.IGaodeLatLonPoint

/**
 * Created on 22/06/2018.

 * @author lx
 */

interface IGaodeWalkStep {

    var polyline: List<IGaodeLatLonPoint>

}
