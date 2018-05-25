package com.ankerboxmanger.bluetooth

import android.content.Context
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothGattCharacteristic
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothLeService

/**
 * Created on 21/03/2018.
 *
 * @author lx
 */

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
