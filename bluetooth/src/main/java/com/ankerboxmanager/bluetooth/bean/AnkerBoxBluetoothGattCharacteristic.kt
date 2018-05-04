package com.ankerboxmanager.bluetooth.bean

import java.util.UUID

/**
 * Created on 21/03/2018.
 *
 * @author lx
 */

class AnkerBoxBluetoothGattCharacteristic(var uuid: UUID, var characteristic: Characteristic) {

    class Characteristic(var read: Boolean, var write: Boolean, var notify: Boolean, var indicate: Boolean)

}
