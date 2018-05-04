package com.ankerboxmanager.bluetooth

import java.util.HashSet

import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothDevice

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

/**
 * Created on 19/03/2018.
 *
 * @author lx
 */

open class AnkerBoxBluetooth : IBluetooth {

    internal var mBluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private var mDeviceFoundListeners: MutableSet<IBluetooth.DeviceFoundListener>? = null
    private var mAdapterStateChangedListeners: MutableSet<IBluetooth.AdapterStateChangedListener>? = null

    private var mAnkerBoxBluetoothDevices: MutableSet<AnkerBoxBluetoothDevice>? = null

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (intent == null || intent.action == null) {
                return
            }
            when (intent.action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1)
                    val newState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                    for (listener in mAdapterStateChangedListeners!!) {
                        listener.onChanged(previousState, newState)
                    }
                }
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    val abDevice = buildDevice(device)
                    mAnkerBoxBluetoothDevices!!.add(abDevice)
                    for (listener in mDeviceFoundListeners!!) {
                        listener.onNewDeviceFound(abDevice)
                    }
                }
            }
        }
    }

    override val isSupportBluetooth: Boolean
        get() = mBluetoothAdapter != null

    override val isEnabled: Boolean
        get() = isSupportBluetooth && mBluetoothAdapter!!.isEnabled

    override val adapterState: Int
        get() = if (isSupportBluetooth) mBluetoothAdapter!!.state else -1

    override val isDiscovering: Boolean
        get() = isSupportBluetooth && mBluetoothAdapter!!.isDiscovering

    override val devices: Set<AnkerBoxBluetoothDevice>?
        get() = if (isSupportBluetooth) { mAnkerBoxBluetoothDevices } else null

    override fun init(context: Context) {
        mDeviceFoundListeners = HashSet()
        mAdapterStateChangedListeners = HashSet()
        mAnkerBoxBluetoothDevices = HashSet()

        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(broadcastReceiver, filter)
    }

    override fun destroy(context: Context) {
        context.unregisterReceiver(broadcastReceiver)
        mDeviceFoundListeners!!.clear()
        mAdapterStateChangedListeners!!.clear()
    }

    override fun openFriendly(activity: Activity, requestCode: Int): Boolean {
        if (isSupportBluetooth) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, requestCode)
            return true
        }
        return false
    }

    override fun open(): Boolean {
        return isSupportBluetooth && mBluetoothAdapter!!.enable()
    }

    override fun close(): Boolean {
        return isSupportBluetooth && mBluetoothAdapter!!.disable()
    }

    override fun registerStateChangedListener(listener: IBluetooth.AdapterStateChangedListener): Boolean {
        return isSupportBluetooth && mAdapterStateChangedListeners!!.add(listener)
    }

    override fun unregisterStateChangedListener(listener: IBluetooth.AdapterStateChangedListener): Boolean {
        return isSupportBluetooth && mAdapterStateChangedListeners!!.remove(listener)
    }

    override fun startDevicesDiscovery(): Boolean {
        return isSupportBluetooth && mBluetoothAdapter!!.startDiscovery()
    }

    override fun stopDevicesDiscovery(): Boolean {
        return isSupportBluetooth && mBluetoothAdapter!!.cancelDiscovery()
    }

    private fun buildDevice(bondedDevice: BluetoothDevice): AnkerBoxBluetoothDevice {
        return AnkerBoxBluetoothDevice(bondedDevice.name, bondedDevice.address)
    }

    override fun registerDeviceFoundListener(listener: IBluetooth.DeviceFoundListener): Boolean {
        return isSupportBluetooth && mDeviceFoundListeners!!.add(listener)
    }

    override fun unregisterDeviceFoundListener(listener: IBluetooth.DeviceFoundListener): Boolean {
        return isSupportBluetooth && mDeviceFoundListeners!!.remove(listener)
    }

    override fun getConnectedDevicesInfo(uuids: Array<String>): Set<AnkerBoxBluetoothDevice>? {
        if (isSupportBluetooth) {
            val devices = HashSet<AnkerBoxBluetoothDevice>()
            for (uuid in uuids) {
                val bondedDevice = mBluetoothAdapter!!.getRemoteDevice(uuid)
                devices.add(buildDevice(bondedDevice))
            }
            return devices
        }
        return null
    }

}
