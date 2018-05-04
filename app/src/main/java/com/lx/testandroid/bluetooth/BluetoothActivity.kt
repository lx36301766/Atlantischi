package com.lx.testandroid.bluetooth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ankerboxmanager.bluetooth.AnkerBoxBluetoothManager
import com.ankerboxmanager.bluetooth.IBluetooth
import com.lx.testandroid.R
import com.lx.testandroid.util.Log

class BluetoothActivity : AppCompatActivity() {

    val bluetoothLe = AnkerBoxBluetoothManager.instance.bluetoothLe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        findViewById(R.id.button2).setOnClickListener {
            val result = bluetoothLe!!.startDevicesDiscovery()
            println(result)
        }

        findViewById(R.id.button1).setOnClickListener {

            val devices = bluetoothLe!!.devices as Set
            for (device in devices) {

                bluetoothLe!!.createGattConnection(this, device.deviceId!!, object : IBluetooth.AsyncReturnCallback {
                    //object的作用是调用内部匿名类
                    override fun onAsyncReturn(result: Boolean) {
                        Log.d("lx", "deviceId=${device.deviceId}, result=$result" )
                    }
                })
            }

        }
    }

}
