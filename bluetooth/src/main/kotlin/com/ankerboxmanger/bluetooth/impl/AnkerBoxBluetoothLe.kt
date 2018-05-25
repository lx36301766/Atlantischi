package com.ankerboxmanger.bluetooth.impl

import android.annotation.TargetApi
import com.ankerboxmanger.bluetooth.impl.AnkerBoxBluetoothHelper.setNotify
import com.ankerboxmanger.bluetooth.impl.AnkerBoxBluetoothHelper.uuidFromString

import java.util.ArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import com.ankerboxmanger.bluetooth.ext.notNull
import com.ankerboxmanger.bluetooth.ext.safeLet
import com.ankerboxmanger.bluetooth.IBluetoothLe
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothGattCharacteristic
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothLeService
import com.ankerboxmanger.bluetooth.scaner.BluetoothScanner
import com.ankerboxmanger.bluetooth.scaner.LegacyLeBluetoothScanner
import com.ankerboxmanger.bluetooth.scaner.LollipopLeBluetoothScanner
import com.ankerboxmanger.bluetooth.IBluetooth
import com.ankerboxmanger.bluetooth.IBluetooth.Companion.TAG

/**
 * Created on 21/03/2018.
 *
 * @author lx
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class AnkerBoxBluetoothLe : AnkerBoxBluetooth(), IBluetoothLe {

    override var isSupportLe: Boolean = false
        private set

    private var mBluetoothGatt: BluetoothGatt? = null

    private var executorService: ExecutorService? = Executors.newFixedThreadPool(5)

    private var gattCallback: GattCallbackImpl? = null

    override fun init(context: Context) {
        super.init(context)
        Log.d(TAG, "le init")
        gattCallback = GattCallbackImpl()
        isSupportLe = isSupportBluetooth && context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    override fun createBluetoothScanner(context: Context): BluetoothScanner {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LollipopLeBluetoothScanner(context)
        } else {
            LegacyLeBluetoothScanner(context)
        }
    }

    override fun destroy(context: Context) {
        super.destroy(context)
        gattCallback?.destroy()
        gattCallback = null
        mBluetoothGatt?.disconnect()
        mBluetoothGatt?.close()
        mBluetoothGatt = null
        executorService?.shutdown()
        executorService = null
        Log.d(TAG, "le destroy")
    }

    override fun startDevicesDiscovery(serviceUUIDs: Array<String>, returnCallback: IBluetooth.AsyncReturnCallback?) {
        if (isSupportLe) {
            super.startDevicesDiscovery(serviceUUIDs, returnCallback)
        } else {
            returnCallback?.onReturn(false)
        }
    }

    override fun stopDevicesDiscovery(returnCallback: IBluetooth.AsyncReturnCallback?) {
        if (isSupportLe) {
            super.stopDevicesDiscovery(returnCallback)
        } else {
            returnCallback?.onReturn(false)
        }
    }

    override fun connectGatt(context: Context, deviceId: String, returnCallback: IBluetooth.AsyncReturnCallback) {
        Log.d(TAG, "connectGatt, deviceId=$deviceId")
        if (isSupportLe) {
            safeLet(executorService, mBluetoothAdapter) { service, adapter ->
                service.execute {
                    val bondedDevice = adapter.getRemoteDevice(deviceId)
                    val gatt = bondedDevice.connectGatt(context, true, gattCallback)
                    gattCallback?.setGattConnect(gatt, returnCallback)
                    mBluetoothGatt = gatt
                }
            }
        }
    }

    override fun disconnectGatt(context: Context, deviceId: String): Boolean {
        Log.d(TAG, "disconnectGatt, deviceId=$deviceId")
        if (mBluetoothGatt != null) {
            mBluetoothGatt!!.disconnect()
            mBluetoothGatt!!.close()
            mBluetoothGatt = null
            return true
        }
        return false
    }

    override fun addLeStateChangedListener(listener: IBluetoothLe.StateChangedListener): Boolean {
        Log.d(TAG, "addLeStateChangedListener, listener=$listener")
        return gattCallback.let { it != null && addLeStateChangedListener(listener) }
    }

    override fun removeLeStateChangedListener(listener: IBluetoothLe.StateChangedListener): Boolean {
        Log.d(TAG, "removeLeStateChangedListener, listener=$listener")
        return gattCallback.let { it != null && removeLeStateChangedListener(listener) }
    }

    override fun getServices(context: Context, deviceId: String): List<AnkerBoxBluetoothLeService>? {
        Log.d(TAG, "getServices, deviceId = $deviceId")
        mBluetoothGatt.notNull {
            val services = ArrayList<AnkerBoxBluetoothLeService>()
            Log.d(TAG, "gattServices.size = " + it.services.size)
            for (gattService in it.services) {
                val service = AnkerBoxBluetoothLeService(gattService.uuid, gattService.type == BluetoothGattService.SERVICE_TYPE_PRIMARY)
                Log.d(TAG, "service.uuid = ${service.uuid}, service.isPrimary = ${service.isPrimary}")
                services.add(service)
            }
            return services
        }
        return null
    }

    override fun getDeviceCharacteristics(context: Context, deviceId: String,
                                          serviceId: String): List<AnkerBoxBluetoothGattCharacteristic>? {
        Log.d(TAG, "getDeviceCharacteristics deviceId= $deviceId, serviceId = $serviceId")
        mBluetoothGatt.notNull {
            val characteristicList = ArrayList<AnkerBoxBluetoothGattCharacteristic>()
            val gattService = it.getService(uuidFromString(serviceId))
            val gattCharacteristics = gattService.characteristics
            Log.d(TAG, "gattCharacteristics.size = ${gattCharacteristics.size}")
            for (gattCharacteristic in gattCharacteristics) {
                val properties = gattCharacteristic.properties
                val read = properties and BluetoothGattCharacteristic.PROPERTY_READ
                val write = properties and BluetoothGattCharacteristic.PROPERTY_WRITE or
                        (properties and BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)
                val notify = properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY
                val indicate = properties and BluetoothGattCharacteristic.PROPERTY_INDICATE
                val characteristic = AnkerBoxBluetoothGattCharacteristic.Characteristic(read, write, notify, indicate)
                characteristicList.add(AnkerBoxBluetoothGattCharacteristic(gattCharacteristic.uuid, characteristic))
                Log.d(TAG, characteristic.toString())
            }
            return characteristicList
        }
        return null
    }

    override fun readCharacteristicValue(context: Context, deviceId: String, serviceId: String, characteristicId: String): Boolean {
        Log.d(TAG, "readCharacteristicValue deviceId= $deviceId, serviceId = $serviceId, characteristicId = $characteristicId")
        mBluetoothGatt.notNull {
            val gattService = it.getService(uuidFromString(serviceId))
            val characteristic = gattService.getCharacteristic(uuidFromString(characteristicId))
            return it.readCharacteristic(characteristic)
        }
        return false
    }

    override fun writeCharacteristicValue(context: Context, deviceId: String, serviceId: String, characteristicId: String,
                                          value: String): Boolean {
        Log.d(TAG, "writeCharacteristicValue deviceId= " + deviceId + ", serviceId = " + serviceId
                + ", characteristicId = " + characteristicId + ", value = " + value)
        mBluetoothGatt.notNull {
            val gattService = it.getService(uuidFromString(serviceId))
            val characteristic = gattService.getCharacteristic(uuidFromString(characteristicId))
            characteristic.value = Base64.decode(value, Base64.DEFAULT)
            characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
            return it.writeCharacteristic(characteristic)
        }
        return false
    }

    override fun notifyCharacteristicValueChange(context: Context, deviceId: String, serviceId: String,
                                                 characteristicId: String, state: Int): Boolean {
        Log.d(TAG, "notifyCharacteristicValueChange deviceId= " + deviceId + ", serviceId = " + serviceId
                + ", characteristicId = " + characteristicId + ", state = " + state)
        return mBluetoothGatt.let {
            it != null && setNotify(it, uuidFromString(serviceId), uuidFromString(characteristicId), state == 1)
        }
    }

    override fun addCharacteristicValueChangedListener(listener: IBluetoothLe.CharacteristicValueChangedListener): Boolean {
        Log.d(TAG, "addCharacteristicValueChangedListener, listener=$listener")
        return gattCallback.let { it != null && addCharacteristicValueChangedListener(listener) }
    }

    override fun removeCharacteristicValueChangedListener(listener: IBluetoothLe.CharacteristicValueChangedListener): Boolean {
        Log.d(TAG, "removeCharacteristicValueChangedListener, listener=$listener")
        return gattCallback.let { it != null && removeCharacteristicValueChangedListener(listener) }
    }

}
