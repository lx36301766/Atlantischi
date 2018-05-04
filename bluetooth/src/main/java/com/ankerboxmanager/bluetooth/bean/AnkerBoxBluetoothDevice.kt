package com.ankerboxmanager.bluetooth.bean

/**
 * Created on 21/03/2018.
 *
 * @author lx
 */

class AnkerBoxBluetoothDevice(var name: String?, var deviceId: String?) {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is AnkerBoxBluetoothDevice) {
            return false
        }

        val that = o as AnkerBoxBluetoothDevice?

        if (if (name != null) name != that!!.name else that!!.name != null) {
            return false
        }
        return if (deviceId != null) deviceId == that.deviceId else that.deviceId == null
    }

    override fun hashCode(): Int {
        var result = if (name != null) name!!.hashCode() else 0
        result = 31 * result + if (deviceId != null) deviceId!!.hashCode() else 0
        return result
    }

}
