package pl.atlantischi.androidannotation.runtime;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import pl.atlantischi.androidannotation.runtime.inject.Event;
import pl.atlantischi.androidannotation.runtime.inject.RContentView;
import pl.atlantischi.androidannotation.runtime.inject.RInjectView;
import pl.atlantischi.androidannotation.runtime.inject.ROnClick;

/**
 * Created on 22/03/2017.
 *
 * @author lx
 */

public class XInjectManager {

    public static void init(Activity activity) {
        injectContextView(activity);
        injectView(activity);
        injectOnClickEvent(activity);
        injectEvent(activity);
    }

    private static void injectContextView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        RContentView RContentView = clazz.getAnnotation(RContentView.class);
        if (RContentView != null) {
            int layoutId = RContentView.value();
            //            activity.setContentView(layoutId);
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.setAccessible(true);
                method.invoke(activity, layoutId);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            RInjectView RInjectView = field.getAnnotation(RInjectView.class);
            if (RInjectView != null) {
                int viewId = RInjectView.value();
                if (viewId != -1) {
                    field.setAccessible(true);
                    //                    Object view = activity.findViewById(viewId);
                    //                    try {
                    //                        field.setter(activity, view);
                    //                    } catch (IllegalAccessException e) {
                    //                        e.printStackTrace();
                    //                    }
                    try {
                        Method method = clazz.getMethod("findViewById", int.class);
                        Object view = method.invoke(activity, viewId);
                        field.set(activity, view);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectOnClickEvent(final Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            ROnClick ROnClick = method.getAnnotation(ROnClick.class);
            if (ROnClick != null) {
                int[] viewIds = ROnClick.value();
                for (int viewId : viewIds) {
                    View view = activity.findViewById(viewId);
                    if (view != null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    method.invoke(activity, v);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    /**
     * 注入所有的事件
     *
     * @param activity
     */
    private static void injectEvent(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //拿到注解上的注解参数
                Event eventAnnotation = annotationType.getAnnotation(Event.class);
                if (eventAnnotation != null) {
                    //取出设置监听器的名称，监听器的类型，调用的方法名
                    String listenerSetter = eventAnnotation.setter();
                    Class<?> listenerType = eventAnnotation.type();
                    String methodName = eventAnnotation.methodName();
                    try {
                        //拿到Onclick注解中的value方法
                        Method aMethod = annotationType.getDeclaredMethod("value");
                        //取出所有的viewId
                        int[] viewIds = (int[]) aMethod.invoke(annotation);
                        //通过InvocationHandler设置代理
                        DynamicHandler handler = new DynamicHandler(activity);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class<?>[] {listenerType}, handler);
                        //遍历所有的View，设置事件
                        for (int viewId : viewIds) {
                            View view = activity.findViewById(viewId);
                            Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, listener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class DynamicHandler implements InvocationHandler {

        private WeakReference<Object> handlerRef;
        private final HashMap<String, Method> methodMap = new HashMap<>(1);

        DynamicHandler(Object handler) {
            this.handlerRef = new WeakReference<>(handler);
        }

        public void addMethod(String name, Method method) {
            methodMap.put(name, method);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            Object handler = handlerRef.get();
            if (handler != null) {
                String methodName = method.getName();
                method = methodMap.get(methodName);
                if (method != null) {
                    return method.invoke(handler, args);
                }
            }
            return null;
        }
    }
}
