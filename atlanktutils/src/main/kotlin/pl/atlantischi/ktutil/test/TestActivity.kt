package pl.atlantischi.ktutil.test

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import pl.atlantischi.ktutil.extension.bindView
import pl.atlantischi.ktutil.extension.findView
import pl.atlantischi.ktutil.R
import pl.atlantischi.ktutil.extension.TAG

/**
 * Created on 30/08/2018.

 * @author lx
 */

class TestActivity : Activity() {

    val tv by bindView<TextView>(R.id.tv_test)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv2 = findView<TextView>(R.id.tv_test)
        Log.d(TAG, "tv=$tv, tv2=$tv2")
        tv?.text = ""

    }

}
