package com.jm.jiedian.mapadapter.internal.gaode.delegate

import android.graphics.Point
import com.amap.api.maps.CameraUpdateFactory
import com.jm.jiedian.mapadapter.mapapi.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

internal class GaodeCameraUpdateFactory : ICameraUpdateFactory {

    override fun zoomIn(): ICameraUpdate {
        return GaodeCameraUpdate(CameraUpdateFactory.zoomIn())
    }

    override fun zoomOut(): ICameraUpdate {
        return GaodeCameraUpdate(CameraUpdateFactory.zoomOut())
    }

    override fun scrollBy(xPixel: Float, yPixel: Float): ICameraUpdate {
        return GaodeCameraUpdate(CameraUpdateFactory.scrollBy(xPixel, yPixel))
    }

    override fun zoomTo(zoom: Float): ICameraUpdate {
        return GaodeCameraUpdate(CameraUpdateFactory.zoomTo(zoom))
    }

    override fun zoomBy(amount: Float): ICameraUpdate {
        return GaodeCameraUpdate(CameraUpdateFactory.zoomBy(amount))
    }

    override fun zoomBy(amount: Float, focus: Point): ICameraUpdate {
        return GaodeCameraUpdate(CameraUpdateFactory.zoomBy(amount, focus))
    }

    override fun newCameraPosition(cameraPosition: ICameraPosition): ICameraUpdate {
        val gcp = cameraPosition as GaodeCameraPosition
        return GaodeCameraUpdate(CameraUpdateFactory.newCameraPosition(gcp.cameraPosition))
    }

    override fun newLatLng(latLng: ILatLng): ICameraUpdate {
        val gll = latLng as GaodeLatLng
        return GaodeCameraUpdate(CameraUpdateFactory.newLatLng(gll.latlng))
    }

    override fun newLatLngZoom(latLng: ILatLng, zoom: Float): ICameraUpdate {
        val gll = latLng as GaodeLatLng
        return GaodeCameraUpdate(CameraUpdateFactory.newLatLngZoom(gll.latlng, zoom))
    }

    override fun newLatLngBounds(latLngBounds: ILatLngBounds, padding: Int): ICameraUpdate {
        val gllb = latLngBounds as GaodeLatLngBounds
        return GaodeCameraUpdate(CameraUpdateFactory.newLatLngBounds(gllb.latLngBounds, padding))
    }

    override fun newLatLngBounds(latLngBounds: ILatLngBounds, width: Int, height: Int, padding: Int): ICameraUpdate {
        val gllb = latLngBounds as GaodeLatLngBounds
        return GaodeCameraUpdate(CameraUpdateFactory.newLatLngBounds(gllb.latLngBounds, width, height, padding))
    }

}
