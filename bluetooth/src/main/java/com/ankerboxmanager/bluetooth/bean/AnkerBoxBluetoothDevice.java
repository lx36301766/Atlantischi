//package com.ankerboxmanager.bluetooth.bean;
//
///**
// * Created on 21/03/2018.
// *
// * @author lx
// */
//
//public class AnkerBoxBluetoothDevice {
//
//    public String name;
//
//    public String deviceId;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof AnkerBoxBluetoothDevice)) {
//            return false;
//        }
//
//        AnkerBoxBluetoothDevice that = (AnkerBoxBluetoothDevice) o;
//
//        if (name != null ? !name.equals(that.name) : that.name != null) {
//            return false;
//        }
//        return deviceId != null ? deviceId.equals(that.deviceId) : that.deviceId == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = name != null ? name.hashCode() : 0;
//        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "{" +
//                "name='" + name + '\'' +
//                ", deviceId='" + deviceId + '\'' +
//                '}';
//    }
//}
