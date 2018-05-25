package com.ankerboxmanger.bluetooth.scaner

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.ankerboxmanger.bluetooth.IBluetooth

/**
 * Created on 15/05/2018.
 *
 * @author lx
 */

class NormalBluetoothScanner(context: Context) : BluetoothScanner(context) {

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (BluetoothDevice.ACTION_FOUND == intent?.action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                onNewDeviceFound(device)
            }
        }
    }

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    override fun startScan(serviceUUIDs: Array<String>?, asyncReturnCallback: IBluetooth.AsyncReturnCallback?) {
        context.registerReceiver(broadcastReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        val result = bluetoothAdapter.startDiscovery()
        asyncReturnCallback?.onReturn(result)
    }

    override fun stopScan(asyncReturnCallback: IBluetooth.AsyncReturnCallback?) {
        context.unregisterReceiver(broadcastReceiver)
        val result = bluetoothAdapter.cancelDiscovery()
        asyncReturnCallback?.onReturn(result)
    }

    override fun destroy() {
        try {
            context.unregisterReceiver(broadcastReceiver)
        } catch (e: Exception) {
        }
        super.destroy()
    }

}
