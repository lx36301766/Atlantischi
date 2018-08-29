package pl.atlantischi.kotlinandroid.inlines

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import pl.atlantischi.kotlinandroid.R

/**
 * Created on 29/08/2018.

 * @author lx
 */

inline fun <reified T : Activity> Context.startActivity() = startActivity(Intent(this, T::class.java))

inline fun <reified T : View> Activity.findView(id: Int) = findViewById(id) as T




//fun main(args: Array<String>) {
//
//    val activity = Activity()
//
//    activity.startActivity<FragmentActivity>()
//    activity.startActivity<AppCompatActivity>()
//
//    val textview = activity.findView<TextView>(R.id.text)
//}
