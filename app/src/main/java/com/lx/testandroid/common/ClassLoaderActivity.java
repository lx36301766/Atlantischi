package com.lx.testandroid.common;

import com.lx.testandroid.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * Created on 20/05/2017.
 *
 * @author lx
 */

public class ClassLoaderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("DEMO", "Context的类加载加载器:" + Context.class.getClassLoader());
        Log.i("DEMO", "ListView的类加载器:" + ListView.class.getClassLoader());
        Log.i("DEMO", "应用程序默认加载器:" + getClassLoader());
        Log.i("DEMO", "系统类加载器:" + ClassLoader.getSystemClassLoader());
        Log.i("DEMO",
                "系统类加载器和Context的类加载器是否相等:" + (Context.class.getClassLoader() == ClassLoader.getSystemClassLoader()));
        Log.i("DEMO", "系统类加载器和应用程序默认加载器是否相等:" + (getClassLoader() == ClassLoader.getSystemClassLoader()));

        Log.i("DEMO", "打印应用程序默认加载器的委派机制:");
        ClassLoader classLoader = getClassLoader();
        while (classLoader != null) {
            Log.i("DEMO", "类加载器:" + classLoader);
            classLoader = classLoader.getParent();
        }

        Log.i("DEMO", "打印系统加载器的委派机制:");
        classLoader = ClassLoader.getSystemClassLoader();
        while (classLoader != null) {
            Log.i("DEMO", "类加载器:" + classLoader);
            classLoader = classLoader.getParent();
        }

    }

//    Context的类加载加载器:java.lang.BootClassLoader@416743f8
//    ListView的类加载器:java.lang.BootClassLoader@416743f8
//    应用程序默认加载器:dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/pl.atlantischi.test-2.apk"],nativeLibraryDirectories=[/data/app-lib/pl.atlantischi.test-2, /vendor/lib, /system/lib]]]
//    系统类加载器:dalvik.system.PathClassLoader[DexPathList[[directory "."],nativeLibraryDirectories=[/vendor/lib, /system/lib]]]
//    系统类加载器和Context的类加载器是否相等:false
//    系统类加载器和应用程序默认加载器是否相等:false
//    打印应用程序默认加载器的委派机制:
//    类加载器:dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/pl.atlantischi.test-2.apk"],nativeLibraryDirectories=[/data/app-lib/pl.atlantischi.test-2, /vendor/lib, /system/lib]]]
//    类加载器:java.lang.BootClassLoader@416743f8
//    打印系统加载器的委派机制:
//    类加载器:dalvik.system.PathClassLoader[DexPathList[[directory "."],nativeLibraryDirectories=[/vendor/lib, /system/lib]]]
//    类加载器:java.lang.BootClassLoader@416743f8

}
