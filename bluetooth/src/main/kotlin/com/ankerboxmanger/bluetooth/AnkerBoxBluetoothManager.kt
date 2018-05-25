package com.ankerboxmanger.bluetooth

import android.content.Context
import android.util.Log
import com.ankerboxmanger.bluetooth.impl.AnkerBoxBluetoothLe

import com.ankerboxmanger.bluetooth.IBluetooth.Companion.TAG

/**
 * Created on 23/05/2018.

 * @author lx
 */

class AnkerBoxBluetoothManager {

    private object SingletonHolder {
        val holder = AnkerBoxBluetoothManager()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    val bluetoothLe: IBluetoothLe by lazy { AnkerBoxBluetoothLe() }

    fun init(context: Context) {
        Log.d(TAG, "AnkerBoxBluetoothManager, init")
        bluetoothLe.init(context)
    }

    fun destroy(context: Context) {
        Log.d(TAG, "AnkerBoxBluetoothManager, destroy")
        bluetoothLe.stopDevicesDiscovery(null)
        bluetoothLe.destroy(context.applicationContext)
    }

}
