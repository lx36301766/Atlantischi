package com.ankerboxmanager.bluetooth

import java.util.ArrayList
import java.util.HashSet
import java.util.UUID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothGattCharacteristic
import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothLeService

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.Context
import android.content.pm.PackageManager

/**
 * Created on 21/03/2018.
 *
 * @author lx
 */

class AnkerBoxBluetoothLe : AnkerBoxBluetooth(), IBluetoothLe {

    override var isSupportLe: Boolean = false
        private set

    private var mStateChangedListeners: MutableSet<IBluetoothLe.StateChangedListener>? = null
    private var mCharacteristicValueChangedListeners: MutableSet<IBluetoothLe.CharacteristicValueChangedListener>? = null

    private var mBluetoothGatt: BluetoothGatt? = null

    private val executorService = Executors.newCachedThreadPool()

    private val mBluetoothGattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            for (listener in mStateChangedListeners!!) {
                listener.onChanged(gatt.device.address, newState)
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            super.onCharacteristicChanged(gatt, characteristic)
            val deviceId = gatt.device.name
            val serviceId = characteristic.service.uuid
            val characteristicId = characteristic.uuid
            val value = characteristic.value
            for (listener in mCharacteristicValueChangedListeners!!) {
                listener.onChanged(deviceId, serviceId, characteristicId, value)
            }
        }
    }

    override fun init(context: Context) {
        super.init(context)
        mStateChangedListeners = HashSet()
        mCharacteristicValueChangedListeners = HashSet()
        isSupportLe = isSupportBluetooth && context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    override fun destroy(context: Context) {
        super.destroy(context)
        mStateChangedListeners!!.clear()
        mCharacteristicValueChangedListeners!!.clear()
    }

    override fun createGattConnection(context: Context, deviceId: String, returnCallback: IBluetooth.AsyncReturnCallback?) {
        if (isSupportLe) {
            val bondedDevice = mBluetoothAdapter!!.getRemoteDevice(deviceId)
            mBluetoothGatt = bondedDevice.connectGatt(context, true, mBluetoothGattCallback)
            executorService.submit {
                val result = mBluetoothGatt!!.connect()
                returnCallback?.onAsyncReturn(result)
            }
        }
    }

    override fun closeLeConnection(context: Context, deviceId: String): Boolean {
        if (mBluetoothGatt != null) {
            mBluetoothGatt!!.disconnect()
            return true
        }
        return false
    }

    override fun addLeStateChangedListener(listener: IBluetoothLe.StateChangedListener): Boolean {
        return isSupportLe && mStateChangedListeners!!.add(listener)
    }

    override fun removeLeStateChangedListener(listener: IBluetoothLe.StateChangedListener): Boolean {
        return isSupportLe && mStateChangedListeners!!.remove(listener)
    }

    override fun getServices(context: Context, deviceId: String): List<AnkerBoxBluetoothLeService>? {
        if (mBluetoothGatt != null) {
            val services = ArrayList<AnkerBoxBluetoothLeService>()
            val gattServices = mBluetoothGatt!!.services
            for (gattService in gattServices) {
                val uuid = gattService.uuid
                val isPrimary = gattService.type == BluetoothGattService.SERVICE_TYPE_PRIMARY
                val service = AnkerBoxBluetoothLeService(uuid, isPrimary)
                services.add(service)
            }
            return services
        }
        return null
    }

    override fun getDeviceCharacteristics(context: Context, deviceId: String,
                                          serviceId: UUID): List<AnkerBoxBluetoothGattCharacteristic>? {
        if (mBluetoothGatt != null) {
            val characteristicList = ArrayList<AnkerBoxBluetoothGattCharacteristic>()
            val gattService = mBluetoothGatt!!.getService(serviceId)
            val gattCharacteristics = gattService.characteristics
            for (gattCharacteristic in gattCharacteristics) {
                val properties = gattCharacteristic.properties
                val read = properties and BluetoothGattCharacteristic.PROPERTY_READ > 0
                val write = properties and BluetoothGattCharacteristic.PROPERTY_WRITE > 0 || properties and
                        BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE > 0
                val notify = properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0
                val indicate = properties and BluetoothGattCharacteristic.PROPERTY_INDICATE > 0
                val characteristic = AnkerBoxBluetoothGattCharacteristic.Characteristic(read, write, notify, indicate)
                val abGattCharacteristic = AnkerBoxBluetoothGattCharacteristic(gattCharacteristic.uuid, characteristic)
                characteristicList.add(abGattCharacteristic)
            }
            return characteristicList
        }
        return null
    }

    override fun readCharacteristicValue(context: Context, deviceId: String, serviceId: UUID, characteristicId: UUID): Boolean {
        if (mBluetoothGatt != null) {
            val gattService = mBluetoothGatt!!.getService(serviceId)
            val characteristic = gattService.getCharacteristic(characteristicId)
            mBluetoothGatt!!.readCharacteristic(characteristic)
            return true
        }
        return false
    }

    override fun writeCharacteristicValue(context: Context, deviceId: String, serviceId: UUID, characteristicId: UUID,
                                          value: String): Boolean {
        if (mBluetoothGatt != null) {
            val gattService = mBluetoothGatt!!.getService(serviceId)
            val characteristic = gattService.getCharacteristic(characteristicId)
            characteristic.setValue(value)
            mBluetoothGatt!!.writeCharacteristic(characteristic)
            return true
        }
        return false
    }

    override fun notifyCharacteristicValueChange(context: Context, deviceId: String, serviceId: UUID, characteristicId: UUID,
                                                 state: Int): Boolean {
        if (mBluetoothGatt != null) {
            val gattService = mBluetoothGatt!!.getService(serviceId)
            val characteristic = gattService.getCharacteristic(characteristicId)
            mBluetoothGatt!!.setCharacteristicNotification(characteristic, state == 1)
            return true
        }
        return false
    }

    override fun addCharacteristicValueChangedListener(listener: IBluetoothLe.CharacteristicValueChangedListener): Boolean {
        return isSupportLe && mCharacteristicValueChangedListeners!!.add(listener)
    }

    override fun removeCharacteristicValueChangedListener(listener: IBluetoothLe.CharacteristicValueChangedListener): Boolean {
        return isSupportLe && mCharacteristicValueChangedListeners!!.remove(listener)
    }

}
