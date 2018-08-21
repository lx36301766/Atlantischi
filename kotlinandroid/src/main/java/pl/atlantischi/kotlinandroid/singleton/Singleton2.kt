package pl.atlantischi.kotlinandroid.singleton

/**
 * Created on 21/08/2018.

 * @author lx

    懒汉式, java代码实现:

    public class SingletonDemo {

        private static SingletonDemo instance;

        private SingletonDemo(){}

        public static SingletonDemo getInstance(){
            if(instance==null){
                instance=new SingletonDemo();
            }
            return instance;
        }
    }

 */

class Singleton2 private constructor() {

    companion object {

        var _instance: Singleton2? = null
            get() {
                if (field == null) {
                    field = Singleton2()
                }
                return field
            }

        val instance: Singleton2 = _instance!!

    }

    fun foo() {}

}
