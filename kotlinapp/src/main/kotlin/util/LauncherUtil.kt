package util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ProviderInfo
import android.content.pm.ResolveInfo
import android.text.TextUtils

/**
 * Function: LauncherUtil
 * Create date on 15/8/17.
 *
 * @version 1.0
 */
class LauncherUtil @Throws(InstantiationException::class)
private constructor() {

    init {
        throw InstantiationException("This class is not for instantiation")
    }

    companion object {

        private var mBufferedValue: String? = null

        /**
         * get the current Launcher's Package Name
         */
        fun getCurrentLauncherPackageName(context: Context): String {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            val res = context.packageManager.resolveActivity(intent, 0)
            if (res == null || res.activityInfo == null) {
                // should not happen. A home is always installed, isn't it?
                return ""
            }
            return if (res.activityInfo.packageName == "android") {
                ""
            } else {
                res.activityInfo.packageName
            }
        }

        /**
         * default permission is "com.android.launcher.permission.READ_SETTINGS"<br></br>
         * [.getAuthorityFromPermission]<br></br>
         *
         * @param context context
         */
        fun getAuthorityFromPermissionDefault(context: Context): String {
            if (TextUtils.isEmpty(mBufferedValue))
            //we get value buffered
                mBufferedValue = getAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS")
            return ""
        }

        /**
         * be cautious to use this, it will cost about 500ms 此函数为费时函数，大概占用500ms左右的时间<br></br>
         * android系统桌面的基本信息由一个launcher.db的Sqlite数据库管理，里面有三张表<br></br>
         * 其中一张表就是favorites。这个db文件一般放在data/data/com.android.launcher(launcher2)文件的databases下<br></br>
         * 但是对于不同的rom会放在不同的地方<br></br>
         * 例如MIUI放在data/data/com.miui.home/databases下面<br></br>
         * htc放在data/data/com.htc.launcher/databases下面<br></br>/
         *
         * @param context    context
         * @param permission 读取设置的权限  READ_SETTINGS_PERMISSION
         * @return permission
         */
        fun getAuthorityFromPermission(context: Context, permission: String): String {
            if (TextUtils.isEmpty(permission)) {
                return ""
            }
            try {
                val packs = context.packageManager.getInstalledPackages(PackageManager.GET_PROVIDERS) ?: return ""
                for (pack in packs) {
                    val providers = pack.providers
                    if (providers != null) {
                        for (provider in providers) {
                            if (permission == provider.readPermission || permission == provider.writePermission) {
                                return provider.authority
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }
    }
}
