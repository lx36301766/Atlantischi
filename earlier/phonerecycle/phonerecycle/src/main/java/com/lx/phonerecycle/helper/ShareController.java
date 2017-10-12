package com.lx.phonerecycle.helper;

import android.app.Activity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-8-4 下午8:34 <br>
 * description:
 */

public class ShareController {

    private Activity mActivity;

    public ShareController(Activity activity) {
        mActivity = activity;

        initShareData();
    }

    // 整个平台的Controller, 负责管理整个SDK的配置、操作等处理
    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    private void initShareData() {
        UMImage mUMImgBitmap = new UMImage(mActivity,
                "http://www.umeng.com/images/pic/banner_module_social.png");

        UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
        uMusic.setAuthor("zhangliyong");
        uMusic.setTitle("天籁之音");
        // uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
        // 非url类型的缩略图需要传递一个UMImage的对象
        uMusic.setThumb(mUMImgBitmap);
        //
        // 视频分享
        UMVideo umVedio = new UMVideo(
                "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        umVedio.setTitle("友盟社会化组件视频");
        // umVedio.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
        umVedio.setThumb(mUMImgBitmap);
        // 设置分享文字内容
        mController.setShareContent("友盟社会化组件还不错，让移动应用快速整合社交分享功能。www.umeng.com/social");
        // mController.setShareContent(null);
        // 设置分享图片
        // mController.setShareMedia(mUMImgBitmap);
        // 支持微信朋友圈
        //QQ
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();

        //QQZONE
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();

        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx967daebe835fbeac";
        // 微信图文分享,音乐必须设置一个url
//		String contentUrl = "http://www.umeng.com/social";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mActivity, appId);
        wxHandler.addToSocialSDK();
        wxHandler.setTitle("友盟社会化组件还不错-WXHandler...");

        // 朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mActivity, appId);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        wxCircleHandler.setTitle("友盟社会化组件还不错-CircleHandler...");
        // mController.getConfig().closeToast();
        //
        // mController.getConfig().registerListener(new SnsPostListener() {
        //
        // @Override
        // public void onStart() {
        // Toast.makeText(mActivity, "weixin -- xxxx onStart", 0)
        // .show();
        // }
        //
        // @Override
        // public void onComplete(SHARE_MEDIA platform, int eCode,
        // SocializeEntity entity) {
        // Toast.makeText(mActivity, platform + " code = " + eCode, 0)
        // .show();
        // }
        // });
    }

    public void openShareContent() {
        mController.getConfig().setPlatformOrder(SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT,SHARE_MEDIA.RENREN,
                SHARE_MEDIA.DOUBAN,SHARE_MEDIA.QZONE,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE);
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能");
        // 是否只有已登录用户才能打开分享选择页
        mController.openShare(mActivity, false);
    }


}
