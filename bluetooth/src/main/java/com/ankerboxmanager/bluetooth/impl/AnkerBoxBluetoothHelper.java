//package com.ankerboxmanager.bluetooth.impl;
//
//import java.util.List;
//import java.util.UUID;
//
//import com.ankerboxmanager.bluetooth.IBluetooth;
//
//import android.annotation.TargetApi;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattService;
//import android.os.Build;
//import android.util.Log;
//
///**
// * Created on 15/05/2018.
// *
// * @author lx
// */
//public class AnkerBoxBluetoothHelper {
//
//    private static final String TAG = IBluetooth.TAG;
//
//    // base UUID used to build 128 bit Bluetooth UUIDs
//    public static final String UUID_BASE = "0000XXXX-0000-1000-8000-00805f9b34fb";
//
//    // handle 16 and 128 bit UUIDs
//    public static UUID uuidFromString(String uuid) {
//        if (uuid.length() == 4) {
//            uuid = UUID_BASE.replace("XXXX", uuid);
//        }
//        try {
//            return UUID.fromString(uuid);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return UUID.fromString(UUID_BASE.replace("XXXX", "0000"));
//        }
//    }
//
//
//    private static final String CHARACTERISTIC_NOTIFICATION_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
//    public static boolean setNotify(BluetoothGatt gatt, UUID serviceUUID, UUID characteristicUUID, Boolean notify){
//        BluetoothGattService service = gatt.getService(serviceUUID);
//        BluetoothGattCharacteristic characteristic = findNotifyCharacteristic(service, characteristicUUID);
//        if (characteristic != null) {
//            if (gatt.setCharacteristicNotification(characteristic, notify)) {
//                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(uuidFromString(CHARACTERISTIC_NOTIFICATION_CONFIG));
//                if (descriptor != null) {
//                    // Prefer notify over indicate
//                    if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
//                        Log.d(TAG, "Characteristic " + characteristicUUID + " set NOTIFY");
//                        descriptor.setValue(notify ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
//                    } else if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
//                        Log.d(TAG, "Characteristic " + characteristicUUID + " set INDICATE");
//                        descriptor.setValue(notify ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
//                    } else {
//                        Log.d(TAG, "Characteristic " + characteristicUUID + " does not have NOTIFY or INDICATE property set");
//                    }
//                    try {
//                        if (gatt.writeDescriptor(descriptor)) {
//                            Log.d(TAG, "setNotify complete");
//                            return true;
//                        } else {
//                            Log.d(TAG, "Failed to set client characteristic notification for " + characteristicUUID);
//                        }
//                    } catch (Exception e) {
//                        Log.d(TAG, "Failed to set client characteristic notification for " + characteristicUUID + ", error: " + e.getMessage());
//                    }
//                } else {
//                    Log.d(TAG, "Set notification failed for " + characteristicUUID);
//                }
//            } else {
//                Log.d(TAG, "Failed to register notification for " + characteristicUUID);
//            }
//        } else {
//            Log.d(TAG, "Characteristic " + characteristicUUID + " not found");
//        }
//        return false;
//    }
//
//    // Some devices reuse UUIDs across characteristics, so we can't use service.getCharacteristic(characteristicUUID)
//    // instead check the UUID and properties for each characteristic in the service until we find the best match
//    // This function prefers Notify over Indicate
//    private static BluetoothGattCharacteristic findNotifyCharacteristic(BluetoothGattService service,
//                                                                        UUID characteristicUUID) {
//        BluetoothGattCharacteristic characteristic = null;
//        try {
//            // Check for Notify first
//            List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
//            for (BluetoothGattCharacteristic c : characteristics) {
//                if ((c.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0 && characteristicUUID.equals(c.getUuid())) {
//                    characteristic = c;
//                    break;
//                }
//            }
//            if (characteristic != null) return characteristic;
//            // If there wasn't Notify Characteristic, check for Indicate
//            for (BluetoothGattCharacteristic c : characteristics) {
//                if ((c.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0 && characteristicUUID.equals(c.getUuid())) {
//                    characteristic = c;
//                    break;
//                }
//            }
//            // As a last resort, try and find ANY characteristic with this UUID, even if it doesn't have the correct properties
//            if (characteristic == null) {
//                characteristic = service.getCharacteristic(characteristicUUID);
//            }
//            return characteristic;
//        }catch (Exception e) {
//            Log.e(TAG, "Errore su caratteristica " + characteristicUUID, e);
//            return null;
//        }
//    }
//
//}
