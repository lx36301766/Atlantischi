//package com.ankerboxmanager.bluetooth;
//
//import static com.ankerboxmanager.bluetooth.IBluetooth.TAG;
//
//import com.ankerboxmanager.bluetooth.impl.AnkerBoxBluetoothLe;
//
//import android.content.Context;
//import android.util.Log;
//
///**
// * Created on 19/03/2018.
// *
// * @author lx
// */
//
//public class AnkerBoxBluetoothManager {
//
//    private static class InstanceHolder {
//        private static AnkerBoxBluetoothManager instance = new AnkerBoxBluetoothManager();
//    }
//
//    public static AnkerBoxBluetoothManager getInstance() {
//        return InstanceHolder.instance;
//    }
//
//    private IBluetoothLe bluetoothLe;
//
//    public void init(Context context) {
//        Log.d(TAG, "AnkerBoxBluetoothManager, init");
//        bluetoothLe = new AnkerBoxBluetoothLe();
//        if (context != null) {
//            bluetoothLe.init(context.getApplicationContext());
//        }
//    }
//
//    public void destroy(Context context) {
//        Log.d(TAG, "AnkerBoxBluetoothManager, destroy");
//        if (context != null) {
//            bluetoothLe.stopDevicesDiscovery(null);
//            bluetoothLe.destroy(context.getApplicationContext());
//        }
//    }
//
//    public IBluetoothLe getBluetoothLe() {
//        return bluetoothLe;
//    }
//
//}
