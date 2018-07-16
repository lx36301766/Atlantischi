package com.jm.jiedian.mapadapter

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
internal abstract class MapViewLifecycleImpl(f: Fragment? = null, a: Activity? = null) :
        FragmentManager.FragmentLifecycleCallbacks(), Application.ActivityLifecycleCallbacks {

    private lateinit var activity: Activity

    private lateinit var fragment: Fragment

    init {
        when {
            f != null -> {
                f.fragmentManager.registerFragmentLifecycleCallbacks(this, false)
                registerLowMemoryCallback(f.activity)
                fragment = f
            }
            a != null -> {
                activity.application.registerActivityLifecycleCallbacks(this)
                registerLowMemoryCallback(activity)
                activity = a
            }
            else -> { IllegalAccessException("fragment or activity at least one is not null") }
        }
    }

    private fun registerLowMemoryCallback(activity: Activity) {
        activity.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {
                this@MapViewLifecycleImpl.onLowMemory()
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

    override fun onFragmentDestroyed(fm: FragmentManager?, f: Fragment?) {
        super.onFragmentDestroyed(fm, f)
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