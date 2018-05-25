package com.ankerboxmanger.bluetooth.impl

import java.util.Arrays
import java.util.HashSet

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.ankerboxmanger.bluetooth.ext.notNull
import com.ankerboxmanger.bluetooth.ext.safeLet
import com.ankerboxmanger.bluetooth.IBluetooth
import com.ankerboxmanger.bluetooth.IBluetooth.Companion.TAG
import com.ankerboxmanger.bluetooth.bean.AnkerBoxBluetoothDevice
import com.ankerboxmanger.bluetooth.scaner.BluetoothScanner
import com.ankerboxmanger.bluetooth.scaner.NormalBluetoothScanner

/**
 * Created on 19/03/2018.
 *
 * @author lx
 */

open class AnkerBoxBluetooth : IBluetooth {

    internal val mBluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    protected var bluetoothScanner: BluetoothScanner? = null

    private val mAdapterStateChangedListeners: MutableSet<IBluetooth.AdapterStateChangedListener>
            by lazy { HashSet<IBluetooth.AdapterStateChangedListener>() }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            when (intent?.action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1)
                    val newState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                    for (listener in mAdapterStateChangedListeners) {
                        listener.onChanged(previousState, newState)
                    }
                    Log.d(TAG, "ACTION_STATE_CHANGED, previousState=$previousState, newState=$newState")
                }
            }
        }
    }

    override val isSupportBluetooth = mBluetoothAdapter != null

    override val isEnabled: Boolean
        get() {
            Log.d(TAG, "isEnabled")
            return isSupportBluetooth && mBluetoothAdapter.let { it != null && it.isEnabled }
        }

    override val adapterState: Int
        get() {
            Log.d(TAG, "getAdapterState")
            if (isSupportBluetooth) {
//                mBluetoothAdapter?.let{ return it.state }
                mBluetoothAdapter.notNull { return it.state }
            }
            return -1
        }

    override val isDiscovering: Boolean
        get() = isSupportBluetooth && mBluetoothAdapter.let { it != null && it.isDiscovering }

    override val devices: Set<AnkerBoxBluetoothDevice>?
        get() {
            Log.d(TAG, "getDevices")
            return if (isSupportBluetooth) { bluetoothScanner?.foundedBluetoothDevices } else null
        }

    override fun init(context: Context) {
        Log.d(TAG, "init")
        bluetoothScanner = createBluetoothScanner(context)
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(broadcastReceiver, filter)
    }

    protected open fun createBluetoothScanner(context: Context): BluetoothScanner {
        return NormalBluetoothScanner(context)
    }

    override fun destroy(context: Context) {
        context.unregisterReceiver(broadcastReceiver)
        mAdapterStateChangedListeners.clear()
        bluetoothScanner?.destroy()
        Log.d(TAG, "destroy")
    }

    override fun openFriendly(activity: Activity, requestCode: Int): Boolean {
        Log.d(TAG, "openFriendly")
        if (isSupportBluetooth) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, requestCode)
            return true
        }
        return false
    }

    override fun open(): Boolean {
        Log.d(TAG, "open")
        return isSupportBluetooth && mBluetoothAdapter.let { it != null && it.enable() }
    }

    override fun close(): Boolean {
        Log.d(TAG, "close")
        return isSupportBluetooth && mBluetoothAdapter.let { it != null && it.disable() }
    }

    override fun registerStateChangedListener(listener: IBluetooth.AdapterStateChangedListener): Boolean {
        Log.d(TAG, "registerStateChangedListener, listener=$listener")
        return isSupportBluetooth && mAdapterStateChangedListeners.add(listener)
    }

    override fun unregisterStateChangedListener(listener: IBluetooth.AdapterStateChangedListener): Boolean {
        Log.d(TAG, "unregisterStateChangedListener, listener=$listener")
        return isSupportBluetooth && mAdapterStateChangedListeners.remove(listener)
    }

    override fun startDevicesDiscovery(serviceUUIDs: Array<String>, returnCallback: IBluetooth.AsyncReturnCallback?) {
        Log.d(TAG, "startDevicesDiscovery")
        if (isSupportBluetooth) {
            bluetoothScanner?.startScan(null, returnCallback)
        } else {
            returnCallback?.onReturn(false)
        }
    }

    override fun stopDevicesDiscovery(returnCallback: IBluetooth.AsyncReturnCallback?) {
        Log.d(TAG, "stopDevicesDiscovery")
        if (isSupportBluetooth) {
            bluetoothScanner?.stopScan(returnCallback)
        } else {
            returnCallback?.onReturn(false)
        }
    }

    override fun addDeviceFoundListener(listener: IBluetooth.DeviceFoundListener): Boolean {
        Log.d(TAG, "addDeviceFoundListener, listener=$listener")
        return isSupportBluetooth && bluetoothScanner.let { it != null && it.addDeviceFoundListener(listener) }
    }

    override fun removeDeviceFoundListener(listener: IBluetooth.DeviceFoundListener): Boolean {
        Log.d(TAG, "removeDeviceFoundListener, listener=$listener")
        return isSupportBluetooth && bluetoothScanner.let { it != null && it.removeDeviceFoundListener(listener) }
    }

    override fun getConnectedDevicesInfo(uuids: Array<String>): Set<AnkerBoxBluetoothDevice>? {
        Log.d(TAG, "getConnectedDevicesInfo, uuids=" + Arrays.toString(uuids))
        if (isSupportBluetooth) {
            val devices = HashSet<AnkerBoxBluetoothDevice>()
            for (uuid in uuids) {
//                val bondedDevice = mBluetoothAdapter?.getRemoteDevice(uuid)
//                bluetoothScanner?.let { devices.add(it.buildDevice(bondedDevice)) }
                safeLet(bluetoothScanner, mBluetoothAdapter) {
                    scanner, adapter -> devices.add(scanner.buildDevice(adapter.getRemoteDevice(uuid)))
                }

            }
            return devices
        }
        return null
    }

}
