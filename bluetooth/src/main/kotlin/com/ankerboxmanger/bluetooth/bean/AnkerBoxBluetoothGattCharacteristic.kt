package com.ankerboxmanger.bluetooth.bean

import java.util.UUID

import com.alibaba.fastjson.annotation.JSONField

/**
 * Created on 21/03/2018.
 *
 * @author lx
 */


data class AnkerBoxBluetoothGattCharacteristic(val uuid: UUID,
                                                @JSONField(name = "properties") val characteristic: Characteristic) {

    data class Characteristic(var read: Int, var write: Int, var notify: Int, var indicate: Int)

}
