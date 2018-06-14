package pl.atlantischi.mapadapter.internal.gaode

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.ViewStub
import com.amap.api.maps.AMap
import com.amap.api.maps.TextureMapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLngBounds
import pl.atlantischi.mapadapter.IMapAdapter
import pl.atlantischi.mapadapter.R
import pl.atlantischi.mapadapter.internal.gaode.delegate.*
import pl.atlantischi.mapadapter.params.MarkerOptionsParameters
import pl.atlantischi.mapadapter.callback.*

/**
 * Created on 05/06/2018.

 * @author lx
 */


internal class GaodeMapAdapter: IMapAdapter {
    private lateinit var mapView: TextureMapView

    private lateinit var aMap: AMap

    private var mapViewLifecycleDelegateImpl: GaodeMapViewLifecycleImpl

    private lateinit var uiSetting: IUISettings

    private lateinit var bitmapDescriptorFactory: IBitmapDescriptorFactory

    constructor(activity: Activity) {
        mapViewLifecycleDelegateImpl = GaodeMapViewLifecycleImpl(activity, mapViewFoundCallback)
    }

    constructor(fragment: Fragment) {
        mapViewLifecycleDelegateImpl = GaodeMapViewLifecycleImpl(fragment, mapViewFoundCallback)
    }

    private val mapViewFoundCallback: (TextureMapView) -> Unit = {
        mapView = it
        aMap = mapView.map
        uiSetting = GaodeUISetting(aMap.uiSettings)
        bitmapDescriptorFactory = GaodeBitmapDescriptorFactory()
    }

    override fun setMapViewStub(viewStub: ViewStub) {
        viewStub.layoutResource = R.layout.view_gaode_map
        viewStub.inflate()
    }

    override fun getUISetting(): IUISettings {
        return uiSetting
    }

    override fun getBitmapDescriptorFactory(): IBitmapDescriptorFactory {
        return bitmapDescriptorFactory
    }

    override fun addMarker(markerOptionsParameters: MarkerOptionsParameters): IMarker {
        return GaodeMarker(aMap.addMarker(GaodeMarkerOptions.build(markerOptionsParameters)))
    }

    override fun setOnMarkerClickListener(onMarkerClickListener: (marker: IMarker) -> Boolean) {
        aMap.setOnMarkerClickListener {
            onMarkerClickListener.invoke(GaodeMarker(it))
        }
    }

    override fun newLatLngBoundBuiler(): ILatLngBounds.Builder {
        return GaodeLatLngBounds.Builder(LatLngBounds.Builder())
    }

    override fun setOnMapClickListener(onMapClickListener: (latlng: ILatLng) -> Unit) {
        aMap.setOnMapClickListener {
            onMapClickListener.invoke(GaodeLatLng(it))
        }
    }

    override fun setOnMapLongClickListener(onMapLongClickListener: (latlng: ILatLng) -> Unit) {
        aMap.setOnMapLongClickListener {
            onMapLongClickListener.invoke(GaodeLatLng(it))
        }
    }

    override fun setOnCameraChangeListener(onCameraChangeListener: (cameraPosition: ICameraPosition, isFinished: Boolean) -> Unit) {
        aMap.setOnCameraChangeListener(object: AMap.OnCameraChangeListener{

            override fun onCameraChange(cameraPosition: CameraPosition) {
                onCameraChangeListener.invoke(GaodeCameraPosition(cameraPosition), false)
            }

            override fun onCameraChangeFinish(cameraPosition: CameraPosition) {
                onCameraChangeListener.invoke(GaodeCameraPosition(cameraPosition),true)
            }

        })
    }

}
