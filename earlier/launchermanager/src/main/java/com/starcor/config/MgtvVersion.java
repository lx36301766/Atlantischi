package com.starcor.config;

/**
 * @author czy
 *         2013/6/21
 *         芒果要求定义的版本号，仅用来标识芒果的需求，建议不要用来作任何业务逻辑的分类
 *         <p/>
 *         zhouliang20131016：重构代码，使版本号定义、修改操作更集中一点，方便每次打包
 */

public class MgtvVersion {
    public static final AppVersionNumber versionNumber;
    public static final ReleaseType releaseType;
    public static int factoryCode;

    static {
        versionNumber = new AppVersionNumber();
        versionNumber.majorVersion = 3;

        versionNumber.minorVersion = 2;
        versionNumber.patchVersion = 9;
        releaseType = ReleaseType.Beta;
        factoryCode = Factory.VERSION_SC_1_0_0;
    }


    public static String getVersion(int factoryCodeForInitVersion) {
        factoryCode = factoryCodeForInitVersion;
        return getVersion();
    }

    public static String getVersion() {
        MgtvVersionInfo vInfo = MgtvVersionTable.getMgtvVersionInfo(factoryCode);
        if (null == vInfo) {
            return "";
        }

        String versionDiscribe = vInfo.versionDiscribe.toString();
        return versionNumber.toString() + "." + versionDiscribe + "_" + releaseType.name();
    }

    public static ReleaseType getReleaseType() {
        return releaseType;
    }

    public static int getFactoryCode() {
        return factoryCode;
    }

    public static int getMinorVersionNum() {
        return versionNumber.minorVersion;
    }

    /**
     * 版本号 格式：*.*.*
     *
     * @author zhouliang20131016
     */
    public static class AppVersionNumber {
        int majorVersion;  //主版本号，通常是整个版本从头开发、重新设计时更新
        int minorVersion;  //子版本号，新增较多需求、版本功能有较大变化时更新
        int patchVersion;  //阶段版本号（补丁版本号），每一次提交测试版本更新

        public String toString() {
            return majorVersion + "." + minorVersion + "." + patchVersion;
        }
    }

    // 注意，下面注释中的#ReleaseType#是自动构建的参数标识
    // 如果添加新的ReleaseType，添加此标识，构建系统可以自动识别
    public enum ReleaseType {
        Release,    //#ReleaseType#发行版本，连正式平台
        Beta,       //#ReleaseType#测试版本，连测试平台
        Alpha,      //#ReleaseType#测试版本，连测试平台
        Demo,       //#ReleaseType#演示版本，连开发平台
        Debug,          //#ReleaseType#内部调试版本，连开发平台
        Debug_Test,     //#ReleaseType#内部调试版本，连测试平台
        Debug_Release,  //#ReleaseType#内部调试版本，连正式平台
    }

    // 代码编译信息, 以下属性由编译服务自动更新
    // 代码对应SVN版本
    public static final String codeRev = "head";
    // 附加信息
    public static final String buildInfo = "";
}
