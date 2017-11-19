package pl.atlantischi.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaTest {

    public static void main(String[] args) {

        add();
        add(null, null, new Object());
        add(new Object());
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
