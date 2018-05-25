//package com.ankerboxmanager.bluetooth.scaner;
//
//import com.ankerboxmanager.bluetooth.IBluetooth;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//
///**
// * Created on 15/05/2018.
// *
// * @author lx
// */
//
//public class NormalBluetoothScanner extends BluetoothScanner {
//
//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent == null || intent.getAction() == null) {
//                return;
//            }
//            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                onNewDeviceFound(device);
//            }
//        }
//    };
//
//    public NormalBluetoothScanner(Context context) {
//        super(context);
//    }
//
//    @Override
//    public void startScan(String[] serviceUUIDs, IBluetooth.AsyncReturnCallback asyncReturnCallback) {
//        context.registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
//        boolean result =  getBluetoothAdapter().startDiscovery();
//        if (asyncReturnCallback != null) {
//            asyncReturnCallback.onReturn(result);
//        }
//    }
//
//    @Override
//    public void stopScan(IBluetooth.AsyncReturnCallback asyncReturnCallback) {
//        context.unregisterReceiver(broadcastReceiver);
//        boolean result =  getBluetoothAdapter().cancelDiscovery();
//        if (asyncReturnCallback != null) {
//            asyncReturnCallback.onReturn(result);
//        }
//    }
//
//    @Override
//    public void destroy() {
//        try {
//            context.unregisterReceiver(broadcastReceiver);
//        } catch (Exception e) {
//        }
//        super.destroy();
//    }
//
//    private BluetoothAdapter getBluetoothAdapter() {
//        return BluetoothAdapter.getDefaultAdapter();
//    }
//
//}
