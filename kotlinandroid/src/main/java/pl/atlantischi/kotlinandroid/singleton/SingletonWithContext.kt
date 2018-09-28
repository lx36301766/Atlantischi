package pl.atlantischi.kotlinandroid.singleton

import android.content.Context

/**
 * Created on 27/09/2018.
 *
 * @author lx
 *
    双重校验锁式, 带参数的java代码实现

    public class SingletonDemo {

        private volatile static SingletonDemo instance;

        private SingletonDemo(Context context){}

        public static SingletonDemo getInstance(Context context){
            if(instance==null){
                synchronized (SingletonDemo.class){
                    if(instance==null){
                        instance=new SingletonDemo(context);
                    }
                }
            }
            return instance;
        }
    }

 */

class SingletonWithContext private constructor(context: Context) {

    companion object {

        @Volatile
        private var instance: SingletonWithContext? = null

        fun getInstance(context: Context): SingletonWithContext {
            return instance ?: synchronized(this) {
                instance ?: SingletonWithContext(context) }
        }

    }

    fun foo(){}

}
