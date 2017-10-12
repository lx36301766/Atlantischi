package com.starcor.config;

/**
 * @author czy
 *         <p/>
 *         zhouliang20131016：重构代码，使版本号定义、修改操作更集中一点，方便每次打包
 */
/*public class MgtvVersionInfo
{
	public String mgtvVersion;
	public String N1AUrl;
	public int factory;
	public MgtvVersionInfo( String mgtvVersion, int factory, String N1AUrl )
	{
		this.mgtvVersion = mgtvVersion;
		this.N1AUrl = N1AUrl;
		this.factory = factory;
	}
}*/

public class MgtvVersionInfo {
    public static class VersionDiscribe {
        int policy;  //后台计费策略编码，由芒果定义
        String cooprationCode;    //合作方编码，由芒果定义
        String factoryName;          //厂家/运营商名称
        String deviceCodeMajor;   //设备主编号
        String deviceCodeMinor;   //设备子编号

        public VersionDiscribe(int policy, String cooprationCode, String factoryName, String deviceCodeMajor, String deviceCodeMinor) {
            this.policy = policy;
            this.cooprationCode = cooprationCode;
            this.factoryName = factoryName;
            this.deviceCodeMajor = deviceCodeMajor;
            this.deviceCodeMinor = deviceCodeMinor;
        }

        public String toString() {
            return String.valueOf(policy) + "." + cooprationCode + "." + factoryName
                    + "." + deviceCodeMajor + "." + deviceCodeMinor;
            /*return String.valueOf(policy) + "." + cooprationCode + "." + factoryName
							+ deviceCodeMajor + deviceCodeMinor;*/
        }
    }

    public int factory;
    public VersionDiscribe versionDiscribe;
    public String N1AUrl;

    public MgtvVersionInfo(int factory, int policy, String cooprationCode, String factoryName, String deviceCodeMajor, String deviceCodeMinor, String N1AUrl) {
        this.factory = factory;
        this.versionDiscribe = new VersionDiscribe(policy, cooprationCode, factoryName, deviceCodeMajor, deviceCodeMinor);
        this.N1AUrl = N1AUrl;
    }
}
