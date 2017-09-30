package util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap

/**
 * Created by xuyisheng on 15/10/30.
 * Version 1.0
 */
class ShortcutUtils @Throws(InstantiationException::class)
private constructor() {

    init {
        throw InstantiationException("This class is not for instantiation")
    }

    companion object {

        // Action 添加Shortcut
        val ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT"
        // Action 移除Shortcut
        val ACTION_REMOVE_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT"

        /**
         * 添加快捷方式
         *
         * @param context      context
         * @param actionIntent 要启动的Intent
         * @param name         name
         * @param allowRepeat  是否允许重复
         * @param iconBitmap   快捷方式图标
         */
        fun addShortcut(context: Context, actionIntent: Intent, name: String,
                        allowRepeat: Boolean, iconBitmap: Bitmap) {
            val addShortcutIntent = Intent(ACTION_ADD_SHORTCUT)
            // 是否允许重复创建
            addShortcutIntent.putExtra("duplicate", allowRepeat)
            // 快捷方式的标题
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name)
            // 快捷方式的图标
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmap)
            // 快捷方式的动作
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent)
            context.sendBroadcast(addShortcutIntent)
        }

        /**
         * 添加快捷方式
         *
         * @param context      context
         * @param actionIntent 要启动的Intent
         * @param name         name
         * @param allowRepeat  是否允许重复
         * @param iconRes   快捷方式图标
         */
        fun addShortcut(context: Context, actionIntent: Intent, name: String,
                        allowRepeat: Boolean, iconRes: Intent.ShortcutIconResource) {
            val addShortcutIntent = Intent(ACTION_ADD_SHORTCUT)
            // 是否允许重复创建
            addShortcutIntent.putExtra("duplicate", allowRepeat)
            // 快捷方式的标题
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name)
            // 快捷方式的图标
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes)
            // 快捷方式的动作
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent)
            context.sendBroadcast(addShortcutIntent)
        }

        /**
         * 移除快捷方式
         *
         * @param context      context
         * @param actionIntent 要启动的Intent
         * @param name         name
         */
        fun removeShortcut(context: Context, actionIntent: Intent, name: String) {
            val intent = Intent(ACTION_REMOVE_SHORTCUT)
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name)
            //        intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.putExtra("duplicate", false)
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent)
            context.sendBroadcast(intent)
        }
    }

}
