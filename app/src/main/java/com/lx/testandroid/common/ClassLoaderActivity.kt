package com.lx.testandroid.common

import com.lx.testandroid.R

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ListView

/**
 * Created on 20/05/2017.
 *
 * @author lx
 */

class ClassLoaderActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("DEMO", "Context的类加载加载器:" + Context::class.java.classLoader)
        Log.i("DEMO", "ListView的类加载器:" + ListView::class.java.classLoader)
        Log.i("DEMO", "应用程序默认加载器:$classLoader")
        Log.i("DEMO", "系统类加载器:" + ClassLoader.getSystemClassLoader())
        Log.i("DEMO",
                "系统类加载器和Context的类加载器是否相等:" + (Context::class.java.classLoader === ClassLoader.getSystemClassLoader()))
        Log.i("DEMO", "系统类加载器和应用程序默认加载器是否相等:" + (classLoader === ClassLoader.getSystemClassLoader()))

        Log.i("DEMO", "打印应用程序默认加载器的委派机制:")
        var classLoader: ClassLoader? = classLoader
        while (classLoader != null) {
            Log.i("DEMO", "类加载器:$classLoader")
            classLoader = classLoader.parent
        }

        Log.i("DEMO", "打印系统加载器的委派机制:")
        classLoader = ClassLoader.getSystemClassLoader()
        while (classLoader != null) {
            Log.i("DEMO", "类加载器:$classLoader")
            classLoader = classLoader.parent
        }

    }

}
