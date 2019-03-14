package com.lx.testandroid.common

import java.util.ArrayList

import com.lx.testandroid.R
import com.lx.testandroid.util.ResidentNotification
import com.lx.testandroid.util.TestShortcutUtils

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View

/**
 * Created on 05/09/2017.
 *
 * @author lx
 */

class CommonActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common)
        findViewById<View>(R.id.btn_1).setOnClickListener(this)
        findViewById<View>(R.id.btn_2).setOnClickListener(this)
        findViewById<View>(R.id.btn_3).setOnClickListener(this)
        findViewById<View>(R.id.btn_4).setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_1 -> TestShortcutUtils.addLaunchIcon(this)
            R.id.btn_2 -> TestShortcutUtils.delLaunchIcon(this)
            R.id.btn_3 -> testShowNotification()
            R.id.btn_4 -> ResidentNotification.cancelNotification(this)
        }
    }

    private fun testShowNotification() {
        val dataList = ArrayList<ResidentNotification.RemoteViewsData>()
        dataList.add(ResidentNotification.RemoteViewsData("宝箱",
                BitmapFactory.decodeResource(resources, R.drawable.notification_icon_treasure_box)))
        dataList.add(ResidentNotification.RemoteViewsData("签到",
                BitmapFactory.decodeResource(resources, R.drawable.notification_icon_sign_in)))
        dataList.add(ResidentNotification.RemoteViewsData("搜索",
                BitmapFactory.decodeResource(resources, R.drawable.notification_icon_search)))
        dataList.add(ResidentNotification.RemoteViewsData("聚美",
                BitmapFactory.decodeResource(resources, R.drawable.notification_icon_jumei)))
        ResidentNotification.setNotification(this, dataList)
    }

}
