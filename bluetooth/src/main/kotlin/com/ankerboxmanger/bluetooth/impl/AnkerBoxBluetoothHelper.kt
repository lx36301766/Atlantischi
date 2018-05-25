package com.ankerboxmanger.bluetooth.impl

import android.annotation.TargetApi
import java.util.UUID

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.os.Build
import android.util.Log
import com.ankerboxmanger.bluetooth.IBluetooth.Companion.TAG

/**
 * Created on 15/05/2018.
 *
 * @author lx
 */
object AnkerBoxBluetoothHelper {

    // base UUID used to build 128 bit Bluetooth UUIDs
    private const val UUID_BASE = "0000XXXX-0000-1000-8000-00805f9b34fb"

    private const val CHARACTERISTIC_NOTIFICATION_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"

    // handle 16 and 128 bit UUIDs
    fun uuidFromString(uuid: String): UUID {
        var uuid = uuid
        if (uuid.length == 4) {
            uuid = UUID_BASE.replace("XXXX", uuid)
        }
        try {
            return UUID.fromString(uuid)
        } catch (e: Exception) {
            e.printStackTrace()
            return UUID.fromString(UUID_BASE.replace("XXXX", "0000"))
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun setNotify(gatt: BluetoothGatt, serviceUUID: UUID, characteristicUUID: UUID, notify: Boolean?): Boolean {
        val service = gatt.getService(serviceUUID)
        val characteristic = findNotifyCharacteristic(service, characteristicUUID)
        if (characteristic != null) {
            if (gatt.setCharacteristicNotification(characteristic, notify!!)) {
                val descriptor = characteristic.getDescriptor(uuidFromString(CHARACTERISTIC_NOTIFICATION_CONFIG))
                if (descriptor != null) {
                    // Prefer notify over indicate
                    if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
                        Log.d(TAG, "Characteristic $characteristicUUID set NOTIFY")
                        descriptor.value = if (notify) BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                    } else if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0) {
                        Log.d(TAG, "Characteristic $characteristicUUID set INDICATE")
                        descriptor.value = if (notify) BluetoothGattDescriptor.ENABLE_INDICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                    } else {
                        Log.d(TAG, "Characteristic $characteristicUUID does not have NOTIFY or INDICATE property set")
                    }
                    try {
                        if (gatt.writeDescriptor(descriptor)) {
                            Log.d(TAG, "setNotify complete")
                            return true
                        } else {
                            Log.d(TAG, "Failed to set client characteristic notification for $characteristicUUID")
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "Failed to set client characteristic notification for $characteristicUUID , error: ${e.message}")
                    }

                } else {
                    Log.d(TAG, "Set notification failed for $characteristicUUID")
                }
            } else {
                Log.d(TAG, "Failed to register notification for $characteristicUUID")
            }
        } else {
            Log.d(TAG, "Characteristic $characteristicUUID not found")
        }
        return false
    }

    // Some devices reuse UUIDs across characteristics, so we can't use service.getCharacteristic(characteristicUUID)
    // instead check the UUID and properties for each characteristic in the service until we find the best match
    // This function prefers Notify over Indicate
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun findNotifyCharacteristic(service: BluetoothGattService,
                                         characteristicUUID: UUID): BluetoothGattCharacteristic? {
        var characteristic: BluetoothGattCharacteristic? = null
        try {
            // Check for Notify first
            val characteristics = service.characteristics
            for (c in characteristics) {
                if (c.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0 && characteristicUUID == c.uuid) {
                    characteristic = c
                    break
                }
            }
            if (characteristic != null) return characteristic
            // If there wasn't Notify Characteristic, check for Indicate
            for (c in characteristics) {
                if (c.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0 && characteristicUUID == c.uuid) {
                    characteristic = c
                    break
                }
            }
            // As a last resort, try and find ANY characteristic with this UUID, even if it doesn't have the correct properties
            if (characteristic == null) {
                characteristic = service.getCharacteristic(characteristicUUID)
            }
            return characteristic
        } catch (e: Exception) {
            Log.e(TAG, "Errore su caratteristica $characteristicUUID", e)
            return null
        }
    }

}
