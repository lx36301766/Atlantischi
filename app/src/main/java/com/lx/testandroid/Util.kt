package com.lx.testandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentActivity

fun main(args: Array<String>) {
    val activity : Activity = Activity()
    activity.startActivity<FragmentActivity>()
}

inline fun <reified T: Activity> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}