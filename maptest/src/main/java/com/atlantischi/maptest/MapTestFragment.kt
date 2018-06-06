package com.atlantischi.maptest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.atlantischi.mapadapter.MapAdapter


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

    private lateinit var mapView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapAdapter.instance.initialize(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_google_maps, container, false)
        mapView = root.findViewById(R.id.g_map)
        return root
    }

}

