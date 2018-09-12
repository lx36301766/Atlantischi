package pl.atlantischi.ktutil.test

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import pl.atlantischi.ktutil.extension.*

/**
 * Created on 29/08/2018.

 * @author lx
 */

fun main(args: Array<String>) {

    val activity = Activity()

    activity.startActivity<FragmentActivity>()
    activity.startActivity<AppCompatActivity>{
//        action = Intent.ACTION_MAIN,
//        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP,
//        data = Uri.parse("file://xxxx"),
//        type = "text/plain",
        putExtra("", "")
    }
//    activity.launchIntent(
//            action = Intent.ACTION_MAIN,
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP,
//            data = Uri.parse("file://xxxx"),
//            type = "text/plain",
//            categories = Intent.CATEGORY_APP_BROWSER
//    ) {
//        putExtra("", "")
//    }
    activity.startActivity {
        action = Intent.ACTION_MAIN
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        data = Uri.parse("file://xxxx")
        type = "text/plain"
        addCategory(Intent.CATEGORY_APP_BROWSER)
        putExtra("", "")
    }

    activity.startActivity(Intent().apply{
        action = Intent.ACTION_MAIN
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        data = Uri.parse("file://xxxx")
        type = "text/plain"
        addCategory(Intent.CATEGORY_APP_BROWSER)
        putExtra("", "")
    })

    activity.TAG
}

class Foo {

    fun foo() {
        Log.d(TAG, "")

    }

}
