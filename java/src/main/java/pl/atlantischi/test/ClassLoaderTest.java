package pl.atlantischi.test;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created on 20/05/2017.
 *
 * @author lx
 */

public class ClassLoaderTest {

    static class MyClassLoader extends ClassLoader {

        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

    }

//    public static void main(String args[]) throws Exception {
//        MyClassLoader classLoader =
//                new MyClassLoader("/Users/xuanluo/Develop/android/GitHub/Atlantischi/java/src/main/java");
//        Class clazz = classLoader.loadClass("pl.atlantischi.test.MyTest");
//        Object obj = clazz.newInstance();
//        Method helloMethod = clazz.getDeclaredMethod("hello123", null);
//        helloMethod.invoke(obj, null);
//
//        //输出ClassLoaderText的类加载器名称
//        System.out.println("ClassLoaderText类的加载器的名称:" + ClassLoaderTest.class.getClassLoader().getClass().getName());
//        System.out.println("System类的加载器的名称:" + System.class.getClassLoader());
//        System.out.println("List类的加载器的名称:" + List.class.getClassLoader());
//        ClassLoader cl = ClassLoaderTest.class.getClassLoader();
//        while (cl != null) {
//            System.out.print(cl.getClass().getName() + "->");
//            cl = cl.getParent();
//        }
//        System.out.println(cl);
//    }

}
