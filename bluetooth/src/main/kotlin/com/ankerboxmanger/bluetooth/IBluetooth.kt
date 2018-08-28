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

interface IBluetoothLe : IBluetooth {

    val isSupportLe: Boolean

    interface StateChangedListener {
        fun onChanged(deviceId: String, state: Int)
    }

    interface CharacteristicValueChangedListener {
        fun onChanged(deviceId: String, serviceId: String, characteristicId: String, value: String)
    }

    //    void startLeDevicesDiscovery(String[] serviceUUIDs, AsyncReturnCallback returnCallback);
    //
    //    void stopLeDevicesDiscovery(AsyncReturnCallback returnCallback);

    fun connectGatt(context: Context, deviceId: String, returnCallback: IBluetooth.AsyncReturnCallback)

    fun disconnectGatt(context: Context, deviceId: String): Boolean

    fun addLeStateChangedListener(listener: StateChangedListener): Boolean

    fun removeLeStateChangedListener(listener: StateChangedListener): Boolean

    fun getServices(context: Context, deviceId: String): List<AnkerBoxBluetoothLeService>?

    fun getDeviceCharacteristics(context: Context, deviceId: String,
                                 serviceId: String): List<AnkerBoxBluetoothGattCharacteristic>?

    fun readCharacteristicValue(context: Context, deviceId: String, serviceId: String, characteristicId: String): Boolean

    fun writeCharacteristicValue(context: Context, deviceId: String, serviceId: String, characteristicId: String,
                                 value: String): Boolean

    fun notifyCharacteristicValueChange(context: Context, deviceId: String, serviceId: String, characteristicId: String,
                                        state: Int): Boolean

    fun addCharacteristicValueChangedListener(listener: CharacteristicValueChangedListener): Boolean

    fun removeCharacteristicValueChangedListener(listener: CharacteristicValueChangedListener): Boolean

}
