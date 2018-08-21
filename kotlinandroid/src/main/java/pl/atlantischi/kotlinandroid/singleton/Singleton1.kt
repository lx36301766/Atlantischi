package pl.atlantischi.kotlinandroid.singleton

/**
 * Created on 21/08/2018.

 * @author lx

    饿汉式, java代码实现:

    public class SingletonDemo {

        private static SingletonDemo instance=new SingletonDemo();

        private SingletonDemo(){}

        public static SingletonDemo getInstance(){
            return instance;
        }
    }

 */

object Singleton1 {

    fun foo() {}

}
