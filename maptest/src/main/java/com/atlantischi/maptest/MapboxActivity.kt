//package com.atlantischi.maptest
//
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import com.mapbox.mapboxsdk.Mapbox
//import com.mapbox.mapboxsdk.net.ConnectivityReceiver
//
//import kotlinx.android.synthetic.main.activity_mapbox.*
//import kotlin.concurrent.thread
//
//class MapboxActivity : AppCompatActivity() {
//
//    val accessToken = "pk.eyJ1IjoibHgzNjQzMDE3NjYiLCJhIjoiY2pocmV4ZjAyNHlibzMwbmdrb2QzazhkcCJ9.H6Crc1PCIhUML1GZjAz-Ug"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Mapbox.getInstance(this, accessToken)
//        setContentView(R.layout.activity_mapbox)
//        mapView.onCreate(savedInstanceState)
//
////        thread {
////            ConnectivityReceiver.instance(this).activate()
////        }
//
//        val sss= accessToken.substring(2) ?: return
//    }
//
//    override fun onStart() {
//        super.onStart()
//        mapView.onStart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mapView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mapView.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mapView.onStop()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView.onLowMemory()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView.onDestroy()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        mapView.onSaveInstanceState(outState)
//    }
//
//}
