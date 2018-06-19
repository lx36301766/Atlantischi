package pl.atlantischi.mapadapter.internal.google.delegate.graphics

import com.google.android.gms.maps.model.TileOverlay
import com.google.android.gms.maps.model.TileOverlayOptions
import pl.atlantischi.mapadapter.callback.graphics.ITileOverlay

/**
 * Created on 19/06/2018.

 * @author lx
 */
class GoogleTileOverlay(private val tileOverlay: TileOverlay) : ITileOverlay {

    class Options : ITileOverlay.Options {

        val options = TileOverlayOptions()

    }

}