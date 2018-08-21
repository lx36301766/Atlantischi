package pl.atlantischi.kotlinandroid.singleton

/**
 * Created on 21/08/2018.

 * @author lx

    静态内部类式, java代码实现:

    public class SingletonDemo {

        private static class SingletonHolder{
            private static SingletonDemo instance=new SingletonDemo();
        }

        private SingletonDemo() {}

        public static SingletonDemo getInstance(){
            return SingletonHolder.instance;
        }
    }

 */


class Singleton5 private constructor() {

    private object SingletonHolder {
        val holder = Singleton5()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    fun foo(){}

}
