package pl.atlantischi.mapadapter.gaode

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.ViewStub
import com.amap.api.maps.AMap
import com.amap.api.maps.TextureMapView
import pl.atlantischi.mapadapter.IMapAdapter
import pl.atlantischi.mapadapter.IUISettings
import pl.atlantischi.mapadapter.R

/**
 * Created on 05/06/2018.

 * @author lx
 */


internal class GaodeMapAdapter: IMapAdapter {

    private lateinit var mapView: TextureMapView

    private lateinit var aMap: AMap

    private lateinit var uiSetting: IUISettings

    private var mapViewLifecycleDelegateImpl: GaodeMapViewLifecycleDelegateImpl

    constructor(activity: Activity) {
        mapViewLifecycleDelegateImpl = GaodeMapViewLifecycleDelegateImpl(activity, mapViewFoundCallback)
    }

    constructor(fragment: Fragment) {
        mapViewLifecycleDelegateImpl = GaodeMapViewLifecycleDelegateImpl(fragment, mapViewFoundCallback)
    }

    private val mapViewFoundCallback: (TextureMapView) -> Unit = {
        mapView = it
        aMap = mapView.map
        uiSetting = GaodeUISetting(aMap.uiSettings)
    }

    override fun setMapViewStub(viewStub: ViewStub) {
        viewStub.layoutResource = R.layout.view_gaode_map
        viewStub.inflate()
    }

    override fun getUISetting(): IUISettings {
        return uiSetting
    }

}
