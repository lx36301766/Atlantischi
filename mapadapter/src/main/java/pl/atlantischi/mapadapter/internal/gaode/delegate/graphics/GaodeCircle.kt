package pl.atlantischi.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.Circle
import com.amap.api.maps.model.CircleOptions
import pl.atlantischi.mapadapter.callback.graphics.ICircle

/**
 * Created on 19/06/2018.

 * @author lx
 */
class GaodeCircle(private val circle: Circle) : ICircle {

    class Options : ICircle.Options {

        val options = CircleOptions()

    }

}