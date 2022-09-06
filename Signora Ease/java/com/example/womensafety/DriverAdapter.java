package com.example.womensafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DriverAdapter {
    myDbHelper myhelper;
    public DriverAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String vdnm,String vcart,String vcarno,String vmob)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.DNAME, vdnm);
        contentValues.put(myDbHelper.CARTYPE, vcart);
        contentValues.put(myDbHelper.CARNO, vcarno);
        contentValues.put(myDbHelper.MOBNO, vmob);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData(String vctype)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.DID, myDbHelper.DNAME, myDbHelper.CARTYPE, myDbHelper.CARNO, myDbHelper.MOBNO};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String vvct=cursor.getString(cursor.getColumnIndex(myDbHelper.CARTYPE));
            if(vvct.equals(vctype)) {
                String vvdn = cursor.getString(cursor.getColumnIndex(myDbHelper.DNAME));
                String vvcn = cursor.getString(cursor.getColumnIndex(myDbHelper.CARNO));
                String vvmb = cursor.getString(cursor.getColumnIndex(myDbHelper.MOBNO));
                buffer.append(vvdn+"/"+vvcn+"/"+vvmb+ " \n");
            }

        }
        return buffer.toString();
    }
    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.DID+" = ?",whereArgs);
        return  count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "VoterGmail10";    // Database Name
        private static final String TABLE_NAME = "Driver";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String DID="DId";
        private static final String DNAME = "Source";
        private static final String CARTYPE = "Destination";
        private static final String CARNO = "CarType";
        private static final String MOBNO = "MobileNo";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+DID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+DNAME+" VARCHAR(255),"+CARTYPE+" VARCHAR(255),"+CARNO+" VARCHAR(255),"+MOBNO+" VARCHAR(255));";
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
