//package com.ankerboxmanager.bluetooth.impl;
//
//import static com.ankerboxmanager.bluetooth.impl.AnkerBoxBluetoothHelper.setNotify;
//import static com.ankerboxmanager.bluetooth.impl.AnkerBoxBluetoothHelper.uuidFromString;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import com.ankerboxmanager.bluetooth.IBluetoothLe;
//import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothGattCharacteristic;
//import com.ankerboxmanager.bluetooth.bean.AnkerBoxBluetoothLeService;
//import com.ankerboxmanager.bluetooth.scaner.BluetoothScanner;
//import com.ankerboxmanager.bluetooth.scaner.LegacyLeBluetoothScanner;
//import com.ankerboxmanager.bluetooth.scaner.LollipopLeBluetoothScanner;
//
//import android.annotation.TargetApi;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattService;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.util.Base64;
//import android.util.Log;
//
///**
// * Created on 21/03/2018.
// *
// * @author lx
// */
//
//@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
//public class AnkerBoxBluetoothLe extends AnkerBoxBluetooth implements IBluetoothLe {
//
//    private boolean isSupportLe;
//
//    private BluetoothGatt mBluetoothGatt;
//
//    private ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//    private GattCallbackImpl gattCallback;
//
//    @Override
//    public void init(Context context) {
//        super.init(context);
//        Log.d(TAG, "le init");
//        gattCallback = new GattCallbackImpl();
//        isSupportLe = isSupportBluetooth() && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
//    }
//
//    @Override
//    protected BluetoothScanner createBluetoothScanner(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            return new LollipopLeBluetoothScanner(context);
//        } else {
//            return new LegacyLeBluetoothScanner(context);
//        }
//    }
//
//    @Override
//    public void destroy(Context context) {
//        super.destroy(context);
//        if (gattCallback != null) {
//            gattCallback.destroy();
//            gattCallback = null;
//        }
//        if (mBluetoothGatt != null) {
//            mBluetoothGatt.disconnect();
//            mBluetoothGatt.close();
//            mBluetoothGatt = null;
//        }
//        if (executorService != null) {
//            executorService.shutdown();
//            executorService = null;
//        }
//        Log.d(TAG, "le destroy");
//    }
//
//    @Override
//    public boolean isSupportLe() {
//        return isSupportLe;
//    }
//
//    @Override
//    public void startDevicesDiscovery(String[] serviceUUIDs, AsyncReturnCallback returnCallback) {
//        if (isSupportLe()) {
//            super.startDevicesDiscovery(serviceUUIDs, returnCallback);
//        } else {
//            if (returnCallback != null) {
//                returnCallback.onReturn(false);
//            }
//        }
//    }
//
//    @Override
//    public void stopDevicesDiscovery(AsyncReturnCallback returnCallback) {
//        if (isSupportLe()) {
//            super.stopDevicesDiscovery(returnCallback);
//        } else {
//            if (returnCallback != null) {
//                returnCallback.onReturn(false);
//            }
//        }
//    }
//
//    @Override
//    public void connectGatt(final Context context, final String deviceId, final AsyncReturnCallback returnCallback) {
//        Log.d(TAG, "connectGatt, deviceId=" + deviceId);
//        if (isSupportLe()) {
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    BluetoothDevice bondedDevice = mBluetoothAdapter.getRemoteDevice(deviceId);
//                    mBluetoothGatt = bondedDevice.connectGatt(context, true, gattCallback);
//                    gattCallback.setGattConnect(mBluetoothGatt, returnCallback);
//                }
//            });
//        }
//    }
//
//    @Override
//    public boolean disconnectGatt(Context context, String deviceId) {
//        Log.d(TAG, "disconnectGatt, deviceId=" + deviceId);
//        if (mBluetoothGatt != null) {
//            mBluetoothGatt.disconnect();
//            mBluetoothGatt.close();
//            mBluetoothGatt = null;
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean addLeStateChangedListener(StateChangedListener listener) {
//        Log.d(TAG, "addLeStateChangedListener, listener=" + listener);
//        return gattCallback.addLeStateChangedListener(listener);
//    }
//
//    @Override
//    public boolean removeLeStateChangedListener(StateChangedListener listener) {
//        Log.d(TAG, "removeLeStateChangedListener, listener=" + listener);
//        return gattCallback.removeLeStateChangedListener(listener);
//    }
//
//    @Override
//    public List<AnkerBoxBluetoothLeService> getServices(Context context, String deviceId) {
//        Log.d(TAG, "getServices, deviceId = " + deviceId);
//        if (mBluetoothGatt != null) {
//            List<AnkerBoxBluetoothLeService> services = new ArrayList<>();
//            List<BluetoothGattService> gattServices = mBluetoothGatt.getServices();
//            Log.d(TAG, "gattServices.size = " + gattServices.size());
//            for (BluetoothGattService gattService : gattServices) {
//                AnkerBoxBluetoothLeService service = new AnkerBoxBluetoothLeService();
//                service.uuid = gattService.getUuid();
//                service.isPrimary = gattService.getType() == BluetoothGattService.SERVICE_TYPE_PRIMARY;
//                Log.d(TAG, "service.uuid = " + service.uuid + "service.isPrimary = " + service.isPrimary);
//                services.add(service);
//            }
//            return services;
//        }
//        return null;
//    }
//
//    @Override
//    public List<AnkerBoxBluetoothGattCharacteristic> getDeviceCharacteristics(Context context, String deviceId,
//                                                                              String serviceId) {
//        Log.d(TAG, "getDeviceCharacteristics deviceId= " + deviceId + ", serviceId = " + serviceId);
//        if (mBluetoothGatt != null) {
//            List<AnkerBoxBluetoothGattCharacteristic> characteristicList = new ArrayList<>();
//            BluetoothGattService gattService = mBluetoothGatt.getService(uuidFromString(serviceId));
//            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
//            Log.d(TAG, "gattCharacteristics.size = " + gattCharacteristics.size());
//            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
//                AnkerBoxBluetoothGattCharacteristic characteristic = new AnkerBoxBluetoothGattCharacteristic();
//                characteristic.uuid = gattCharacteristic.getUuid();
//                int properties = gattCharacteristic.getProperties();
//                characteristic.characteristic = new AnkerBoxBluetoothGattCharacteristic.Characteristic();
//                characteristic.characteristic.read = properties & BluetoothGattCharacteristic.PROPERTY_READ;
//                characteristic.characteristic.write = (properties & BluetoothGattCharacteristic.PROPERTY_WRITE)
//                        | (properties & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) ;
//                characteristic.characteristic.notify = properties & BluetoothGattCharacteristic.PROPERTY_NOTIFY;
//                characteristic.characteristic.indicate = properties & BluetoothGattCharacteristic.PROPERTY_INDICATE;
//                characteristicList.add(characteristic);
//                Log.d(TAG, characteristic.toString());
//            }
//            return characteristicList;
//        }
//        return null;
//    }
//
//    @Override
//    public boolean readCharacteristicValue(Context context, String deviceId, String serviceId, String characteristicId) {
//        Log.d(TAG, "readCharacteristicValue deviceId= " + deviceId + ", serviceId = " + serviceId + ", characteristicId = " + characteristicId);
//        if (mBluetoothGatt != null) {
//            BluetoothGattService gattService = mBluetoothGatt.getService(uuidFromString(serviceId));
//            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(uuidFromString(characteristicId));
//            return mBluetoothGatt.readCharacteristic(characteristic);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean writeCharacteristicValue(Context context, String deviceId, String serviceId, String characteristicId,
//                                            String value) {
//        Log.d(TAG, "writeCharacteristicValue deviceId= " + deviceId + ", serviceId = " + serviceId
//                + ", characteristicId = " + characteristicId + ", value = " + value);
//        if (mBluetoothGatt != null) {
//            BluetoothGattService gattService = mBluetoothGatt.getService(uuidFromString(serviceId));
//            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(uuidFromString(characteristicId));
//            characteristic.setValue(Base64.decode(value, Base64.DEFAULT));
//            characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
//            return mBluetoothGatt.writeCharacteristic(characteristic);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean notifyCharacteristicValueChange(Context context, String deviceId, String serviceId,
//                                                   String characteristicId, int state) {
//        Log.d(TAG, "notifyCharacteristicValueChange deviceId= " + deviceId + ", serviceId = " + serviceId
//                + ", characteristicId = " + characteristicId + ", state = " + state);
//        if (mBluetoothGatt != null) {
//           return setNotify(mBluetoothGatt, uuidFromString(serviceId), uuidFromString(characteristicId), state == 1);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean addCharacteristicValueChangedListener(CharacteristicValueChangedListener listener) {
//        Log.d(TAG, "addCharacteristicValueChangedListener, listener=" + listener);
//        return gattCallback.addCharacteristicValueChangedListener(listener);
//    }
//
//    @Override
//    public boolean removeCharacteristicValueChangedListener(CharacteristicValueChangedListener listener) {
//        Log.d(TAG, "removeCharacteristicValueChangedListener, listener=" + listener);
//        return gattCallback.removeCharacteristicValueChangedListener(listener);
//    }
//
//
//
//}
