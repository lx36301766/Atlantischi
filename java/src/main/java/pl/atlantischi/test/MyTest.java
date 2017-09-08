package pl.atlantischi.test;

import java.util.Random;

public class MyTest {

    public static void main(String[] args) {


        int max = 20;
        int min = 10;
        int random;
        for (int i = 0; i < 50; i++) {
            random = new Random().nextInt(max - min + 1) + min;
            System.out.print(random + "  ");
        }
        System.out.println();
        for (int i = 0; i < 50; i++) {
            random = new Random().nextInt(max - min + 1) + min;
            System.out.print(random + "  ");
        }
        System.out.println();
        for (int i = 0; i < 50; i++) {
            random = new Random().nextInt(max - min + 1) + min;
            System.out.print(random + "  ");
        }

    }

}
