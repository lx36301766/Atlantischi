package com.ankerboxmanager.bluetooth

import android.content.Context

/**
 * Created on 19/03/2018.
 *
 * @author lx
 */

class AnkerBoxBluetoothManager {

    var bluetoothLe: IBluetoothLe? = null
        private set

    private object InstanceHolder {
        internal val instance = AnkerBoxBluetoothManager()
    }

    fun init(context: Context) {
        bluetoothLe = AnkerBoxBluetoothFactory.newBluetoothLE()
        bluetoothLe!!.init(context)
    }

    fun destory(context: Context) {
        bluetoothLe!!.destroy(context)
    }

    companion object {
        val instance: AnkerBoxBluetoothManager
            get() = InstanceHolder.instance
    }

}
