package com.ankerboxmanger.bluetooth

import android.app.Activity
import android.content.Context
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothDevice
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothGattCharacteristic
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothLeService

/**
 * Created on 19/03/2018.
 *
 * @author lx
 */

interface IBluetooth {

    companion object {
        val TAG = "AnkerBoxBluetooth"
    }

    interface AsyncReturnCallback {
        fun onReturn(result: Boolean)
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

    fun startDevicesDiscovery(serviceUUIDs: Array<String>, returnCallback: AsyncReturnCallback?)

    fun stopDevicesDiscovery(returnCallback: AsyncReturnCallback?)

    fun registerStateChangedListener(listener: AdapterStateChangedListener): Boolean

    fun unregisterStateChangedListener(listener: AdapterStateChangedListener): Boolean

    fun addDeviceFoundListener(listener: DeviceFoundListener): Boolean

    fun removeDeviceFoundListener(listener: DeviceFoundListener): Boolean

    fun getConnectedDevicesInfo(uuids: Array<String>): Set<AnkerBoxBluetoothDevice>?

}

