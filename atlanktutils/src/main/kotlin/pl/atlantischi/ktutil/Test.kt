package pl.atlantischi.ktutil

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created on 29/08/2018.

 * @author lx
 */

fun main(args: Array<String>) {

    val activity = Activity()

    activity.launchActivity<FragmentActivity>()
    activity.launchActivity<AppCompatActivity>(intercept = { intent ->
        intent.putExtra("", "")
    })
    activity.launchIntent(action = "")

    activity.TAG
}

class Foo {

    fun foo() {
        Log.d(TAG, "")
    }

}