package com.lx.phonerecycle.location;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lx
 * Date: 13-8-22
 * Time: 下午6:24
 */

public class LocationInfoProvider extends ContentProvider {

    private static final String TAG = LocationInfoProvider.class.getSimpleName();

    public static interface DBColumns extends BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/location");
        public static final String LOCATION_ID = "location_id";
        public static final String LOCATION_NAME = "location_name";
        public static final String PARENT_ID = "parent_id";
    }

    public static final String AUTHORITY = "com.lx.phonerecycle.cityinfo.provider";

    private static final String LOCATION_INFO_DIR_TYPE = "vnd.android.cursor.dir/vnd.phonerecycle.cityinfo.provider";
    private static final String LOCATION_INFO_ITEM_TYPE = "vnd.android.cursor.item/vnd.phonerecycle.cityinfo.provider";

    private static final int LOCATION_INFO = 0;
    private static final int LOCATION_INFO_ITEM = 1;

    private static final String TABLE_NAME = "location";

    private static final UriMatcher mURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mURIMatcher.addURI(AUTHORITY, TABLE_NAME, LOCATION_INFO);
        mURIMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", LOCATION_INFO_ITEM);
    }

    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;

    private final class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(final Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            createTable(db);
        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, int oldV, final int newV) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            createTable(db);
        }

        private void createTable(SQLiteDatabase db) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                String dbColumns
                        = DBColumns._ID + " integer primary key autoincrement, "
                        + DBColumns.LOCATION_ID + " integer, "
                        + DBColumns.LOCATION_NAME + " text, "
                        + DBColumns.PARENT_ID + " integer"
                        ;
                db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + dbColumns + ");");
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    private DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = mURIMatcher.match(uri);
        switch (match) {
            case LOCATION_INFO:
                return LOCATION_INFO_DIR_TYPE;
            case LOCATION_INFO_ITEM:
                return LOCATION_INFO_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Uri result;
        int match = mURIMatcher.match(uri);
        if (LOCATION_INFO == match) {
            ContentValues newValues = new ContentValues(values);
            if (!newValues.containsKey(DBColumns.LOCATION_ID)) {
                newValues.put(DBColumns.LOCATION_ID, "");
            }
            if (!newValues.containsKey(DBColumns.LOCATION_NAME)) {
                newValues.put(DBColumns.LOCATION_NAME, "");
            }
            if (!newValues.containsKey(DBColumns.PARENT_ID)) {
                newValues.put(DBColumns.PARENT_ID, "");
            }
            long id = db.insert(TABLE_NAME, null, newValues);
            result = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(uri, null);
            newValues.clear();
        } else {
            throw new IllegalArgumentException("Unknown uri:" + uri);
        }
        return result;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor;
        int match = mURIMatcher.match(uri);
        switch (match) {
            case LOCATION_INFO:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case LOCATION_INFO_ITEM:
                cursor = db.query(TABLE_NAME, projection, getItemSelection(uri, selection), selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri:" + uri);
        }
        if ((cursor != null) && !isTemporary()) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int result;
        int match = mURIMatcher.match(uri);
        switch (match) {
            case LOCATION_INFO:
                result = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case LOCATION_INFO_ITEM:
                result = db.delete(TABLE_NAME, getItemSelection(uri, selection), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri:" + uri);
        }
        if (result > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int result;
        int match = mURIMatcher.match(uri);
        switch (match) {
            case LOCATION_INFO:
                result = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case LOCATION_INFO_ITEM:
                result = db.update(TABLE_NAME, values, getItemSelection(uri, selection), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri:" + uri);
        }
        if (result > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return result;
    }

    private String getItemSelection(Uri uri, String selection) {
        String id = uri.getLastPathSegment();
        selection = BaseColumns._ID + "=" + id +
                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
        return selection;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int numValues = 0;
        db.beginTransaction(); //开始事务
        try {
            //数据库操作
            numValues = values.length;
            for (int i = 0; i < numValues; i++) {
                insert(uri, values[i]);
            }
            db.setTransactionSuccessful(); //别忘了这句 Commit
        } finally {
            db.endTransaction(); //结束事务
        }
        return numValues;
    }

}
