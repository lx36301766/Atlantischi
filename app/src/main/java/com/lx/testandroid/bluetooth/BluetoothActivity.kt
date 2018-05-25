//package com.lx.testandroid.bluetooth
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v13.app.ActivityCompat
//import bak.bluetooth.AnkerBoxBluetooth
//import bak.bluetooth.AnkerBoxBluetoothManager
//import bak.bluetooth.IBluetooth
//import bak.bluetooth.IBluetoothLe
//import bak.bluetooth.bean.AnkerBoxBluetoothDevice
//import com.lx.testandroid.R
//import com.lx.testandroid.util.Log
//import java.util.*
//
//class BluetoothActivity : AppCompatActivity() {
//
//    private val TAG = AnkerBoxBluetooth::class.java.simpleName
//
//    var bluetoothLe: IBluetoothLe? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bluetooth)
//
//        AnkerBoxBluetoothManager.instance.init(this)
//        bluetoothLe = AnkerBoxBluetoothManager.instance.bluetoothLe
//
//        bluetoothLe?.let {
//            it.registerDeviceFoundListener(bluetoothListener)
//            it.registerStateChangedListener(stateChangedListener)
//            it.addCharacteristicValueChangedListener(characteristicValueChangedListener)
//        }
//
//        findViewById(R.id.open).setOnClickListener {
//            bluetoothLe?.let { printResult(it.open()) }
//        }
//
//        findViewById(R.id.start).setOnClickListener {
//            doDiscovery()
//        }
//
//        findViewById(R.id.connect).setOnClickListener {
//            bluetoothLe?.let {
//                it.stopLeDevicesDiscovery()
//                it.createGattConnection(this, macAddr, object : IBluetooth.AsyncReturnCallback {
//                    override fun onAsyncReturn(result: Boolean) {
//                        printResult(result)
//                    }
//                })
//            }
//        }
//        findViewById(R.id.disconnect).setOnClickListener {
//            bluetoothLe?.let { printResult(it.closeLeConnection(this, macAddr)) }
//        }
//
//        findViewById(R.id.get_devices).setOnClickListener {
//            bluetoothLe?.let { Log.d(TAG, "get_devices = ${it.devices}") }
//        }
//
//        findViewById(R.id.get_service).setOnClickListener {
//            bluetoothLe?.let { Log.d(TAG, "get_service = ${it.getServices(this, macAddr)}") }
//        }
//
//        findViewById(R.id.get_characteristics).setOnClickListener {
//            bluetoothLe?.let { Log.d(TAG, "get_characteristics = ${it.getDeviceCharacteristics(
//                    this, "", UUID.fromString(serviceId))}") }
//        }
//
//        findViewById(R.id.notify_characteristic).setOnClickListener {
//            bluetoothLe?.let { Log.d(TAG, "notify_characteristic = ${it.notifyCharacteristicValueChange(
//                    this, "", UUID.fromString(serviceId), UUID.fromString(characteristicId), 1)}") }
//        }
//
//        findViewById(R.id.write_characteristic).setOnClickListener {
//            bluetoothLe?.let { Log.d(TAG, "notify_characteristic = ${it.writeCharacteristicValue(
//                    this, "", UUID.fromString(serviceId), UUID.fromString(characteristicId), "xxx")}") }
//        }
//
//    }
//
//    val REQUEST_COARSE_LOCATION_PERMISSIONS = 22
//
//    //name=B073MP1213, address=C0:15:83:2A:B1:9B
//    //name=S296PA2491, address=C0:15:83:34:15:87
//    //name=A166MP1240, address=C0:15:83:EF:0D:3F
//    val macAddr = "C0:15:83:2A:B1:9B"
//
//    val serviceId = "0000ff00-0000-1000-8000-00805f9b34fb"
//
//    // AnkerBoxBluetoothGattCharacteristic(uuid=0000ff01-0000-1000-8000-00805f9b34fb, characteristic=Characteristic(read=false, write=false, notify=true, indicate=false)),
//    // AnkerBoxBluetoothGattCharacteristic(uuid=0000ff02-0000-1000-8000-00805f9b34fb, characteristic=Characteristic(read=false, write=true, notify=false, indicate=false)),
//    // AnkerBoxBluetoothGattCharacteristic(uuid=0000ff03-0000-1000-8000-00805f9b34fb, characteristic=Characteristic(read=false, write=false, notify=true, indicate=false))]
//
//    val characteristicId = "0000ff01-0000-1000-8000-00805f9b34fb"
//
//    private fun printResult(result: Boolean) {
//        Log.d(TAG, "result = $result")
//    }
//
//    val bluetoothListener = object : IBluetooth.DeviceFoundListener {
//        override fun onNewDeviceFound(newDevice: AnkerBoxBluetoothDevice) {
//            Log.d(TAG, "newDevice=$newDevice")
//        }
//    }
//
//    val stateChangedListener = object : IBluetooth.AdapterStateChangedListener {
//        override fun onChanged(previousState: Int, newState: Int) {
//            Log.d(TAG, "previousState=$previousState, newState=$newState")
//        }
//    }
//
//    val characteristicValueChangedListener = object : IBluetoothLe.CharacteristicValueChangedListener {
//        override fun onChanged(deviceId: String, serviceId: UUID, characteristicId: UUID, value: ByteArray) {
//            Log.d(TAG, "deviceId=$deviceId, serviceId=$serviceId")
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        bluetoothLe?.unregisterDeviceFoundListener(bluetoothListener)
//    }
//
//    fun doDiscovery() {
//        val hasPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
//            //            continueDoDiscovery();
////            printResult(bluetoothLe!!.startDevicesDiscovery())
//            bluetoothLe?.let { printResult(it.startLeDevicesDiscovery(null)) }
//            return
//        }
//        ActivityCompat.requestPermissions(this,
//                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_COARSE_LOCATION_PERMISSIONS)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        when (requestCode) {
//            REQUEST_COARSE_LOCATION_PERMISSIONS -> {
//                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //                    continueDoDiscovery();
////                    printResult(bluetoothLe!!.startDevicesDiscovery())
//                    bluetoothLe?.let { printResult(it.startLeDevicesDiscovery(null)) }
//                }
//            }
//        }
//    }
//
//}
