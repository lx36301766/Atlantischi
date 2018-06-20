package pl.atlantischi.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.Polygon
import com.amap.api.maps.model.PolygonOptions
import pl.atlantischi.mapadapter.common_api.graphics.IPolygon

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodePolygon(private val polygon: Polygon) : IPolygon {


    internal class Options : IPolygon.Options {

        val options = PolygonOptions()

    }

}
