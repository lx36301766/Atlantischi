//package com.ankerboxmanager.bluetooth.scaner;
//
//import com.ankerboxmanager.bluetooth.IBluetooth;
//
//import android.annotation.TargetApi;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothManager;
//import android.content.Context;
//import android.os.Build;
//
///**
// * Created on 15/05/2018.
// *
// * @author lx
// */
//
//@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
//public class LegacyLeBluetoothScanner extends BluetoothScanner {
//
//    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
//        @Override
//        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//            onNewDeviceFound(device);
//        }
//    };
//
//    public LegacyLeBluetoothScanner(Context context) {
//        super(context);
//    }
//
//    @Override
//    public void startScan(String[] serviceUUIDs, IBluetooth.AsyncReturnCallback asyncReturnCallback) {
//        boolean result = getBluetoothAdapter().startLeScan(leScanCallback);
//        if (asyncReturnCallback != null) {
//            asyncReturnCallback.onReturn(result);
//        }
//    }
//
//    @Override
//    public void stopScan(IBluetooth.AsyncReturnCallback asyncReturnCallback) {
//        getBluetoothAdapter().stopLeScan(leScanCallback);
//        if (asyncReturnCallback != null) {
//            asyncReturnCallback.onReturn(true);
//        }
//    }
//
//    private BluetoothAdapter getBluetoothAdapter() {
//        BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
//        return manager.getAdapter();
//    }
//
//}
