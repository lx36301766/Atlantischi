package pl.atlantischi.mapadapter

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.LayoutInflaterFactory
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created on 05/06/2018.
 *
 * @author lx
 */


class GoogleMapAdapter: IMap, OnMapReadyCallback  {

    override fun onMapReady(googleMap: GoogleMap) {
        val cd = LatLng(30.545162, 104.061024)
        googleMap.addMarker(MarkerOptions().position(cd).title("Marker in CD"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cd, 12f))
    }

    lateinit var mapView: MapView

    override fun initialize(activity: Activity) {
        val app = activity.application
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(a: Activity, savedInstanceState: Bundle?) {
                if (activity == a) onCreate(savedInstanceState)
            }
            override fun onActivityStarted(a: Activity) {
                if (activity == a) onStart()
            }
            override fun onActivityPaused(a: Activity) {
                if (activity == a) onPause()
            }
            override fun onActivityResumed(a: Activity) {
                if (activity == a) onResume()
            }
            override fun onActivityStopped(a: Activity) {
                if (activity == a) onStop()
            }
            override fun onActivityDestroyed(a: Activity) {
                if (activity == a) onDestroy()
            }
            override fun onActivitySaveInstanceState(a: Activity, outState: Bundle?) {
                if (activity == a) onSaveInstanceState(outState)
            }

        })
        initializeInner(activity)
    }

    override fun initialize(fragment: Fragment) {
        fragment.fragmentManager.registerFragmentLifecycleCallbacks(object: FragmentManager.FragmentLifecycleCallbacks(){

            override fun onFragmentViewCreated(fm: FragmentManager?, f: Fragment?, v: View?, savedInstanceState: Bundle?) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                mapView = v?.findViewById(R.id.g_map) as MapView
                mapView.getMapAsync(this@GoogleMapAdapter)
                if (fragment == f) onCreate(savedInstanceState)
            }

            override fun onFragmentStarted(fm: FragmentManager?, f: Fragment?) {
                super.onFragmentStarted(fm, f)
                if (fragment == f) onStart()
            }

            override fun onFragmentResumed(fm: FragmentManager?, f: Fragment?) {
                super.onFragmentResumed(fm, f)
                if (fragment == f) onResume()
            }

            override fun onFragmentPaused(fm: FragmentManager?, f: Fragment?) {
                super.onFragmentPaused(fm, f)
                if (fragment == f) onPause()
            }

            override fun onFragmentStopped(fm: FragmentManager?, f: Fragment?) {
                super.onFragmentStopped(fm, f)
                if (fragment == f) onStop()
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager?, f: Fragment?) {
                super.onFragmentViewDestroyed(fm, f)
                if (fragment == f) onDestroy()
            }

            override fun onFragmentSaveInstanceState(fm: FragmentManager?, f: Fragment?, outState: Bundle?) {
                super.onFragmentSaveInstanceState(fm, f, outState)
                if (fragment == f) onSaveInstanceState(outState)
            }

        }, false)
        initializeInner(fragment.activity)
    }

    fun onCreate(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
    }

    fun onStart() {
        mapView.onStart()
    }

    fun onResume() {
        mapView.onResume()
    }

    fun onPause() {
        mapView.onPause()
    }

    fun onStop() {
        mapView.onStop()
    }

    fun onDestroy() {
        mapView.onDestroy()
    }

    fun onSaveInstanceState(outState: Bundle?) {
        mapView.onSaveInstanceState(outState)
    }

    fun onLowMemory() {
        mapView.onLowMemory()
    }

    private fun initializeInner(activity: Activity) {
        activity.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {
                this@GoogleMapAdapter.onLowMemory()
            }
            override fun onConfigurationChanged(newConfig: Configuration) {
            }
        })

    }

}
