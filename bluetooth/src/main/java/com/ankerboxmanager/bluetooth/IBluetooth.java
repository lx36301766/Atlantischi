//package com.ankerboxmanager.bluetooth;
//
//import java.util.Set;
//
//import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothDevice;
//
//import android.app.Activity;
//import android.content.Context;
//
///**
// * Created on 19/03/2018.
// *
// * @author lx
// */
//
//public interface IBluetooth {
//
//    String TAG = "AnkerBoxBluetooth";
//
//    interface AsyncReturnCallback {
//        void onReturn(boolean result);
//    }
//
//    interface AdapterStateChangedListener {
//        void onChanged(int previousState, int newState);
//    }
//
//    interface DeviceFoundListener {
//        void onNewDeviceFound(AnkerBoxBluetoothDevice newDevice);
//    }
//
//    void init(Context context);
//
//    void destroy(Context context);
//
//    boolean isSupportBluetooth();
//
//    boolean openFriendly(Activity activity, int requestCode);
//
//    boolean open();
//
//    boolean close();
//
//    boolean isEnabled();
//
//    void startDevicesDiscovery(String[] serviceUUIDs, AsyncReturnCallback returnCallback);
//
//    void stopDevicesDiscovery(AsyncReturnCallback returnCallback);
//
//    boolean isDiscovering();
//
//    int getAdapterState();
//
//    boolean registerStateChangedListener(AdapterStateChangedListener listener);
//
//    boolean unregisterStateChangedListener(AdapterStateChangedListener listener);
//
//    Set<AnkerBoxBluetoothDevice> getDevices();
//
//    boolean addDeviceFoundListener(DeviceFoundListener listener);
//
//    boolean removeDeviceFoundListener(DeviceFoundListener listener);
//
//    Set<AnkerBoxBluetoothDevice> getConnectedDevicesInfo(String[] uuids);
//
//}
