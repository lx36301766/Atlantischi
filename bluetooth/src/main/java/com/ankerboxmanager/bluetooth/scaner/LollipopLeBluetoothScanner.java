//package com.ankerboxmanager.bluetooth.scaner;
//
//import static com.ankerboxmanager.bluetooth.IBluetooth.TAG;
//import static com.ankerboxmanager.bluetooth.impl.AnkerBoxBluetoothHelper.uuidFromString;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.ankerboxmanager.bluetooth.IBluetooth;
//
//import android.annotation.TargetApi;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothManager;
//import android.bluetooth.le.BluetoothLeScanner;
//import android.bluetooth.le.ScanCallback;
//import android.bluetooth.le.ScanFilter;
//import android.bluetooth.le.ScanResult;
//import android.bluetooth.le.ScanSettings;
//import android.content.Context;
//import android.os.Build;
//import android.os.Handler;
//import android.os.ParcelUuid;
//import android.util.Log;
//
///**
// * Created on 15/05/2018.
// *
// * @author lx
// */
//
//@TargetApi(Build.VERSION_CODES.LOLLIPOP)
//public class LollipopLeBluetoothScanner extends BluetoothScanner {
//
//    private IBluetooth.AsyncReturnCallback asyncReturnCallback;
//
//    private int retryCount;
//
//    private Handler handler = new Handler();
//
//    private ScanCallback scanCallback = new ScanCallback() {
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            super.onScanResult(callbackType, result);
//            if (asyncReturnCallback != null) {
//                asyncReturnCallback.onReturn(true);
//                asyncReturnCallback = null;
//            }
//            onNewDeviceFound(result.getDevice());
//        }
//
//        @Override
//        public void onScanFailed(int errorCode) {
//            super.onScanFailed(errorCode);
//            if (asyncReturnCallback != null) {
//                asyncReturnCallback.onReturn(false);
//                asyncReturnCallback = null;
//            }
//        }
//    };
//
//    public LollipopLeBluetoothScanner(Context context) {
//        super(context);
//    }
//
//    @Override
//    public void startScan(String[] serviceUUIDs, IBluetooth.AsyncReturnCallback returnCallback) {
//        ScanSettings settings = new ScanSettings.Builder().build();
//        List<ScanFilter> filters = new ArrayList<>();
//        if (serviceUUIDs != null && serviceUUIDs.length > 0) {
//            for(int i = 0; i < serviceUUIDs.length; i++){
//                ScanFilter.Builder builder = new ScanFilter.Builder();
//                builder.setServiceUuid(new ParcelUuid(uuidFromString(serviceUUIDs[i])));
//                filters.add(builder.build());
//            }
//        }
//        asyncReturnCallback = returnCallback;
//        retryCount = 0;
//        handler.removeCallbacksAndMessages(null);
//        delayStartScan(filters, settings);
//    }
//
//    private void delayStartScan(final List<ScanFilter> filters, final ScanSettings settings) {
//        BluetoothLeScanner leScanner = getBluetoothAdapter().getBluetoothLeScanner();
//        Log.d(TAG, "leScanner=" + leScanner + ", retryCount=" + retryCount);
//        if (leScanner == null) {
//            if (retryCount++ < 60) {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        delayStartScan(filters, settings);
//                    }
//                }, 500);
//            } else {
//                Log.e(TAG, "Could not get leScanner !");
//            }
//        } else {
//            retryCount = 0;
//            leScanner.startScan(filters, settings, scanCallback);
//        }
//    }
//
//    @Override
//    public void stopScan(IBluetooth.AsyncReturnCallback returnCallback) {
//        BluetoothLeScanner leScanner = getBluetoothAdapter().getBluetoothLeScanner();
//        if (leScanner != null) {
//            leScanner.stopScan(scanCallback);
//        }
//        asyncReturnCallback = returnCallback;
//    }
//
//    @Override
//    public void destroy() {
//        super.destroy();
//        handler.removeCallbacksAndMessages(null);
//        asyncReturnCallback = null;
//    }
//
//    public BluetoothAdapter getBluetoothAdapter() {
//        BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
//        return manager.getAdapter();
//    }
//
//}
