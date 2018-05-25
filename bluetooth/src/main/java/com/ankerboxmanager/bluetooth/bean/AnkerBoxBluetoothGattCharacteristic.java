//package com.ankerboxmanager.bluetooth.bean;
//
//import java.util.UUID;
//
//import com.alibaba.fastjson.annotation.JSONField;
//
///**
// * Created on 21/03/2018.
// *
// * @author lx
// */
//
//public class AnkerBoxBluetoothGattCharacteristic {
//
//    public UUID uuid;
//
//    @JSONField(name = "properties")
//    public Characteristic characteristic;
//
//    public static class Characteristic {
//        public int read;
//        public int write;
//        public int notify;
//        public int indicate;
//
//        @Override
//        public String toString() {
//            return "Characteristic{" +
//                    "read=" + read +
//                    ", write=" + write +
//                    ", notify=" + notify +
//                    ", indicate=" + indicate +
//                    '}';
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "AnkerBoxBluetoothGattCharacteristic{" +
//                "uuid=" + uuid +
//                ", characteristic=" + characteristic +
//                '}';
//    }
//}
