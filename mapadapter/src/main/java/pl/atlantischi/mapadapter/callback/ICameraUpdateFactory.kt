package pl.atlantischi.mapadapter.callback

import android.graphics.Point

/**
 * Created on 14/06/2018.

 * @author lx
 */

interface ICameraUpdateFactory {

    fun zoomIn(): ICameraUpdate

    fun zoomOut(): ICameraUpdate

    fun scrollBy(xPixel: Float, yPixel: Float): ICameraUpdate

    fun zoomTo(zoom: Float): ICameraUpdate

    fun zoomBy(amount: Float): ICameraUpdate

    fun zoomBy(amount: Float, focus: Point): ICameraUpdate

    fun newCameraPosition(cameraPosition: ICameraPosition): ICameraUpdate

    fun newLatLng(latLng: ILatLng): ICameraUpdate

    fun newLatLngZoom(latLng: ILatLng, zoom: Float): ICameraUpdate

    fun newLatLngBounds(latLngBounds: ILatLngBounds, padding: Int): ICameraUpdate

    fun newLatLngBounds(latLngBounds: ILatLngBounds, width: Int, height: Int, padding: Int): ICameraUpdate

}
