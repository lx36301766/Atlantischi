package com.ankerboxmanger.bluetooth.scaner

import java.util.HashSet

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import com.ankerboxmanger.bluetooth.IBluetooth
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothDevice

import com.ankerboxmanger.bluetooth.IBluetooth.Companion.TAG

/**
 * Created on 14/05/2018.
 *
 * @author lx
 */
abstract class BluetoothScanner(protected var context: Context) {

    private val deviceFoundListeners: MutableSet<IBluetooth.DeviceFoundListener>
            by lazy(LazyThreadSafetyMode.NONE) { HashSet<IBluetooth.DeviceFoundListener>() }

    val foundedBluetoothDevices: MutableSet<AnkerBoxBluetoothDevice> by lazy { HashSet<AnkerBoxBluetoothDevice>() }

    abstract fun startScan(serviceUUIDs: Array<String>?, asyncReturnCallback: IBluetooth.AsyncReturnCallback?)

    abstract fun stopScan(asyncReturnCallback: IBluetooth.AsyncReturnCallback?)

    fun addDeviceFoundListener(listener: IBluetooth.DeviceFoundListener): Boolean {
        return deviceFoundListeners.add(listener)
    }

    fun removeDeviceFoundListener(listener: IBluetooth.DeviceFoundListener): Boolean {
        return deviceFoundListeners.remove(listener)
    }

    open fun destroy() {
        deviceFoundListeners.clear()
        foundedBluetoothDevices.clear()
    }

    internal fun onNewDeviceFound(bondedDevice: BluetoothDevice) {
        val abDevice = buildDevice(bondedDevice)
        foundedBluetoothDevices.add(abDevice)
        for (listener in deviceFoundListeners) {
            listener.onNewDeviceFound(abDevice)
        }
        Log.d(TAG, this.javaClass.simpleName + ", onNewDeviceFound, abDevice=" + abDevice)
    }

    fun buildDevice(bondedDevice: BluetoothDevice): AnkerBoxBluetoothDevice {
        val device = AnkerBoxBluetoothDevice(bondedDevice.name, bondedDevice.address)
        return device
    }

}

