package pl.atlantischi.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgorithmTest {

    public static void main(String[] args) {
        //        List<Integer> num = getNum(1, 100);
        //        for (Integer n : num) {
        //            System.out.print(n+" ");
        //        }

        //        System.out.println(System.currentTimeMillis());
        //        System.out.println(fibonacci(45));
        //        System.out.println(System.currentTimeMillis());
        //        System.out.println(fibonacci2(45));
        //        System.out.println(System.currentTimeMillis());
        //        f3();

        print99();

        int a = 1;
        Object result = a++ + 3 << 2;
        System.out.println(result); //16

        a = 2;
        result = (a++ > 2) ? (++a) : (a += 3);
        System.out.println(result); //6

        a = 1234567;
        int b = 0x06;
        result = a & b;
        System.out.println(result); //6

        Integer c = 128;
        Integer d = 128;
        result = c == d;
        System.out.println(result); //false

        result = func("luoxuan");
        System.out.println(result); //

        result = (byte) 0xffeffefe;
        System.out.println(result); //

        //        result = [(0xfe2baf & 0xf) | 0xff] >>2 / (4 << 1);
        //        System.out.println(result);

        String pathA = "/a/b/c/d/g/m/1.txt";
        String pathB = "/c/b/c/d/g/h/2.txt";
        System.out.println(pathARelativePathBRecursion(pathA, pathB, ""));
    }

    /**
     * pathA相对于pathB的相对路径 递归算法:
     *
     * @param pathA
     * @param pathB
     * @param i
     *
     * @return
     */
    public static String pathARelativePathBRecursion(String pathA, String pathB, String tempPath) {
        if (pathA.startsWith(pathB)) {
            return pathA.replaceFirst(pathB + "/", tempPath.substring(0, tempPath.length() - 3));
        } else {
            return pathARelativePathBRecursion(pathA, pathB.substring(0, pathB.lastIndexOf("/")), "../" + tempPath);
        }
    }

    //字符串倒叙
    static String func(String s) {
        return s.length() > 0 ? func(s.substring(1)) + s.charAt(0) : "";
    }

    //    private static void printSameLetter(String strA, String strB) {
    //        int lengthA = strA.length();
    //        int lengthB = strB.length();
    //        for (int i = 0; i < lengthA; i++) {
    //            char charA =strA.charAt(i);
    //            for (int j = 0; j < lengthB; j++) {
    //                char charB =strA.charAt(j);
    //                if (charA == charB) {
    //
    //                }
    //            }
    //        }
    //    }

    private static void print99() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                //                System.out.print(i * j +" ");/**/
                System.out.printf("%dx%d=%2d  ", j, i, i * j);
            }
            System.out.println();
        }
    }

    private static void f3() {
        int b = 1;
        for (int a = 0; a <= 9; a++) {
            for (int c = 0; c <= 9; c++) {
                if (a + c == 13) {
                    System.out.println(a + "" + b + "" + c);
                }
            }
        }
    }

    //    public static int[] openLight() {
    //        int[100]
    //        return null;
    //    }

    public static long getStepNumber(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (n == 3) {
            return 4;
        }
        if (n > 3) {
            return getStepNumber(n - 1) + getStepNumber(n - 2) + getStepNumber(n - 3);
        }
        return 0;
    }

    //斐波那契数列（耗时）
    public static int fibonacci(int n) {
        if (n < 2) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    //斐波那契数列
    public static int fibonacci2(int n) {
        //        int i = 0, j = 1;
        //        for (; n > 0; n--) {
        //            j += i;
        //            i = j - i;
        //        }

        int f = 0, s = 1;
        for (int k = 0; k < n; k++) {
            s += f;
            f = s - f;
        }
        return f;
    }

    /**
     * 跳台阶
     */
    public static int jumpFloorII(int target) {
        if (target <= 1) {
            return 1;
        }
        return 1 << (target - 1);
    }

    /**
     * 求素数
     */
    private static List<Integer> getNum(int from, int to) {
        if (from < 1 || from >= to) {
            return null;
        }
        List<Integer> prime = new ArrayList<>();
        start:
        for (int i = from; i <= to; i++) {
            if (i == 2) {
                prime.add(i);
            }
            if (i == 1 || i % 2 == 0) {
                continue;
            }
            for (int j = 3; j <= Math.sqrt(i); j += 2) {
                if (i % j == 0) {
                    continue start;
                }
            }
            prime.add(i);
        }

        return prime;
    }

    /**
     * 假如所求素数很大时，此方法效率很高
     */
    public static List<Integer> getPrimeNumber(int from, int to) {
        if (from <= 0 || from >= to) {
            return null;
        }
        List<Integer> prime = new ArrayList<Integer>();
        // 首先定义一个比所求素数大1的数组
        boolean result[] = new boolean[to + 1];
        // 排除偶数
        for (int i = 2; i <= to; i++) {
            result[i] = i % 2 != 0;
        }
        // 排除所有从3到开平方数的倍数
        for (int i = 3; i <= Math.sqrt(to); i++) {
            if (result[i]) {
                for (int j = i + i; j < to; j += i) {
                    result[j] = false;
                }
            }
        }

        for (int i = from; i <= to; i++) {
            if (result[i]) {
                prime.add(i);
            }
        }
        return prime;
    }
}
