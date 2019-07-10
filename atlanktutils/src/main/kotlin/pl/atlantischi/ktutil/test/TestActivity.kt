package pl.atlantischi.ktutil.test

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.telephony.SubscriptionManager
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.atlantischi.ktutil.R
import pl.atlantischi.ktutil.extension.*

/**
 * Created on 30/08/2018.

 * @author lx
 */

class TestActivity : Activity() {

    val tv by bindView<TextView>(R.id.tv_test)

    val myColor by bindColorRes(R.color.bind_color_test)

    val myString by bindStringRes(R.string.bind_string_test)

    val myDrawable by bindDrawableRes(android.R.drawable.alert_dark_frame)

    val dir by bindDir("/cache")

    val am by bindSystemService<ActivityManager>(Context.ACTIVITY_SERVICE)

    val pm by bindSystemService(SubscriptionManager::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val tv2 = findView<TextView>(R.id.tv_test)
//        Log.d(TAG, "tv=$tv, tv2=$tv2")

        tv?.run {
//            setOnClickListener {
//                text = myString
//                setTextColor(myColor)
//            }
        }

        pm?.let{

        }

        startActivity<AppCompatActivity> {
            action = Intent.ACTION_MAIN
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            data = Uri.parse("file://xxxx")
            type = "text/plain"
            addCategory(Intent.CATEGORY_APP_BROWSER)
            putExtra("", "")
        }

        startIntent {
            action = Intent.ACTION_MAIN
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            data = Uri.parse("file://xxxx")
            type = "text/plain"
            addCategory(Intent.CATEGORY_APP_BROWSER)
            putExtra("", "")
        }

    }

}
