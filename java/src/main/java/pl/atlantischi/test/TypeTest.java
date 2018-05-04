package pl.atlantischi.test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created on 25/04/2018.
 *
 * @author lx
 */
public class TypeTest {


    static abstract class Father {

    }

    static class Son extends Father {

    }

    static class Daughter extends Father {

    }


    interface TestInterface<F extends Father> {

        void test(F f);

    }

    public static void main(String[] args) {

        TestInterface<Daughter> testInterface = new TestInterface<Daughter>() {

            @Override
            public void test(Daughter daughter) {

            }
        };


        Type[] types = testInterface.getClass().getGenericInterfaces();
        ParameterizedType type = (ParameterizedType) types[0];
//        Object type = testInterface.getClass().get

        System.out.println(1);
    }

}
