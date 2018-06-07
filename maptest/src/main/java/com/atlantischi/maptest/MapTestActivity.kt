package com.atlantischi.maptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created on 05/06/2018.

 * @author lx
 */

class MapTestActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

//        val factory = LayoutInflaterFactory { parent, name, context, attrs ->
//            if ("pl.atlantischi.mapadapter.MapView" == name) {
//                return@LayoutInflaterFactory com.google.android.gms.maps.MapView(context, attrs)
//            }
//            if (context is AppCompatActivity) {
//                val appCompatActivity = this as AppCompatActivity
//                return@LayoutInflaterFactory appCompatActivity.delegate.createView(parent, name, context, attrs)
//            }
//            return@LayoutInflaterFactory null
//        }
//        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), factory)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_test)

        val fragment = MapTestFragment.newInstance()
        addFragment(fragment, R.id.fragment_container)
    }

}
