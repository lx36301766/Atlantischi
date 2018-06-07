package pl.atlantischi.mapadapter.gaode

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.ViewStub
import com.amap.api.maps.AMap
import com.amap.api.maps.TextureMapView
import pl.atlantischi.mapadapter.IMapAdapter
import pl.atlantischi.mapadapter.R

/**
 * Created on 05/06/2018.

 * @author lx
 */


class GaodeMapAdapter: IMapAdapter {

    private lateinit var mapView: TextureMapView

    private lateinit var aMap: AMap

    override fun initialize(activity: Activity) {
        val mapViewLifecycleDelegate = GaodeMapViewLifecycleDelegate()
        mapViewLifecycleDelegate.initialize(activity, {
            mapView = it
            aMap = mapView.map
        })
    }

    override fun initialize(fragment: Fragment) {
        val mapViewLifecycleDelegate = GaodeMapViewLifecycleDelegate()
        mapViewLifecycleDelegate.initialize(fragment, {
            mapView = it
            aMap = mapView.map
        })
    }

    override fun setMapViewStub(viewStub: ViewStub) {
        viewStub.layoutResource = R.layout.view_gaode_map
        viewStub.inflate()
    }

}
