package pl.atlantischi.mapadapter

import android.view.ViewStub
import pl.atlantischi.mapadapter.params.MarkerOptionsParameters
import pl.atlantischi.mapadapter.callback.*

/**
 * Created on 05/06/2018.

 * @author lx
 */

interface IMapAdapter {

    fun setMapViewStub(viewStub: ViewStub)

    fun getUISetting(): IUISettings

    fun getBitmapDescriptorFactory(): IBitmapDescriptorFactory

    fun addMarker(markerOptionsParameters: MarkerOptionsParameters): IMarker

    fun setOnMarkerClickListener(onMarkerClickListener: (marker: IMarker) -> Boolean)

    fun newLatLngBoundBuiler(): ILatLngBounds.Builder

    fun setOnMapClickListener(onMapClickListener: (latlng: ILatLng) -> Unit)

    fun setOnMapLongClickListener(onMapLongClickListener: (latlng: ILatLng) -> Unit)

    fun setOnCameraChangeListener(onCameraChangeListener: (cameraPosition: ICameraPosition, isFinished: Boolean) -> Unit)

}
