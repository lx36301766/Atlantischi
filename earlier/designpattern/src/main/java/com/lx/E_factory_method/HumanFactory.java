package com.lx.E_factory_method;

import java.util.List;
import java.util.Random;

/**
 * 生产人类的工厂
 */
public class HumanFactory {

    // 定义一个烤箱，泥巴塞进去，人就出来，这个很先进！
    public static Human createHuman(Class<?> c) {
        Human human = null;

        try {
            // 产生人类了
            human = (Human) Class.forName(c.getName()).newInstance();
        } catch (InstantiationException e) { // 要说明人类的颜色才能考啊，要黑还是要白
            System.out.println("必须指定人类的颜色");
        } catch (IllegalAccessException e) { // 定义的人类有问题，考不出来
            System.out.println("人类定义错误！");
        } catch (ClassNotFoundException e) { // 你随便说个人类，我哪么给你制造？
            System.out.println("混蛋，你指定的人类找不到！");
        }

        /*
         * 工厂模式还有一个很重要的应用，延迟初始化，什么叫延迟初始化？
         * 一个对象初始化完毕后就不释放，等到再次用到就不用再次初始化了，直接从内存中拿到就可以了 如下： private static
         * HashMap<String, Human> humans = new HashMap<String, Human>();
         * 
         * if (humans.containsKey(c.getSimpleName())) { human =
         * humans.get(c.getSimpleName()); } else { human =
         * Class.forName(c.getName()).newInstance();
         * humans.put(c.getSimpleName(), human); }
         */
        return human;
    }

    // 女娲做累了，直接把一团泥巴塞到八卦炉，该产生啥人类就啥人类
    public static Human createHuman() {
        Human human = null;
        List<Class> concreteHumanList = ClassUtils.getAllClassByInterface(Human.class); // 定义了多少人类
        // 八卦炉自己开始想烧出什么人就什么人
        Random ran = new Random();
        int rand = ran.nextInt(concreteHumanList.size());
        human = createHuman(concreteHumanList.get(rand));
        return human;
    }
}
