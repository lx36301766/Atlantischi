package com.lx.webservice.test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.util.Calendar;
import java.util.Random;

import com.sun.crypto.provider.SunJCE;

/**
 * Copyright (C) 2015 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  15-4-2 上午10:37 <br>
 * description:
 */

public class AuthHelper {

    private static final String TAG = AuthHelper.class.getSimpleName();

    public static String getAuthToken() {
        String AuthServer = "http://124.232.135.227:8298/auth";        // 认证服务器
//        String Stbsd = "9A1003990070318000004C16F114DCDC";		// 机顶盒序列号
        String StbId = "181003990070318000004C16F114D0C0";        // 机顶盒序列号
        String MacAddr = "4c:16:f1:14:d0:c0";                // 机顶盒网卡MAC地址
        String IpAddr = "182.138.101.47";                    // 机顶盒IP地址
        String UserId = "ztezte";                // 业务帐号
        String Password = "654321";                    // 帐号密码
        String AccessMethod = "lan";                    // 用户宽带帐号类型  lan dhcp pppoe

        try {
            URL AuthUrl;
            HttpURLConnection Conn;
            String Url;
            String Line;
            String Response;
            String AuthInfo;
            String Request;
            int Begin, End;

            AuthUrl = new URL(AuthServer + "?UserID=" + UserId + "&Action=Login");

            Conn = (HttpURLConnection) AuthUrl.openConnection();

            if (Conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(Conn.getInputStream()));

                Response = "";
                while ((Line = in.readLine()) != null) {
                    Response += Line;
                }
                Conn.disconnect();
            } else {
                Conn.disconnect();
//                Logger.d(TAG, "URL: " + AuthUrl.toString() + " Code: " + Conn.getResponseCode());
                return null;
            }
            Begin = Response.indexOf("Authentication.CTCGetAuthInfo");
            if (Begin < 0 || (Begin = Response.indexOf("'", Begin)) < 0 || (End = Response.indexOf("'", Begin + 1)) < 0) {
//                Logger.d(TAG, "Authentication.CTCGetAuthInfo not found.");
                return null;
            }
            AuthInfo = Response.substring(Begin + 1, End);
//            Logger.d(TAG, "AuthInfo: " + AuthInfo);

            Request = "UserID=" + UserId;
            Request += "&Authenticator=" + Authenticator(AuthInfo, StbId, MacAddr, IpAddr, UserId, Password);
            Request += "&AccessMethod=lan";
            Request += "&AccessUserName=" + UserId + "@itv";

            AuthUrl = new URL(AuthServer.substring(0, AuthServer.lastIndexOf('/')) + "/uploadAuthInfo");
            Conn = (HttpURLConnection) AuthUrl.openConnection();
            Conn.setRequestMethod("POST");
            Conn.setDoOutput(true);
            Conn.setDoInput(true);
            Conn.setUseCaches(false);
            Conn.setAllowUserInteraction(false);
            Conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Conn.setRequestProperty("Content-Length", "" + Request.length());
            DataOutputStream OutStream = new DataOutputStream(Conn.getOutputStream());
            OutStream.writeBytes(Request);
            OutStream.close();
            if (Conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(Conn.getInputStream()));

                Response = "";
                while ((Line = in.readLine()) != null) {
                    Response += Line;
                }
                Conn.disconnect();
            } else {
                Conn.disconnect();
//                Logger.d(TAG, "URL: " + AuthUrl.toString() + " Code: " + Conn.getResponseCode());
                return null;
            }

            String UserToken = CTCSetConfig(Response, "UserToken");
//            Logger.d(TAG, "UserToken: " + UserToken);
            return UserToken;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String CTCSetConfig(String Response, String Name) {
        int Begin, End;
        String Key = "Authentication.CTCSetConfig ('" + Name + "','";

        Begin = Response.indexOf(Key);
        if (Begin > 0) {
            Begin += Key.length();
            End = Response.indexOf("'", Begin);
            if (End > 0)
                return Response.substring(Begin, End);
        }

        return "";
    }

    static public String Authenticator(String AuthInfo, String StbId, String MacAddr, String IpAddr, String UserId, String Password) {
        String AuthStr = "";
        String Result = "";
        Random Rand = new Random(Calendar.getInstance().getTimeInMillis());

        AuthStr += String.format("%08d", Rand.nextInt(100000000)) + "$";
        AuthStr += AuthInfo + "$";
        AuthStr += UserId + "$";
        AuthStr += StbId + "$";
        AuthStr += IpAddr + "$";
        AuthStr += MacAddr + "$";
        AuthStr += "990070|$CTC";

//		System.out.println(AuthStr);

        try {
            byte Auth[] = AuthStr.getBytes("ASCII");
            int Len = Auth.length;
            int Mod = Len % 8;

            if (Mod != 0) Len += 8 - Mod;
            byte AuthBytes[] = new byte[Len];

            for (int i = 0; i < Len; i++) {
                if (i >= Auth.length)
                    AuthBytes[i] = (byte) (8 - Mod);
                else
                    AuthBytes[i] = Auth[i];
            }

            String Algorithm = "DESede";
            byte KeyBytes[] = new byte[24];
            byte PassBytes[] = Password.getBytes();

            for (int i = 0; i < 24; i++) {
                if (i < PassBytes.length)
                    KeyBytes[i] = PassBytes[i];
                else
                    KeyBytes[i] = 0x30;
            }

            Security.addProvider(new SunJCE());
            SecretKey DesKey = new SecretKeySpec(KeyBytes, Algorithm);

            Cipher C = Cipher.getInstance(Algorithm);
            C.init(Cipher.ENCRYPT_MODE, DesKey);
            byte Encrypt[] = C.doFinal(AuthBytes);

            for (int i = 0; i < Len; i++) {
                Result += String.format("%02X", Encrypt[i] < 0 ? (256 + Encrypt[i]) : Encrypt[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result;
    }


}
