package com.starcor.ui.haier;

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
public class WidgetVideoInfoProvider extends ContentProvider {

    private static final String TAG = WidgetVideoInfoProvider.class.getSimpleName();

    public static interface DBColumns extends BaseColumns {
        public static final String VIDEO_ID = "video_id";
        public static final String VIDEO_NAME = "video_name";
        public static final String VIDEO_KIND = "video_kind";
        public static final String VIDEO_UI_STYLE = "video_ui_style";
        public static final String VIDEO_IMG_LIST = "video_img_list";
        public static final String VIDEO_ACTORS = "video_actors";
        public static final String VIDEO_DURATION = "video_duration";
        public static final String VIDEO_DIRECTOR = "video_director";
        public static final String VIDEO_DESCRIPTION = "video_description";
        public static final String VIDEO_DESC    = "video_desc";
    }

    public static final String AUTHORITY = "com.starcor.hunan.widget.videoinfo";

    private static final String VIDEOINFO_DIR_TYPE = "vnd.android.cursor.dir/vnd.starcor.widget.videoinfo";
    private static final String VIDEOINFO_ITEM_TYPE = "vnd.android.cursor.item/vnd.starcor.widget.videoinfo";

    private static final int VIDEO_INFO = 0;
    private static final int VIDEO_INFO_ID = 1;

    private static final String TABLE_NAME = "videoinfo";

    private static final UriMatcher mURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mURIMatcher.addURI(AUTHORITY, TABLE_NAME, VIDEO_INFO);
        mURIMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", VIDEO_INFO_ID);
    }

    private static final String DATABASE_NAME = "widget_video_info.db";
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
            createTable(db);
        }

        private void createTable(SQLiteDatabase db) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                String dbColumns
                        = DBColumns._ID + " integer primary key autoincrement, "
                        + DBColumns.VIDEO_ID + " text, "
                        + DBColumns.VIDEO_NAME + " text, "
                        + DBColumns.VIDEO_KIND + " text, "
                        + DBColumns.VIDEO_UI_STYLE + " integer, "
                        + DBColumns.VIDEO_IMG_LIST + " text, "
                        + DBColumns.VIDEO_ACTORS + " text, "
                        + DBColumns.VIDEO_DURATION + " integer, "
                        + DBColumns.VIDEO_DIRECTOR + " text, "
                        + DBColumns.VIDEO_DESCRIPTION + " text, "
                        + DBColumns.VIDEO_DESC + " text "
                        ;
                db.execSQL("CREATE TABLE" + TABLE_NAME + "(" + dbColumns + ");");
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
            case VIDEO_INFO:
                return VIDEOINFO_DIR_TYPE;
            case VIDEO_INFO_ID:
                return VIDEOINFO_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Uri result;
        int match = mURIMatcher.match(uri);
        if (VIDEO_INFO == match) {
            ContentValues newValues = new ContentValues(values);
            if (!newValues.containsKey(DBColumns.VIDEO_ID)) {
                newValues.put(DBColumns.VIDEO_ID, "");
            }
            if (!newValues.containsKey(DBColumns.VIDEO_NAME)) {
                newValues.put(DBColumns.VIDEO_NAME, "");
            }
            if (!newValues.containsKey(DBColumns.VIDEO_KIND)) {
                newValues.put(DBColumns.VIDEO_KIND, "");
            }
            if (!newValues.containsKey(DBColumns.VIDEO_UI_STYLE)) {
                newValues.put(DBColumns.VIDEO_UI_STYLE, -1);
            }
            if (!newValues.containsKey(DBColumns.VIDEO_IMG_LIST)) {
                newValues.put(DBColumns.VIDEO_IMG_LIST, "");
            }
            if (!newValues.containsKey(DBColumns.VIDEO_ACTORS)) {
                newValues.put(DBColumns.VIDEO_ACTORS, "");
            }
            if (!newValues.containsKey(DBColumns.VIDEO_DURATION)) {
                newValues.put(DBColumns.VIDEO_DURATION, 0);
            }
            if (!newValues.containsKey(DBColumns.VIDEO_DIRECTOR)) {
                newValues.put(DBColumns.VIDEO_DIRECTOR, "");
            }
            if (!newValues.containsKey(DBColumns.VIDEO_DESCRIPTION)) {
                newValues.put(DBColumns.VIDEO_DESCRIPTION, 0);
            }
            if (!newValues.containsKey(DBColumns.VIDEO_DESC)) {
                newValues.put(DBColumns.VIDEO_DESC, "");
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
            case VIDEO_INFO:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case VIDEO_INFO_ID:
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
            case VIDEO_INFO:
                result = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case VIDEO_INFO_ID:
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
            case VIDEO_INFO:
                result = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case VIDEO_INFO_ID:
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
                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");;
        return selection;
    }

}
