package com.ankerboxmanger.bluetooth.scaner

import com.ankerboxmanger.bluetooth.IBluetooth.Companion.TAG
import com.ankerboxmanger.bluetooth.impl.AnkerBoxBluetoothHelper.uuidFromString

import java.util.ArrayList

import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.ParcelUuid
import android.util.Log
import com.ankerboxmanger.bluetooth.IBluetooth

/**
 * Created on 15/05/2018.
 *
 * @author lx
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class LollipopLeBluetoothScanner(context: Context) : BluetoothScanner(context) {

    private var asyncReturnCallback: IBluetooth.AsyncReturnCallback? = null

    private var retryCount: Int = 0

    private val handler by lazy { Handler() }

    private val scanCallback = object : ScanCallback() {

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            asyncReturnCallback?.onReturn(true)
            asyncReturnCallback = null
            onNewDeviceFound(result.device)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            asyncReturnCallback?.onReturn(false)
            asyncReturnCallback = null
        }
    }

    val bluetoothAdapter: BluetoothAdapter
        get() {
            val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            return manager.adapter
        }

    override fun startScan(serviceUUIDs: Array<String>?, asyncReturnCallback: IBluetooth.AsyncReturnCallback?) {
        val settings = ScanSettings.Builder().build()
        val filters = ArrayList<ScanFilter>()
        if (serviceUUIDs != null && serviceUUIDs.size > 0) {
            for (i in serviceUUIDs.indices) {
                val builder = ScanFilter.Builder()
                builder.setServiceUuid(ParcelUuid(uuidFromString(serviceUUIDs[i])))
                filters.add(builder.build())
            }
        }
        this.asyncReturnCallback = asyncReturnCallback
        retryCount = 0
        handler.removeCallbacksAndMessages(null)
        delayStartScan(filters, settings)
    }

    private fun delayStartScan(filters: List<ScanFilter>, settings: ScanSettings) {
        val leScanner = bluetoothAdapter.bluetoothLeScanner
        Log.d(TAG, "leScanner=$leScanner, retryCount=$retryCount")
        if (leScanner == null) {
            if (retryCount++ < 60) {
                handler.postDelayed({ delayStartScan(filters, settings) }, 500)
            } else {
                Log.e(TAG, "Could not get leScanner !")
            }
        } else {
            retryCount = 0
            leScanner.startScan(filters, settings, scanCallback)
        }
    }

    override fun stopScan(asyncReturnCallback: IBluetooth.AsyncReturnCallback?) {
        val leScanner = bluetoothAdapter.bluetoothLeScanner
        leScanner?.stopScan(scanCallback)
        this.asyncReturnCallback = asyncReturnCallback
    }

    override fun destroy() {
        super.destroy()
        handler.removeCallbacksAndMessages(null)
        asyncReturnCallback = null
    }

}
