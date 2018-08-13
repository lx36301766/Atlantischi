package pl.atlantischi.mapadapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import android.provider.Contacts;
import android.view.View;

public class gwfs {

    public static void main(String[] args) {
        Collections.sort(peopleList, People::sortByName);
        System.out.println("引用类的静态方法：" + peopleList);

        //第二种，引用类的实例方法
        Collections.sort(peopleList, new Contacts.People()::sortByAge);
        System.out.println("引用类的实例方法：" + peopleList);

        gwfs ff = new gwfs();
        //第三种，特定类的方法调用()
        Integer[] a = new Integer[]{3, 1, 2, 4, 6, 5};
        Arrays.sort(a, ff::compare);
        Arrays.sort(a, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });
        System.out.println("特定类的方法引用：" + Arrays.toString(a));

        //第四种，引用类的构造器
        Car car = Car.create(Car::new);
        System.out.println("引用类的构造器:" + car);

        ff.setListener(new Function<View, Boolean>() {
            @Override
            public Boolean apply(View view) {
                return null;
            }
        });
        ff.setListener(new Consumer<View>() {
            @Override
            public void accept(View view) {

            }
        });
        ff.setListener(new Supplier<View>() {
            @Override
            public View get() {
                return null;
            }
        });
    }


    public void setListener(Supplier<View> clickListener) {

    }

    public void setListener(Consumer<View> clickListener) {

    }

    public void setListener(Function<View, Boolean> clickListener) {

    }


    public int compare(int x, int y) {



        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

}
