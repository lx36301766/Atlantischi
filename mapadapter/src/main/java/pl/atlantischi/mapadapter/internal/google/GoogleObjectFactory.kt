package pl.atlantischi.mapadapter.internal.google

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import pl.atlantischi.mapadapter.IObjectFactory
import pl.atlantischi.mapadapter.callback.*
import pl.atlantischi.mapadapter.internal.google.delegate.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

class GoogleObjectFactory(googleMap: GoogleMap) : IObjectFactory {

    override val uiSetting: IUISettings = GoogleUISetting(googleMap.uiSettings)

    override val bitmapDescriptorFactory: IBitmapDescriptorFactory = GoogleBitmapDescriptorFactory()

    override val cameraUpdateFactory: ICameraUpdateFactory = GoogleCameraUpdateFactory()

    override val cameraPosition: ICameraPosition = GoogleCameraPosition(googleMap.cameraPosition)

    override fun newLatlng(latitude: Double, longitude: Double): ILatLng {
        return GoogleLatLng(LatLng(latitude, longitude))
    }

    override fun newLatLngBoundBuilder(): ILatLngBounds.Builder {
        return GoogleLatLngBounds.Builder(LatLngBounds.Builder())
    }

    override fun newMarkerOptions(): IMarkerOptions {
        return GoogleMarkerOptions()
    }

}
