package util

import java.io.ByteArrayOutputStream
import java.io.IOException

import android.content.ComponentName
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.Log

/**
 * Created by xuyisheng on 15/11/6.
 * version 1.0
 */
class ShortcutSuperUtils @Throws(InstantiationException::class)
private constructor() {

    init {
        throw InstantiationException("This class is not for instantiation")
    }

    companion object {

        /**
         * 判断快捷方式是否存在
         *
         *
         * 检查快捷方式是否存在 <br></br>
         * <font color=red>注意：</font> 有些手机无法判断是否已经创建过快捷方式<br></br>
         * 因此，在创建快捷方式时，请添加<br></br>
         * shortcutIntent.putExtra("duplicate", false);// 不允许重复创建<br></br>
         * 最好使用[.isShortCutExist]
         * 进行判断，因为可能有些应用生成的快捷方式名称是一样的的<br></br>
         *
         * @param context context
         * @param title   快捷方式名
         * @return 是否存在
         */
        fun isShortCutExist(context: Context, title: String): Boolean {
            var result = false
            try {
                val cr = context.contentResolver
                val uri = getUriFromLauncher(context)
                val c = cr.query(uri, arrayOf("title"), "title=? ", arrayOf(title), null)
                if (c != null && c.count > 0) {
                    result = true
                }
                if (c != null && !c.isClosed) {
                    c.close()
                }
            } catch (e: Exception) {
                result = false
                e.printStackTrace()
            }

            return result
        }

        /**
         * 判断快捷方式是否存在
         *
         *
         * 不一定所有的手机都有效，因为国内大部分手机的桌面不是系统原生的<br></br>
         * 更多请参考[.isShortCutExist]<br></br>
         * 桌面有两种，系统桌面(ROM自带)与第三方桌面，一般只考虑系统自带<br></br>
         * 第三方桌面如果没有实现系统响应的方法是无法判断的，比如GO桌面<br></br>
         *
         * @param context context
         * @param title   快捷方式名
         * @param intent  快捷方式Intent
         * @return 是否存在
         */
        fun isShortCutExist(context: Context, title: String, intent: Intent): Boolean {
            var result = false
            try {
                val cr = context.contentResolver
                val uri = getUriFromLauncher(context)
                val c = cr.query(uri, arrayOf("title", "intent"), "title=?  and intent=?",
                        arrayOf(title, intent.toUri(0)), null)
                if (c != null && c.count > 0) {
                    result = true
                }
                if (c != null && !c.isClosed) {
                    c.close()
                }
            } catch (ex: Exception) {
                result = false
                ex.printStackTrace()
            }

            return result
        }

        /**
         * 更新桌面快捷方式图标，不一定所有图标都有效(有可能需要系统权限)
         *
         * @param context context
         * @param title   快捷方式名
         * @param intent  快捷方式Intent
         * @param bitmap  快捷方式Icon
         */
        fun updateShortcutIcon(context: Context, title: String, intent: Intent, bitmap: Bitmap?) {
            if (bitmap == null) {
                Log.i("HJ", "update shortcut icon,bitmap empty")
                return
            }
            try {
                val cr = context.contentResolver
                val uri = getUriFromLauncher(context)
                val c = cr.query(uri, arrayOf("_id", "title", "intent"),
                        "title=?  and intent=? ",
                        arrayOf(title, intent.toUri(0)), null)
                var index = -1
                if (c != null && c.count > 0) {
                    c.moveToFirst()
                    index = c.getInt(0)//获得图标索引
                    val cv = ContentValues()
                    cv.put("icon", flattenBitmap(bitmap))
                    val uri2 = Uri.parse(uri.toString() + "/favorites/" + index + "?notify=true")
                    val i = context.contentResolver.update(uri2, cv, null, null)
                    context.contentResolver.notifyChange(uri, null)//此处不能用uri2，是个坑
                    Log.i("HJ", "update ok: affected $i rows,index is$index")
                } else {
                    Log.i("HJ", "update result failed")
                }
                if (c != null && !c.isClosed) {
                    c.close()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.i("HJ", "update shortcut icon,get errors:" + ex.message)
            }

        }

        private fun flattenBitmap(bitmap: Bitmap): ByteArray? {
            // Try go guesstimate how much space the icon will take when serialized
            // to avoid unnecessary allocations/copies during the write.
            val size = bitmap.width * bitmap.height * 4
            val out = ByteArrayOutputStream(size)
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                return out.toByteArray()
            } catch (e: IOException) {
                Log.w("HJ", "Could not write icon")
                return null
            }

        }

        private fun getUriFromLauncher(context: Context): Uri {
            val uriStr = StringBuilder()
            var authority: String? = LauncherUtil.getAuthorityFromPermissionDefault(context)
            if (authority == null || authority.trim { it <= ' ' } == "") {
                authority = LauncherUtil
                        .getAuthorityFromPermission(context, LauncherUtil.getCurrentLauncherPackageName(context) + ".permission.READ_SETTINGS")
            }
            uriStr.append("content://")
            if (TextUtils.isEmpty(authority)) {
                val sdkInt = android.os.Build.VERSION.SDK_INT
                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                    uriStr.append("com.android.launcher.settings")
                } else if (sdkInt < 19) {// Android 4.4以下
                    uriStr.append("com.android.launcher2.settings")
                } else {// 4.4以及以上
                    uriStr.append("com.android.launcher3.settings")
                }
            } else {
                uriStr.append(authority)
            }
            uriStr.append("/favorites?notify=true")
            return Uri.parse(uriStr.toString())
        }

        /**
         * 为任意PackageName的App添加快捷方式
         *
         * @param context context
         * @param pkg     待添加快捷方式的应用包名
         * @return 返回true为正常执行完毕
         */
        fun addShortcutByPackageName(context: Context, pkg: String): Boolean {
            // 快捷方式名
            var title = "unknown"
            // MainActivity完整名
            var mainAct: String? = null
            // 应用图标标识
            var iconIdentifier = 0
            // 根据包名寻找MainActivity
            val pkgMag = context.packageManager
            val queryIntent = Intent(Intent.ACTION_MAIN, null)
            queryIntent.addCategory(Intent.CATEGORY_LAUNCHER)// 重要，添加后可以进入直接已经打开的页面
            queryIntent.flags = Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            queryIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)

            val list = pkgMag.queryIntentActivities(queryIntent,
                    PackageManager.GET_ACTIVITIES)
            for (i in list.indices) {
                val info = list[i]
                if (info.activityInfo.packageName == pkg) {
                    title = info.loadLabel(pkgMag).toString()
                    mainAct = info.activityInfo.name
                    iconIdentifier = info.activityInfo.applicationInfo.icon
                    break
                }
            }
            if (mainAct == null) {
                // 没有启动类
                return false
            }
            val shortcut = Intent(
                    "com.android.launcher.action.INSTALL_SHORTCUT")
            // 快捷方式的名称
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title)
            // 不允许重复创建
            shortcut.putExtra("duplicate", false)
            val comp = ComponentName(pkg, mainAct)
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, queryIntent.setComponent(comp))
            // 快捷方式的图标
            var pkgContext: Context? = null
            if (context.packageName == pkg) {
                pkgContext = context
            } else {
                // 创建第三方应用的上下文环境，为的是能够根据该应用的图标标识符寻找到图标文件。
                try {
                    pkgContext = context.createPackageContext(pkg, Context.CONTEXT_IGNORE_SECURITY or Context.CONTEXT_INCLUDE_CODE)
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }

            }
            if (pkgContext != null) {
                val iconRes = Intent.ShortcutIconResource.fromContext(pkgContext, iconIdentifier)
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes)
            }
            // 发送广播，让接收者创建快捷方式
            // 需权限<uses-permission
            // android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
            context.sendBroadcast(shortcut)
            return true
        }
    }
}
