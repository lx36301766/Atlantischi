package com.ankerboxmanger.bluetooth.impl

import android.annotation.TargetApi
import com.ankerboxmanger.bluetooth.IBluetooth.Companion.TAG

import java.util.HashSet

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.os.Build
import android.util.Base64
import android.util.Log
import com.ankerboxmanger.bluetooth.IBluetooth
import com.ankerboxmanger.bluetooth.IBluetoothLe

/**
 * Created on 15/05/2018.
 *
 * @author lx
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class GattCallbackImpl internal constructor() : BluetoothGattCallback() {

    private var mBluetoothGatt: BluetoothGatt? = null
    private var connectCallback: IBluetooth.AsyncReturnCallback? = null

    private val mStateChangedListeners: MutableSet<IBluetoothLe.StateChangedListener>
            by lazy { HashSet<IBluetoothLe.StateChangedListener>() }
    private val mCharacteristicValueChangedListeners: MutableSet<IBluetoothLe.CharacteristicValueChangedListener>
            by lazy { HashSet<IBluetoothLe.CharacteristicValueChangedListener>() }

    fun addLeStateChangedListener(listener: IBluetoothLe.StateChangedListener): Boolean {
        return mStateChangedListeners.add(listener)
    }

    fun removeLeStateChangedListener(listener: IBluetoothLe.StateChangedListener): Boolean {
        return mStateChangedListeners.remove(listener)
    }

    fun addCharacteristicValueChangedListener(listener: IBluetoothLe.CharacteristicValueChangedListener): Boolean {
        return mCharacteristicValueChangedListeners.add(listener)
    }

    fun removeCharacteristicValueChangedListener(listener: IBluetoothLe.CharacteristicValueChangedListener): Boolean {
        return mCharacteristicValueChangedListeners.remove(listener)
    }

    fun setGattConnect(bluetoothGatt: BluetoothGatt, connectCallback: IBluetooth.AsyncReturnCallback) {
        this.mBluetoothGatt = bluetoothGatt
        this.connectCallback = connectCallback
    }

    fun destroy() {
        mStateChangedListeners.clear()
        mCharacteristicValueChangedListeners.clear()
        mBluetoothGatt = null
        connectCallback = null
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        Log.d(TAG, "onConnectionStateChange, status=$status, newState=$newState")
        if (newState == BluetoothGatt.STATE_CONNECTED) {
            gatt.discoverServices()
        }
        for (listener in mStateChangedListeners) {
            listener.onChanged(gatt.device.address, newState)
        }
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
        super.onCharacteristicChanged(gatt, characteristic)
        val deviceId = gatt.device.address
        val serviceId = characteristic.service.uuid
        val characteristicId = characteristic.uuid
        val value = Base64.encodeToString(characteristic.value, Base64.DEFAULT).replace("\n", "")
        Log.d(TAG, "onCharacteristicChanged, deviceId=$deviceId, serviceId=$serviceId, characteristicId=$characteristicId, value=$value")
        for (listener in mCharacteristicValueChangedListeners) {
            listener.onChanged(deviceId, serviceId.toString(), characteristicId.toString(), value)
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        super.onServicesDiscovered(gatt, status)
        val result = mBluetoothGatt != null
        Log.d(TAG, "result=$result")
        connectCallback?.onReturn(result)
        connectCallback = null
        Log.d(TAG, "onServicesDiscovered, status=$status , deviceId=${gatt.device.address}")
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
        super.onCharacteristicRead(gatt, characteristic, status)
        Log.d(TAG, "onCharacteristicRead, status=$status")// + ", deviceId=" + gatt.getDevice().getAddress());
    }

    override fun onCharacteristicWrite(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
        super.onCharacteristicWrite(gatt, characteristic, status)
        Log.d(TAG, "onCharacteristicWrite, status=$status")// + ", deviceId=" + gatt.getDevice().getAddress());
    }

    override fun onDescriptorRead(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) {
        super.onDescriptorRead(gatt, descriptor, status)
        Log.d(TAG, "onDescriptorRead, status=$status")//+ ", deviceId=" + gatt.getDevice().getAddress());
    }

    override fun onDescriptorWrite(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) {
        super.onDescriptorWrite(gatt, descriptor, status)
        Log.d(TAG, "onDescriptorWrite, status=$status")// + ", deviceId=" + gatt.getDevice().getAddress());
    }

    override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
        super.onMtuChanged(gatt, mtu, status)
        Log.d(TAG, "onMtuChanged, status=$status, mtu=$mtu")// + ", deviceId=" + gatt.getDevice().getAddress());
    }

    override fun onReadRemoteRssi(gatt: BluetoothGatt, rssi: Int, status: Int) {
        super.onReadRemoteRssi(gatt, rssi, status)
        Log.d(TAG, "onReadRemoteRssi, status=$status, rssi=$rssi")// + ", deviceId=" + gatt.getDevice().getAddress());
    }

    override fun onReliableWriteCompleted(gatt: BluetoothGatt, status: Int) {
        super.onReliableWriteCompleted(gatt, status)
        Log.d(TAG, "onReliableWriteCompleted, status=$status")//+ ", deviceId=" + gatt.getDevice().getAddress());
    }

}
