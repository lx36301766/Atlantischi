package pl.atlantischi.kotlinandroid.singleton

/**
 * Created on 21/08/2018.

 * @author lx

    双重校验锁式, java代码实现:

    public class SingletonDemo {

        private volatile static SingletonDemo instance;

        private SingletonDemo(){}

        public static SingletonDemo getInstance(){
            if(instance==null){
                synchronized (SingletonDemo.class){
                    if(instance==null){
                        instance=new SingletonDemo();
                    }
                }
            }
            return instance;
        }
    }

 */

class Singleton4 private constructor() {

    companion object {
        val instance: Singleton4 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Singleton4() }
    }

    fun foo(){}

}
