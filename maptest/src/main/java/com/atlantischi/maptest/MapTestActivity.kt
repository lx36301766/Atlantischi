package com.atlantischi.maptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created on 05/06/2018.

 * @author lx
 */

class MapTestActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_test)

        val fragment = MapTestFragment.newInstance()
        addFragment(fragment, R.id.fragment_container)
    }

}
