//package com.ankerboxmanager.bluetooth.scaner;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import com.ankerboxmanager.bluetooth.IBluetooth;
//import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothDevice;
//
//import android.bluetooth.BluetoothDevice;
//import android.content.Context;
//import android.util.Log;
//
///**
// * Created on 14/05/2018.
// *
// * @author lx
// */
//public abstract class BluetoothScanner {
//
//    private static final String TAG = IBluetooth.TAG;
//
//    public BluetoothScanner(Context context) {
//        this.context = context;
//        mDeviceFoundListeners = new HashSet<>();
//        mFoundedBluetoothDevices = new HashSet<>();
//    }
//
//    protected Context context;
//
//    public abstract void startScan(String[] serviceUUIDs, IBluetooth.AsyncReturnCallback asyncReturnCallback);
//
//    public abstract void stopScan(IBluetooth.AsyncReturnCallback asyncReturnCallback);
//
//    private Set<IBluetooth.DeviceFoundListener> mDeviceFoundListeners;
//
//    private Set<AnkerBoxBluetoothDevice> mFoundedBluetoothDevices;
//
//    public boolean addDeviceFoundListener(IBluetooth.DeviceFoundListener listener) {
//        return mDeviceFoundListeners.add(listener);
//    }
//
//    public boolean removeDeviceFoundListener(IBluetooth.DeviceFoundListener listener) {
//        return mDeviceFoundListeners.remove(listener);
//    }
//
//    public void destroy() {
//        mDeviceFoundListeners.clear();
//        mFoundedBluetoothDevices.clear();
//        context = null;
//    }
//
//    public Set<AnkerBoxBluetoothDevice> getFoundedBluetoothDevices() {
//        return mFoundedBluetoothDevices;
//    }
//
//    void onNewDeviceFound(BluetoothDevice bondedDevice) {
//        AnkerBoxBluetoothDevice abDevice = buildDevice(bondedDevice);
//        mFoundedBluetoothDevices.add(abDevice);
//        for (IBluetooth.DeviceFoundListener listener : mDeviceFoundListeners) {
//            listener.onNewDeviceFound(abDevice);
//        }
//        Log.d(TAG, this.getClass().getSimpleName() + ", onNewDeviceFound, abDevice=" + abDevice);
//    }
//
//    public AnkerBoxBluetoothDevice buildDevice(BluetoothDevice bondedDevice) {
//        AnkerBoxBluetoothDevice device = new AnkerBoxBluetoothDevice();
//        device.name = bondedDevice.getName();
//        device.deviceId = bondedDevice.getAddress();
//        return device;
//    }
//
//}
//
