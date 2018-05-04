package com.lx.testandroid.common

import java.text.SimpleDateFormat
import java.util.Date

import com.lx.testandroid.R
import com.lx.testandroid.view.NumberTextView

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

/**
 * Created by xuanluo on 2016/12/30.
 */

class NumberTextActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.number)

        val tv = findViewById<View>(R.id.num) as NumberTextView
        tv.setNum(0, 48546)

        val name = componentName.shortClassName
        Log.d("lx", "name=$name")
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()

        val simpleDateFormat = DateFormat.getDateFormat(this) as SimpleDateFormat
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val tvDate = findViewById<View>(R.id.date_text) as TextView
        tvDate.text = java.text.DateFormat.getDateInstance().format(Date())

        tv.setOnClickListener {
            val pm = packageManager
            pm.setComponentEnabledSetting(componentName,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
            pm.setComponentEnabledSetting(ComponentName(this@NumberTextActivity,
                    "com.lx.testandroid.MainAliasActivity"),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

            Handler().postDelayed({
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                val resolves = pm.queryIntentActivities(intent, 0)
                for (res in resolves) {
                    if (res.activityInfo != null) {
                        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                        am.killBackgroundProcesses(res.activityInfo.packageName)
                    }
                }
            }, 1000)
        }
    }

}
