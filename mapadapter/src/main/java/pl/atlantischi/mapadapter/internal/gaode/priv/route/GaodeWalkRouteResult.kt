package pl.atlantischi.mapadapter.internal.gaode.priv.route

import com.amap.api.services.route.WalkPath
import pl.atlantischi.mapadapter.separate.gaode.route.IGaodeWalkRouteResult
import com.amap.api.services.route.WalkRouteResult
import pl.atlantischi.mapadapter.internal.gaode.priv.GaodeLatLonPoint
import pl.atlantischi.mapadapter.separate.gaode.IGaodeLatLonPoint
import pl.atlantischi.mapadapter.separate.gaode.route.IGaodeWalkPath

/**
 * Created on 22/06/2018.

 * @author lx
 */

internal class GaodeWalkRouteResult(var result: WalkRouteResult) : IGaodeWalkRouteResult {

    override var paths: List<IGaodeWalkPath>
        get() {
            val list = mutableListOf<IGaodeWalkPath>()
            result.paths.forEach { list.add(GaodeWalkPath(it)) }
            return list
        }
        set(value) {
            val list = mutableListOf<WalkPath>()
            value.forEach {
                val path = it as GaodeWalkPath
                list.add(path.path)
            }
            result.paths = list
        }

    override var startPos: IGaodeLatLonPoint
        get() = GaodeLatLonPoint(result.startPos)
        set(value) {
            val gllp = value as GaodeLatLonPoint
            result.startPos = gllp.latLonPoint
        }

    override var targetPos: IGaodeLatLonPoint
        get() = GaodeLatLonPoint(result.targetPos)
        set(value) {
            val gllp = value as GaodeLatLonPoint
            result.targetPos = gllp.latLonPoint
        }

}
