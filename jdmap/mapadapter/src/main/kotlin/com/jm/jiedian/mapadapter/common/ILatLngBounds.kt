package com.jm.jiedian.mapadapter.common

/**
 * Created on 14/06/2018.

 * @author lx
 */

interface ILatLngBounds {

    fun contains(latlng: ILatLng): Boolean

    fun including(latlng: ILatLng): ILatLngBounds

    interface Builder {

        fun include(latlng: ILatLng): Builder

        fun build(): ILatLngBounds

    }

}
