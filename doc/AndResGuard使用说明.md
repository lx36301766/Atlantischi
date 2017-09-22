
AndResGuard使用说明

AndResGuard因为用到了7z压缩，所以需要先安装7z，下载地址：

  window： 对于window应下载命名行版本，若将7za指定到环境变量，即无须设置。地址：http://sparanoid.com/lab/7z/download.html
  linux：sudo apt-get install p7zip-full
  mac：brew install p7zip

AndResGuard使用方法有以下两种：



第一种是直接使用作者提供的工具将已经发布的包通过工具直接混淆即可，通过以下命令

java -jar AndResGuard-cli-1.2.0.jar jumeilatest.apk -config config.xml -out JumeiOutput -signature /Users/xxx/android/Jumei/mobile_android/JuMeiYouPin/JuMeiYouPin.KeyStore jumeiapp654321 jumeiapp654321 jumeiapp -mapping ./map_file_path/ -7zip /usr/local/bin/7za  -zipalign /Users/xuanluo/Develop/android/sdk/build-tools/25.0.1/zipalign

参数说明：
AndResGuard-cli-1.2.0.jar 混淆工具,下载地址为：https://github.com/shwenzhang/AndResGuard/raw/master/tool_output/AndResGuard-cli-1.2.0.jar
-config 配置文件路径，配置文件的详细介绍请参考:https://github.com/shwenzhang/AndResGuard/blob/master/doc/how_to_work.zh-cn.md
-out 输出apk目录
-signature 签名文件，参数依次为: signature_file_path storepass_value keypass_value storealias_value
-mapping 指定旧的mapping文件
-7zip  7zip的安装路径，若已添加到环境变量不需要设置
-zipalign zipalign的路径，若已添加到环境变量不需要设置



第二种则是通过插件形式集成到自己项目中再直接打包，打出来的包就已经是混淆过的，使用方法如下：


1.在project的build.gradle中添加：
classpath 'com.tencent.mm:AndResGuard-gradle-plugin:1.2.3'


2.在module的build.gradle添加以下代码：

apply plugin: 'AndResGuard'

andResGuard {
    // mappingFile = file("./resource_mapping.txt")
    mappingFile = null
    // 当你使用v2签名的时候，7zip压缩是无法生效的。
    use7zip = true
    useSign = true
    // 打开这个开关，会keep住所有资源的原始路径，只混淆资源的名字
    keepRoot = false
    whiteList = [
            // for your icon
            "R.drawable.icon",
            // for fabric
            "R.string.com.crashlytics.*",
            // for umeng update
            "R.string.umeng*",
            "R.string.UM*",
            "R.string.tb_*",
            "R.layout.umeng*",
            "R.layout.tb_*",
            "R.drawable.umeng*",
            "R.drawable.tb_*",
            "R.anim.umeng*",
            "R.color.umeng*",
            "R.color.tb_*",
            "R.style.*UM*",
            "R.style.umeng*",
            "R.id.umeng*",
            // umeng share for sina
            "R.drawable.sina*",
            // umeng share commond
            "R.id.progress_bar_parent",
            "R.id.webView"
    ]
    compressFilePattern = [
            "*.png",
            "*.jpg",
            "*.jpeg",
            "*.gif",
            "resources.arsc"
    ]
    sevenzip {
        artifact = 'com.tencent.mm:SevenZip:1.2.3'
        path = "/usr/local/bin/7za"
    }
}


3.通过命令 ./gradlew resguardXXX 打包生成apk文件即可，xxx代表flavors名字



生成的apk在AndResGuard_XXXXX文件夹下，其目录下会生成5个不同签名或压缩方式的apk文件，还有一个名为resource_mapping_xxx.txt的资源混淆map文件






