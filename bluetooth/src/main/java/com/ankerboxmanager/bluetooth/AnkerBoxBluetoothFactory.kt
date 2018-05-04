package com.ankerboxmanager.bluetooth

/**
 * Created on 21/03/2018.
 *
 * @author lx
 */

object AnkerBoxBluetoothFactory {

    fun newBluetooth(): IBluetooth {
        return AnkerBoxBluetooth()
    }

    fun newBluetoothLE(): IBluetoothLe {
        return AnkerBoxBluetoothLe()
    }

}
