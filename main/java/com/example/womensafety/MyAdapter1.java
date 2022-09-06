package com.example.womensafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyAdapter1 {
    myDbHelper myhelper;
    public MyAdapter1(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String vsource)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.SOURCE, vsource);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.SID, myDbHelper.SOURCE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String vvid =cursor.getString(cursor.getColumnIndex(myDbHelper.SID));
            String vvsource =cursor.getString(cursor.getColumnIndex(myDbHelper.SOURCE));

            buffer.append(vvsource + " \n");
        }
        return buffer.toString();
    }

    public int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.SID+" = ?",whereArgs);
        return  count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "VoterGmail1";    // Database Name
        private static final String TABLE_NAME = "Source";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String SID="SourceId";     // Column I (Primary Key)
        private static final String SOURCE = "Source";    //Column II
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+SID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SOURCE+" VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message1.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message1.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message1.message(context,""+e);
            }
        }
    }
}
