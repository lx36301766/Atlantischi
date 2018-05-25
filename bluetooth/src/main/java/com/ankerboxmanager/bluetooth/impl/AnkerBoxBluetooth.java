//package com.ankerboxmanager.bluetooth.impl;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//import com.ankerboxmanager.bluetooth.IBluetooth;
//import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothDevice;
//import com.ankerboxmanager.bluetooth.scaner.BluetoothScanner;
//import com.ankerboxmanager.bluetooth.scaner.NormalBluetoothScanner;
//
//import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.util.Log;
//
///**
// * Created on 19/03/2018.
// *
// * @author lx
// */
//
//public class AnkerBoxBluetooth implements IBluetooth {
//
//    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//    protected BluetoothScanner bluetoothScanner;
//
//    private Set<AdapterStateChangedListener> mAdapterStateChangedListeners;
//
//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent == null || intent.getAction() == null) {
//                return;
//            }
//            switch (intent.getAction()) {
//                case BluetoothAdapter.ACTION_STATE_CHANGED:
//                    int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1);
//                    int newState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
//                    for (AdapterStateChangedListener listener : mAdapterStateChangedListeners) {
//                        listener.onChanged(previousState, newState);
//                    }
//                    Log.d(TAG, "ACTION_STATE_CHANGED, previousState=" + previousState + ", newState=" + newState);
//                    break;
//            }
//        }
//    };
//
//    @Override
//    public void init(Context context) {
//        Log.d(TAG, "init");
//        bluetoothScanner = createBluetoothScanner(context);
//        mAdapterStateChangedListeners = new HashSet<>();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
//        filter.addAction(BluetoothDevice.ACTION_FOUND);
//        context.registerReceiver(broadcastReceiver, filter);
//    }
//
//    protected BluetoothScanner createBluetoothScanner(Context context) {
//        return new NormalBluetoothScanner(context);
//    }
//
//    @Override
//    public void destroy(Context context) {
//        context.unregisterReceiver(broadcastReceiver);
//        mAdapterStateChangedListeners.clear();
//        bluetoothScanner.destroy();
//        Log.d(TAG, "destroy");
//    }
//
//    @Override
//    public boolean isSupportBluetooth() {
//        return mBluetoothAdapter != null;
//    }
//
//    @Override
//    public boolean openFriendly(Activity activity, int requestCode) {
//        Log.d(TAG, "openFriendly");
//        if (isSupportBluetooth()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            activity.startActivityForResult(enableBtIntent, requestCode);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean open() {
//        Log.d(TAG, "open");
//        return isSupportBluetooth() && mBluetoothAdapter.enable();
//    }
//
//    @Override
//    public boolean close() {
//        Log.d(TAG, "close");
//        return isSupportBluetooth() && mBluetoothAdapter.disable();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        Log.d(TAG, "isEnabled");
//        return isSupportBluetooth() && mBluetoothAdapter.isEnabled();
//    }
//
//    @Override
//    public int getAdapterState() {
//        Log.d(TAG, "getAdapterState");
//        return isSupportBluetooth() ? mBluetoothAdapter.getState() : -1;
//    }
//
//    @Override
//    public boolean registerStateChangedListener(AdapterStateChangedListener listener) {
//        Log.d(TAG, "registerStateChangedListener, listener=" + listener);
//        return isSupportBluetooth() && mAdapterStateChangedListeners.add(listener);
//    }
//
//    @Override
//    public boolean unregisterStateChangedListener(AdapterStateChangedListener listener) {
//        Log.d(TAG, "unregisterStateChangedListener, listener=" + listener);
//        return isSupportBluetooth() && mAdapterStateChangedListeners.remove(listener);
//    }
//
//    @Override
//    public void startDevicesDiscovery(String[] serviceUUIDs, AsyncReturnCallback returnCallback) {
//        Log.d(TAG, "startDevicesDiscovery");
//        if (isSupportBluetooth()) {
//            bluetoothScanner.startScan(null, returnCallback);
//        } else {
//            if (returnCallback != null) {
//                returnCallback.onReturn(false);
//            }
//        }
//    }
//
//    @Override
//    public void stopDevicesDiscovery(AsyncReturnCallback returnCallback) {
//        Log.d(TAG, "stopDevicesDiscovery");
//        if (isSupportBluetooth()) {
//            bluetoothScanner.stopScan(returnCallback);
//        } else {
//            if (returnCallback != null) {
//                returnCallback.onReturn(false);
//            }
//        }
//    }
//
//    @Override
//    public boolean isDiscovering() {
//        return isSupportBluetooth() && mBluetoothAdapter.isDiscovering();
//    }
//
//    @Override
//    public Set<AnkerBoxBluetoothDevice> getDevices() {
//        Log.d(TAG, "getDevices");
//        if (isSupportBluetooth()) {
//            return bluetoothScanner.getFoundedBluetoothDevices();
//        }
//        return null;
//    }
//
//    @Override
//    public boolean addDeviceFoundListener(DeviceFoundListener listener) {
//        Log.d(TAG, "addDeviceFoundListener, listener=" + listener);
//        return isSupportBluetooth() && bluetoothScanner.addDeviceFoundListener(listener);
//    }
//
//    @Override
//    public boolean removeDeviceFoundListener(DeviceFoundListener listener) {
//        Log.d(TAG, "removeDeviceFoundListener, listener=" + listener);
//        return isSupportBluetooth() && bluetoothScanner.removeDeviceFoundListener(listener);
//    }
//
//    @Override
//    public Set<AnkerBoxBluetoothDevice> getConnectedDevicesInfo(String[] uuids) {
//        Log.d(TAG, "getConnectedDevicesInfo, uuids=" + Arrays.toString(uuids));
//        if (isSupportBluetooth()) {
//            Set<AnkerBoxBluetoothDevice> devices = new HashSet<>();
//            for (String uuid : uuids) {
//                BluetoothDevice bondedDevice = mBluetoothAdapter.getRemoteDevice(uuid);
//                devices.add(bluetoothScanner.buildDevice(bondedDevice));
//            }
//            return devices;
//        }
//        return null;
//    }
//
//}
