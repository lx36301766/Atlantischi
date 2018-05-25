//package com.ankerboxmanager.bluetooth;
//
//import java.util.List;
//
//import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothGattCharacteristic;
//import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothLeService;
//
//import android.content.Context;
//
///**
// * Created on 21/03/2018.
// *
// * @author lx
// */
//
//public interface IBluetoothLe extends IBluetooth {
//
//    interface StateChangedListener {
//        void onChanged(String deviceId, int state);
//    }
//
//    interface CharacteristicValueChangedListener {
//        void onChanged(String deviceId, String serviceId, String characteristicId, String value);
//    }
//
//    boolean isSupportLe();
//
////    void startLeDevicesDiscovery(String[] serviceUUIDs, AsyncReturnCallback returnCallback);
//    //
//    //    void stopLeDevicesDiscovery(AsyncReturnCallback returnCallback);
//
//    void connectGatt(Context context, String deviceId, AsyncReturnCallback returnCallback);
//
//    boolean disconnectGatt(Context context, String deviceId);
//
//    boolean addLeStateChangedListener(StateChangedListener listener);
//
//    boolean removeLeStateChangedListener(StateChangedListener listener);
//
//    List<AnkerBoxBluetoothLeService> getServices(Context context, String deviceId);
//
//    List<AnkerBoxBluetoothGattCharacteristic> getDeviceCharacteristics(Context context, String deviceId,
//                                                                       String serviceId);
//
//    boolean readCharacteristicValue(Context context, String deviceId, String serviceId, String characteristicId);
//
//    boolean writeCharacteristicValue(Context context, String deviceId, String serviceId, String characteristicId,
//                                     String value);
//
//    boolean notifyCharacteristicValueChange(Context context, String deviceId, String serviceId, String characteristicId,
//                                            int state);
//
//    boolean addCharacteristicValueChangedListener(CharacteristicValueChangedListener listener);
//
//    boolean removeCharacteristicValueChangedListener(CharacteristicValueChangedListener listener);
//
//}
