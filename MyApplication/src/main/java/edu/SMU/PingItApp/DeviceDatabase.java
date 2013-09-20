package edu.SMU.PingItApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris on 9/19/13.
 */
public class DeviceDatabase {

    private static final String tag = "DeviceDatabase";

    private static final String DATABASE_NAME = "Devices";
    private static final int DATABASE_VERSION = 3;

    private static final String DEVICE_TABLE = "DeviceTable";
    private static final String TAG_ID = "TagID";
    private static final String TAG_NAME = "TagName";

    private static final String CREATE_DEVICE_TABLE =
            "CREATE TABLE " + DEVICE_TABLE + "(" + TAG_ID + " TEXT PRIMARY KEY, " + TAG_NAME + " TEXT);";

    private static DatabaseHelper helper = null;
    private Context context;

    public DeviceDatabase(Context context) {
        this.context = context.getApplicationContext();
        helper = new DatabaseHelper(this.context);
    }

    protected static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DEVICE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DEVICE_TABLE);
            onCreate(db);
        }
    }

    private SQLiteDatabase openDatabaseConnection() {
        if (helper == null) {
            helper = new DatabaseHelper(this.context);
        }
        return helper.getWritableDatabase();
    }

    private void closeDatabaseConnection() {
        helper.close();
    }

    public void addTag(TagInfo tag) {
        //Setup the values to insert
        ContentValues values = new ContentValues();
        values.put(TAG_ID, Integer.toString(tag.getTagID()));
        values.put(TAG_NAME, tag.getTagName());

        //Insert into the DB
        synchronized (helper) {
            SQLiteDatabase db = openDatabaseConnection();
            db.insert(DEVICE_TABLE, null, values);
            closeDatabaseConnection();
        }
    }

    public List<TagInfo> getAllTags() {
        List<TagInfo> allTags = new LinkedList<TagInfo>();

        String query = "SELECT * FROM " + DEVICE_TABLE;

        synchronized (helper) {
            //Run query to get all tag info
            SQLiteDatabase db = openDatabaseConnection();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                //Iterate over results and pull information
                do {
                    int tagID = Integer.parseInt(cursor.getString(0));
                    String tagName = cursor.getString(1);
                    TagInfo tagInfo = new TagInfo(tagName, tagID);
                    allTags.add(tagInfo);
                } while (cursor.moveToNext());
            }

            closeDatabaseConnection();
        }

        return allTags;
    }

    public boolean isTagAlreadyRegistered(int tagID) {
        String query = "SELECT * FROM " + DEVICE_TABLE +
                       " WHERE " + TAG_ID + " = " + tagID + ";";

        boolean hasElements = false;

        synchronized(helper) {
            //Open a connection, pull all tags that match, and check the results
            SQLiteDatabase db = openDatabaseConnection();
            Cursor cursor = db.rawQuery(query, null);
            hasElements = cursor.moveToFirst();
            closeDatabaseConnection();
        }

        return hasElements;
    }
}
