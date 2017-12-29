package pl.atlantischi.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sun.misc.BASE64Decoder;

public class JavaTest {

    public static void main(String[] args) {

        System.out.println(Arrays.toString(DES3.Base64.decode("in4NpNs/E80DXXU9cn2fLA==")));
    }

    private static void add(Object... objs) {
//        if (objs == null) {
//            return;
//        }
        for (Object obj : objs) {
            System.out.println();
        }
        Collections.addAll(objList, objs);
    }

    static List<Object> objList = new ArrayList<>();

}
