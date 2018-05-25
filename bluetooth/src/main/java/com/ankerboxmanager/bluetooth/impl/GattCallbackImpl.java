//package com.ankerboxmanager.bluetooth.impl;
//
//import static com.ankerboxmanager.bluetooth.IBluetooth.TAG;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//
//import com.ankerboxmanager.bluetooth.IBluetooth;
//import com.ankerboxmanager.bluetooth.IBluetoothLe;
//
//import android.annotation.TargetApi;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.os.Build;
//import android.util.Base64;
//import android.util.Log;
//
///**
// * Created on 15/05/2018.
// *
// * @author lx
// */
//
//@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
//public class GattCallbackImpl extends BluetoothGattCallback {
//
//    private BluetoothGatt mBluetoothGatt;
//    private IBluetooth.AsyncReturnCallback connectCallback;
//
//    private Set<IBluetoothLe.StateChangedListener> mStateChangedListeners;
//    private Set<IBluetoothLe.CharacteristicValueChangedListener> mCharacteristicValueChangedListeners;
//
//    GattCallbackImpl() {
//        mStateChangedListeners = new HashSet<>();
//        mCharacteristicValueChangedListeners = new HashSet<>();
//    }
//
//    public boolean addLeStateChangedListener(IBluetoothLe.StateChangedListener listener) {
//        return mStateChangedListeners.add(listener);
//    }
//
//    public boolean removeLeStateChangedListener(IBluetoothLe.StateChangedListener listener) {
//        return mStateChangedListeners.remove(listener);
//    }
//
//    public boolean addCharacteristicValueChangedListener(IBluetoothLe.CharacteristicValueChangedListener listener) {
//        return mCharacteristicValueChangedListeners.add(listener);
//    }
//
//    public boolean removeCharacteristicValueChangedListener(IBluetoothLe.CharacteristicValueChangedListener listener) {
//        return mCharacteristicValueChangedListeners.remove(listener);
//    }
//
//    public void setGattConnect(BluetoothGatt bluetoothGatt, IBluetooth.AsyncReturnCallback connectCallback) {
//        this.mBluetoothGatt = bluetoothGatt;
//        this.connectCallback = connectCallback;
//    }
//
//    public void destroy() {
//        mStateChangedListeners.clear();
//        mCharacteristicValueChangedListeners.clear();
//        mBluetoothGatt = null;
//        connectCallback = null;
//    }
//
//    @Override
//    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
//        super.onConnectionStateChange(gatt, status, newState);
//        Log.d(TAG, "onConnectionStateChange, status=" + status + ", newState=" + newState);
//        if (newState == BluetoothGatt.STATE_CONNECTED) {
//            gatt.discoverServices();
//        }
//        for (IBluetoothLe.StateChangedListener listener : mStateChangedListeners) {
//            listener.onChanged(gatt.getDevice().getAddress(), newState);
//        }
//    }
//
//    @Override
//    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//        super.onCharacteristicChanged(gatt, characteristic);
//        String deviceId = gatt.getDevice().getAddress();
//        UUID serviceId = characteristic.getService().getUuid();
//        UUID characteristicId = characteristic.getUuid();
//        String value = Base64.encodeToString(characteristic.getValue(), Base64.DEFAULT).replace("\n", "");
//        Log.d(TAG, "onCharacteristicChanged, deviceId=" + deviceId + ", serviceId=" + serviceId
//                + ", characteristicId=" + characteristicId + ", value=" + value);
//        for (IBluetoothLe.CharacteristicValueChangedListener listener : mCharacteristicValueChangedListeners) {
//            listener.onChanged(deviceId, serviceId.toString(), characteristicId.toString(), value);
//        }
//    }
//
//    @Override
//    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//        super.onServicesDiscovered(gatt, status);
//        boolean result = mBluetoothGatt != null;
//        Log.d(TAG, "result=" + result);
//        if (connectCallback != null) {
//            connectCallback.onReturn(result);
//            connectCallback = null;
//        }
//        Log.d(TAG, "onServicesDiscovered, status=" + status + ", deviceId=" + gatt.getDevice().getAddress());
//    }
//
//    @Override
//    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//        super.onCharacteristicRead(gatt, characteristic, status);
//        Log.d(TAG, "onCharacteristicRead, status=" + status);// + ", deviceId=" + gatt.getDevice().getAddress());
//    }
//
//    @Override
//    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//        super.onCharacteristicWrite(gatt, characteristic, status);
//        Log.d(TAG, "onCharacteristicWrite, status=" + status);// + ", deviceId=" + gatt.getDevice().getAddress());
//
////        String deviceId = gatt.getDevice().getAddress();
////        UUID serviceId = characteristic.getService().getUuid();
////        UUID characteristicId = characteristic.getUuid();
////        String value = Base64.encodeToString(characteristic.getValue(), Base64.DEFAULT);
////        Log.d(TAG, "onCharacteristicWrite, deviceId=" + deviceId + ", serviceId=" + serviceId
////                + ", characteristicId=" + characteristicId + ", value=" + value);
////        for (IBluetoothLe.CharacteristicValueChangedListener listener : mCharacteristicValueChangedListeners) {
////            listener.onChanged(deviceId, serviceId.toString(), characteristicId.toString(), value);
////        }
//    }
//
//    @Override
//    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
//        super.onDescriptorRead(gatt, descriptor, status);
//        Log.d(TAG, "onDescriptorRead, status=" + status);//+ ", deviceId=" + gatt.getDevice().getAddress());
//    }
//
//    @Override
//    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
//        super.onDescriptorWrite(gatt, descriptor, status);
//        Log.d(TAG, "onDescriptorWrite, status=" + status);// + ", deviceId=" + gatt.getDevice().getAddress());
//    }
//
//    @Override
//    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
//        super.onMtuChanged(gatt, mtu, status);
//        Log.d(TAG, "onMtuChanged, status=" + status + ", mtu=" + mtu);// + ", deviceId=" + gatt.getDevice().getAddress());
//    }
//
//    @Override
//    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
//        super.onReadRemoteRssi(gatt, rssi, status);
//        Log.d(TAG, "onReadRemoteRssi, status=" + status + ", rssi=" + rssi);// + ", deviceId=" + gatt.getDevice().getAddress());
//    }
//
//    @Override
//    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
//        super.onReliableWriteCompleted(gatt, status);
//        Log.d(TAG, "onReliableWriteCompleted, status=" + status);//+ ", deviceId=" + gatt.getDevice().getAddress());
//    }
//
//}
