package com.jm.jiedian.mapadapter.mapapi.graphics

import com.jm.jiedian.mapadapter.mapapi.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

interface IPolyline {

    fun remove()

    val id: String

    var points: List<ILatLng>

    var width: Float

    var color: Int

    var zIndex: Float

    var isVisible: Boolean

    var isGeodesic: Boolean

    interface Options {

        fun add(latlng: ILatLng): Options

        fun add(vararg latlng: ILatLng): Options

        fun addAll(latlng: Iterable<ILatLng>): Options

        fun width(width: Float): Options

        fun color(color: Int): Options

        fun zIndex(zIndex: Float): Options

        fun visible(visible: Boolean): Options

        fun geodesic(geodesic: Boolean): Options

        fun getPoints(): List<ILatLng>

    }

}
