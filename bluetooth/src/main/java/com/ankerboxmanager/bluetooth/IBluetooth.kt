package com.ankerboxmanager.bluetooth

import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothDevice

import android.app.Activity
import android.content.Context

/**
 * Created on 19/03/2018.
 *
 * @author lx
 */

interface IBluetooth {

    interface AsyncReturnCallback {
        fun onAsyncReturn(result: Boolean)
    }

    interface AdapterStateChangedListener {
        fun onChanged(previousState: Int, newState: Int)
    }

    interface DeviceFoundListener {
        fun onNewDeviceFound(newDevice: AnkerBoxBluetoothDevice)
    }

    val isSupportBluetooth: Boolean

    val isEnabled: Boolean

    val isDiscovering: Boolean

    val adapterState: Int

    val devices: Set<AnkerBoxBluetoothDevice>?

    fun init(context: Context)

    fun destroy(context: Context)

    fun openFriendly(activity: Activity, requestCode: Int): Boolean

    fun open(): Boolean

    fun close(): Boolean

    fun startDevicesDiscovery(): Boolean

    fun stopDevicesDiscovery(): Boolean

    fun registerStateChangedListener(listener: AdapterStateChangedListener): Boolean

    fun unregisterStateChangedListener(listener: AdapterStateChangedListener): Boolean

    fun registerDeviceFoundListener(listener: DeviceFoundListener): Boolean

    fun unregisterDeviceFoundListener(listener: DeviceFoundListener): Boolean

    fun getConnectedDevicesInfo(uuids: Array<String>): Set<AnkerBoxBluetoothDevice>?

}
