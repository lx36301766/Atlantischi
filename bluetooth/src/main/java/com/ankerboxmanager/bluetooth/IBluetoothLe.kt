package com.ankerboxmanager.bluetooth

import java.util.UUID

import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothGattCharacteristic
import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothLeService

import android.content.Context

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
        fun onChanged(deviceId: String, serviceId: UUID, characteristicId: UUID, value: ByteArray)
    }

    fun createGattConnection(context: Context, deviceId: String, returnCallback: IBluetooth.AsyncReturnCallback?)

    fun closeLeConnection(context: Context, deviceId: String): Boolean

    fun addLeStateChangedListener(listener: StateChangedListener): Boolean

    fun removeLeStateChangedListener(listener: StateChangedListener): Boolean

    fun getServices(context: Context, deviceId: String): List<AnkerBoxBluetoothLeService>?

    fun getDeviceCharacteristics(context: Context, deviceId: String,
                                 serviceId: UUID): List<AnkerBoxBluetoothGattCharacteristic>?

    fun readCharacteristicValue(context: Context, deviceId: String, serviceId: UUID, characteristicId: UUID): Boolean

    fun writeCharacteristicValue(context: Context, deviceId: String, serviceId: UUID, characteristicId: UUID,
                                 value: String): Boolean

    fun notifyCharacteristicValueChange(context: Context, deviceId: String, serviceId: UUID, characteristicId: UUID,
                                        state: Int): Boolean

    fun addCharacteristicValueChangedListener(listener: CharacteristicValueChangedListener): Boolean

    fun removeCharacteristicValueChangedListener(listener: CharacteristicValueChangedListener): Boolean
}
