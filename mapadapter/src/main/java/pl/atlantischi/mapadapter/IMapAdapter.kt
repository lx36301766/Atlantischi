package pl.atlantischi.mapadapter

import android.view.ViewStub
import pl.atlantischi.mapadapter.callback.*

/**
 * Created on 05/06/2018.

 * @author lx
 */

interface IMapAdapter {

    val objectFactory: IObjectFactory

    fun setMapViewStub(viewStub: ViewStub)

    fun addMarker(markerOptions: IMarkerOptions): IMarker

    fun setOnMarkerClickListener(onMarkerClickListener: (marker: IMarker) -> Boolean)

    fun setOnMapClickListener(onMapClickListener: (latlng: ILatLng) -> Unit)

    fun setOnMapLongClickListener(onMapLongClickListener: (latlng: ILatLng) -> Unit)

    fun setOnCameraChangeListener(onCameraChangeListener: (cameraPosition: ICameraPosition, isFinished: Boolean) -> Unit)

}
