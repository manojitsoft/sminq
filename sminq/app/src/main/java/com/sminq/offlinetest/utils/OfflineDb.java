package com.sminq.offlinetest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;

/**
 * Created by Manojkumar on 7/31/2017.
 */

public class OfflineDb {

    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public final static String EMP_TABLE="MyEmployees"; // name of table

    public final static String EMP_ID="_id"; // id value for employee
    public final static String EMP_NAME="name";  // name of employee

    /**
     *
     * @param context
     */
    public OfflineDb(Context context){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(String id, String name){
        ContentValues values = new ContentValues();
        values.put(EMP_ID, id);
        values.put(EMP_NAME, name);
        return database.insert(EMP_TABLE, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {EMP_ID, EMP_NAME};
        Cursor mCursor = database.query(true, EMP_TABLE,cols,null
            , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public boolean deleteRecords() {
        Cursor cursor = selectRecords(); // iterate to get each value.
        while (cursor != null && cursor.moveToNext()) {
            String key = cursor.getString(cursor.getColumnIndex(EMP_ID));
            database.delete(EMP_TABLE, EMP_ID+"=", new String[]{key});
        }
        return true;
    }
}
