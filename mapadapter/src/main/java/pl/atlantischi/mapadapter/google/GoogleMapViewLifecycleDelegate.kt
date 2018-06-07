package pl.atlantischi.mapadapter.google

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.google.android.gms.maps.MapView
import pl.atlantischi.mapadapter.R

/**
 * Created on 07/06/2018.

 * @author lx
 */
class GoogleMapViewLifecycleDelegate {

    lateinit var mapView: MapView

    fun initialize(activity: Activity, onMapViewFound:(MapView) -> Unit) {
        val app = activity.application
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(a: Activity, savedInstanceState: Bundle?) {
                if (activity == a) onCreate(savedInstanceState)
            }
            override fun onActivityStarted(a: Activity) {
                if (activity == a) onStart()
                mapView = a.findViewById(R.id.google_map_view) as MapView
                onMapViewFound.invoke(mapView)
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
        registerLowMemoryCallback(activity)
    }

    fun initialize(fragment: Fragment, onMapViewFound:(MapView) -> Unit) {
        fragment.fragmentManager.registerFragmentLifecycleCallbacks(object: FragmentManager.FragmentLifecycleCallbacks(){

            override fun onFragmentViewCreated(fm: FragmentManager?, f: Fragment?, v: View?, savedInstanceState: Bundle?) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                mapView = v?.findViewById(R.id.google_map_view) as MapView
                onMapViewFound.invoke(mapView)
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
        registerLowMemoryCallback(fragment.activity)
    }

    private fun onCreate(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
    }

    private fun onStart() {
        mapView.onStart()
    }

    private fun onResume() {
        mapView.onResume()
    }

    private fun onPause() {
        mapView.onPause()
    }

    private fun onStop() {
        mapView.onStop()
    }

    private fun onDestroy() {
        mapView.onDestroy()
    }

    private fun onSaveInstanceState(outState: Bundle?) {
        mapView.onSaveInstanceState(outState)
    }

    private fun onLowMemory() {
        mapView.onLowMemory()
    }

    private fun registerLowMemoryCallback(activity: Activity) {
        activity.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {
                this@GoogleMapViewLifecycleDelegate.onLowMemory()
            }
            override fun onConfigurationChanged(newConfig: Configuration) {
            }
        })
    }


}