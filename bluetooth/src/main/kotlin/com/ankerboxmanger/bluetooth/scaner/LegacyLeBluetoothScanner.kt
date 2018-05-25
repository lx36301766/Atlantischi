package com.ankerboxmanger.bluetooth.scaner

import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import com.ankerboxmanger.bluetooth.IBluetooth

/**
 * Created on 15/05/2018.
 *
 * @author lx
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class LegacyLeBluetoothScanner(context: Context) : BluetoothScanner(context) {

    private val leScanCallback = BluetoothAdapter.LeScanCallback {
        device, rssi, scanRecord -> onNewDeviceFound(device)
    }

    private val bluetoothAdapter: BluetoothAdapter
        get() {
            val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            return manager.adapter
        }

    override fun startScan(serviceUUIDs: Array<String>?, asyncReturnCallback: IBluetooth.AsyncReturnCallback?) {
        val result = bluetoothAdapter.startLeScan(leScanCallback)
        asyncReturnCallback?.onReturn(result)
    }

    override fun stopScan(asyncReturnCallback: IBluetooth.AsyncReturnCallback?) {
        bluetoothAdapter.stopLeScan(leScanCallback)
        asyncReturnCallback?.onReturn(true)
    }

}
