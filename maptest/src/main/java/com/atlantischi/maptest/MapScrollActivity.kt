package com.atlantischi.maptest

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import com.amap.api.maps.TextureMapView

/**
 * Created on 2019-07-10.

 * @author lx
 */

class MapScrollActivity : Activity() {

    var count = 0

    val mapView by lazy{ findViewById<TextureMapView>(R.id.map_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_map_scroll)

        val button = findViewById<Button>(R.id.btn)
        button.setOnClickListener{ btn ->
            count = 0
            btn.post(object: Runnable {
                override fun run() {
//                    btn.offsetLeftAndRight(1)
                    btn.offsetTopAndBottom(20)
                    mapView.offsetTopAndBottom(20)
                    count++
                    if (count < 15) {
                        btn.post(this)
                    }
                }
            })
        }
        mapView.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

}
