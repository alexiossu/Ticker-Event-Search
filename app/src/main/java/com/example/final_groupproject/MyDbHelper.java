package com.example.final_groupproject;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Aleksei Surkov
 * @version 1.0
 */

public class MyDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDatabaseFile";
    public static final int VERSION_NUM = 3;
    public static final String TABLE_NAME = "EVENTS";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "EVENT";
    public static final String COL_URL = "URL";
    public static final String COL_DATE = "DATE";
    public static final String COL_TIME = "TIME";
    public static final String COL_MAX = "MAX";
    public static final String COL_MIN = "MIN";


    public MyDbHelper(Context context){

        super(context, DATABASE_NAME, null, VERSION_NUM );
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT, " + COL_URL + " TEXT,"
                + COL_DATE + " TEXT," + COL_TIME + " TEXT,"
                + COL_MIN + " INT," + COL_MAX + " INT );");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
       // Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }
}
