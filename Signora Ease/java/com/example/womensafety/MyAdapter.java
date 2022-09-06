package com.example.womensafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyAdapter {
    myDbHelper myhelper;
    public MyAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String vphone,String votp)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.PHONE, vphone);
        contentValues.put(myDbHelper.OTP, votp);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.PHONE, myDbHelper.OTP};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String vvid =cursor.getString(cursor.getColumnIndex(myDbHelper.UID));
            String vvphone =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            String vvotp =cursor.getString(cursor.getColumnIndex(myDbHelper.OTP));

            buffer.append(vvid+ "   " + vvphone + "   " + vvotp+ " \n");
        }
        return buffer.toString();
    }
    public String getIdData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.PHONE, myDbHelper.OTP};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        if (cursor.moveToLast())
        {
            String vvid =cursor.getString(cursor.getColumnIndex(myDbHelper.UID));

            buffer.append(vvid);
        }
        return buffer.toString();
    }

    public String getOtpData(String sid)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.PHONE, myDbHelper.OTP};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String vvid =cursor.getString(cursor.getColumnIndex(myDbHelper.UID));
            if(sid.equals(vvid)) {
                String vpass =cursor.getString(cursor.getColumnIndex(myDbHelper.OTP));
                buffer.append(vpass);
            }
        }
        return buffer.toString();
    }
    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.UID+" = ?",whereArgs);
        return  count;
    }

    /*public int updatePass(String cid , String newPass)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.PASSWORD,newPass);
        String[] whereArgs= {cid};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.VOTERID+" = ?",whereArgs );
        return count;
    }*/

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "VoterGmail";    // Database Name
        private static final String TABLE_NAME = "VoterData";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="UserId";     // Column I (Primary Key)
        private static final String PHONE = "PhoneNo";    //Column II
        private static final String OTP= "OTP";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PHONE+" VARCHAR(255) ,"+ OTP+" VARCHAR(225));";
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
    }}