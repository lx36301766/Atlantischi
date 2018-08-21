package pl.atlantischi.kotlinandroid.singleton

/**
 * Created on 21/08/2018.

 * @author lx

    线程安全的懒汉式, java代码实现:

    public class SingletonDemo {

        private static SingletonDemo instance;

        private SingletonDemo(){}

        public static synchronized SingletonDemo getInstance() {
            if(instance==null){
                instance=new SingletonDemo();
            }
            return instance;
        }
    }

 */

class Singleton3 private constructor() {

    companion object {

        private var _instance: Singleton3? = null
            get() {
                if (field == null) {
                    field = Singleton3()
                }
                return field
            }

        @get:Synchronized
        val instance: Singleton3 = _instance!!
    }

    fun foo(){}

}
