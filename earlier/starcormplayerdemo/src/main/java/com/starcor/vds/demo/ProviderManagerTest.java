package com.starcor.vds.demo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class ProviderManagerTest {

    Uri CONTENT_URI = Uri.parse("content://com.starcor.mango.hndx.provider/deviceinfo");

    private static final String TAG = ProviderManagerTest.class.getSimpleName();

    private Context mContext;

    public static interface DBColumns extends BaseColumns {
        public static final String AUTH_URL = "auth_url";
        public static final String STB_ID = "stb_id";
        public static final String USER_ID = "user_id";
        public static final String USER_PASSWORD = "user_password";
        public static final String ACCESS_METHOD = "access_method";
    }

    public ProviderManagerTest(Context context) {
        mContext = context.getApplicationContext();
    }

//    //插入一个数据测试
//    public void insertUserInfo() {
//        ContentValues values = new ContentValues();
//        values.put(MyContentProvider.DBColumns.AUTH_URL, "http://222.246.132.231:8298/auth");
//        values.put(MyContentProvider.DBColumns.STB_ID, "181003990070318000004C16F114D0C0");
//        values.put(MyContentProvider.DBColumns.USER_ID, "ztezte");
//        values.put(MyContentProvider.DBColumns.USER_PASSWORD, EncodePassword.encode("654321"));
//        values.put(MyContentProvider.DBColumns.ACCESS_METHOD, "lan");
//        Uri uri = mContext.getContentResolver().insert(CONTENT_URI, values);
//        System.out.println(uri);
//    }

    //对插入的数据取出来测试
    public void queryUserId() {
//        insertUserInfo();

        Cursor cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String auth_url = cursor.getString(cursor.getColumnIndex(DBColumns.AUTH_URL));
                String stbId = cursor.getString(cursor.getColumnIndex(DBColumns.STB_ID));
                String userId = cursor.getString(cursor.getColumnIndex(DBColumns.USER_ID));
                String userPassword = cursor.getString(cursor.getColumnIndex(DBColumns.USER_PASSWORD));
                userPassword = DecodePassword.decode(userPassword);
                String accessMethod = cursor.getString(cursor.getColumnIndex(DBColumns.ACCESS_METHOD));
                Log.d(TAG, "auth_url =" + auth_url);
                Log.d(TAG, "stbId =" + stbId);
                Log.d(TAG, "userId =" + userId);
                Log.d(TAG, "userPassword =" + userPassword);
                Log.d(TAG, "accessMethod =" + accessMethod);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
