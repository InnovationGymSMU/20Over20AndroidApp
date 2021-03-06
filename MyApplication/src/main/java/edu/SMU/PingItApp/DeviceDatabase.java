package edu.SMU.PingItApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris on 9/19/13.
 */
public class DeviceDatabase {

    private static final String tag = "DeviceDatabase";

    private static final String DATABASE_NAME = "Devices";
    private static final int DATABASE_VERSION = 5;

    private static final String DEVICE_TABLE = "DeviceTable";
    private static final String TAG_ID = "TagID";
    private static final String TAG_NAME = "TagName";
    private static final String REGISTRATION_DATE = "RegistrationDate";

    private static final String CREATE_DEVICE_TABLE =
            "CREATE TABLE " + DEVICE_TABLE + "(" +
                    TAG_ID + " TEXT PRIMARY KEY, " +
                    TAG_NAME + " TEXT, " +
                    REGISTRATION_DATE + " TEXT);";

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

    public void addTag(UserTagInfo tag) {

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        String formattedDate = format.format(date);

        //Setup the values to insert
        ContentValues values = new ContentValues();
        values.put(TAG_ID, Integer.toString(tag.getTagID()));
        values.put(TAG_NAME, tag.getTagName());
        values.put(REGISTRATION_DATE, formattedDate);

        //Insert into the DB
        synchronized (helper) {
            SQLiteDatabase db = openDatabaseConnection();
            db.insert(DEVICE_TABLE, null, values);
            closeDatabaseConnection();
        }
    }

    public List<UserTagInfo> getAllTags() {
        List<UserTagInfo> allTags = new LinkedList<UserTagInfo>();

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
                    String registrationDate = cursor.getString(2);
                    UserTagInfo userTagInfo = new UserTagInfo(tagName, tagID, registrationDate);
                    allTags.add(userTagInfo);
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

    public UserTagInfo getTagInfoFromId(int tagID) {
        String query = "SELECT * FROM " + DEVICE_TABLE +
                " WHERE " + TAG_ID + " = " + tagID + ";";

        UserTagInfo userTagInfo = null;
        synchronized (helper) {
            SQLiteDatabase db = openDatabaseConnection();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                String tagName = cursor.getString(1);
                String registrationDate = cursor.getString(2);
                userTagInfo = new UserTagInfo(tagName, tagID, registrationDate);
            }
            closeDatabaseConnection();
        }

        return userTagInfo;
    }

    public void deleteTag(UserTagInfo info){
        synchronized (helper){
            SQLiteDatabase db = openDatabaseConnection();
            db.delete(DEVICE_TABLE, TAG_ID + " = \"" + info.getTagID() + "\"", null );
            closeDatabaseConnection();
        }
    }

    public void updateTagRegistrationDate(UserTagInfo info) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        String formattedDate = format.format(date);

        Log.d(tag, "Updating the date to: " + formattedDate);
        String query = "UPDATE " + DEVICE_TABLE + " SET " + REGISTRATION_DATE +
                       " = \"" + formattedDate + "\" WHERE " + TAG_ID + " = \"" + info.getTagID() + "\";";

        synchronized (helper) {
            SQLiteDatabase db = openDatabaseConnection();
            db.execSQL(query);
            closeDatabaseConnection();
        }
    }
}
