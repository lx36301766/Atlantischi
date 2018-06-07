package com.atlantischi.maptest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.atlantischi.mapadapter.MapController
import pl.atlantischi.mapadapter.MapController.Companion.ADAPTER_TYPE_GAODE
import pl.atlantischi.mapadapter.MapController.Companion.ADAPTER_TYPE_GOOGLE


/**
 * Created on 05/06/2018.

 * @author lx
 */

class MapTestFragment: Fragment() {

    companion object {
        fun newInstance(): MapTestFragment {
            return MapTestFragment()
        }
    }

    val mapAdapter by lazy { MapController.instance.defaultAdapter }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapController.initialize(ADAPTER_TYPE_GOOGLE, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_google_maps, container, false)
        mapAdapter.setMapViewStub(root.findViewById(R.id.map_view_stub))
        return root
    }

}
