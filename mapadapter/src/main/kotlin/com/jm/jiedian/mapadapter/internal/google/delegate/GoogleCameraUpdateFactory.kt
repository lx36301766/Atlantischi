package com.jm.jiedian.mapadapter.internal.google.delegate

import android.graphics.Point
import com.google.android.gms.maps.CameraUpdateFactory
import com.jm.jiedian.mapadapter.mapapi.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

internal class GoogleCameraUpdateFactory : ICameraUpdateFactory {

    override fun zoomIn(): ICameraUpdate {
        return GoogleCameraUpdate(CameraUpdateFactory.zoomIn())
    }

    override fun zoomOut(): ICameraUpdate {
        return GoogleCameraUpdate(CameraUpdateFactory.zoomOut())
    }

    override fun scrollBy(xPixel: Float, yPixel: Float): ICameraUpdate {
        return GoogleCameraUpdate(CameraUpdateFactory.scrollBy(xPixel, yPixel))
    }

    override fun zoomTo(zoom: Float): ICameraUpdate {
        return GoogleCameraUpdate(CameraUpdateFactory.zoomTo(zoom))
    }

    override fun zoomBy(amount: Float): ICameraUpdate {
        return GoogleCameraUpdate(CameraUpdateFactory.zoomBy(amount))
    }

    override fun zoomBy(amount: Float, focus: Point): ICameraUpdate {
        return GoogleCameraUpdate(CameraUpdateFactory.zoomBy(amount, focus))
    }

    override fun newCameraPosition(cameraPosition: ICameraPosition): ICameraUpdate {
        val gcp = cameraPosition as GoogleCameraPosition
        return GoogleCameraUpdate(CameraUpdateFactory.newCameraPosition(gcp.cameraPosition))
    }

    override fun newLatLng(latLng: ILatLng): ICameraUpdate {
        val gll = latLng as GoogleLatLng
        return GoogleCameraUpdate(CameraUpdateFactory.newLatLng(gll.latlng))
    }

    override fun newLatLngZoom(latLng: ILatLng, zoom: Float): ICameraUpdate {
        val gll = latLng as GoogleLatLng
        return GoogleCameraUpdate(CameraUpdateFactory.newLatLngZoom(gll.latlng, zoom))
    }

    override fun newLatLngBounds(latLngBounds: ILatLngBounds, padding: Int): ICameraUpdate {
        val gllb = latLngBounds as GoogleLatLngBounds
        return GoogleCameraUpdate(CameraUpdateFactory.newLatLngBounds(gllb.latLngBounds, padding))
    }

    override fun newLatLngBounds(latLngBounds: ILatLngBounds, width: Int, height: Int, padding: Int): ICameraUpdate {
        val gllb = latLngBounds as GoogleLatLngBounds
        return GoogleCameraUpdate(CameraUpdateFactory.newLatLngBounds(gllb.latLngBounds, width, height, padding))
    }

}
