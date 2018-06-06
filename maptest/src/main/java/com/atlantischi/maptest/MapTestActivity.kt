package com.atlantischi.maptest

import android.content.Context
import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.LayoutInflaterFactory
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.MapView

/**
 * Created on 05/06/2018.

 * @author lx
 */

class MapTestActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val factory = LayoutInflaterFactory { parent, name, context, attrs ->
            if ("pl.atlantischi.mapadapter.MapView" == name) {
                return@LayoutInflaterFactory com.google.android.gms.maps.MapView(context, attrs)
            }
            if (context is AppCompatActivity) {
                val appCompatActivity = this as AppCompatActivity
                return@LayoutInflaterFactory appCompatActivity.delegate.createView(parent, name, context, attrs)
            }
            return@LayoutInflaterFactory null
        }
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), factory)

//        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), object: LayoutInflater.Factory2 {
//
//            override fun onCreateView(parent: View, name: String, context: Context, attrs: AttributeSet): View? {
//                if ("pl.atlantischi.mapadapter.MapView" == name) {
//                    return MapView(context, attrs)
//                }
//                if (context is AppCompatActivity) {
//                    val appCompatActivity = this as AppCompatActivity
//                    return appCompatActivity.delegate.createView(parent, name, context, attrs)
//                }
//                return null
//            }
//
//            override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//                return null
//            }
//        })
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_test)

        val fragment = MapTestFragment.newInstance()
        addFragment(fragment, R.id.fragment_container)
    }

}
