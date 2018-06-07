package pl.atlantischi.mapadapter

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View

/**
 * Created on 07/06/2018.

 * @author lx
 */
internal abstract class MapViewLifecycleDelegateBase :
        FragmentManager.FragmentLifecycleCallbacks, Application.ActivityLifecycleCallbacks {

    private lateinit var activity: Activity

    private lateinit var fragment: Fragment

    constructor(a: Activity) {
        activity = a
        activity.application.registerActivityLifecycleCallbacks(this)
        registerLowMemoryCallback(activity)
    }

    constructor(f: Fragment) {
        fragment = f
        fragment.fragmentManager.registerFragmentLifecycleCallbacks(this, false)
        registerLowMemoryCallback(fragment.activity)
    }

    private fun registerLowMemoryCallback(activity: Activity) {
        activity.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {
                this@MapViewLifecycleDelegateBase.onLowMemory()
            }

            override fun onConfigurationChanged(newConfig: Configuration) {
            }
        })
    }


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


    override fun onFragmentViewCreated(fm: FragmentManager?, f: Fragment?, v: View?, savedInstanceState: Bundle?) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
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


    open fun onCreate(savedInstanceState: Bundle?) {
    }

    open fun onStart() {
    }

    open fun onResume() {
    }

    open fun onPause() {
    }

    open fun onStop() {
    }

    open fun onDestroy() {
    }

    open fun onSaveInstanceState(outState: Bundle?) {
    }

    open fun onLowMemory() {
    }


}