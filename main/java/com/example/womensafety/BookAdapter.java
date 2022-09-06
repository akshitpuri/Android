package com.example.womensafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookAdapter {
    myDbHelper myhelper;
    public BookAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String vcid,String vsource,String vdes,String vcartype,String vdate,String vtime,String vdname)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.CID,vcid);
        contentValues.put(myDbHelper.SOURCE, vsource);
        contentValues.put(myDbHelper.DESTINATION, vdes);
        contentValues.put(myDbHelper.CARTYPE, vcartype);

        contentValues.put(myDbHelper.DATE, vdate);
        contentValues.put(myDbHelper.TIME, vtime);
        contentValues.put(myDbHelper.DNAME, vdname);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.ORDERID, myDbHelper.CID, myDbHelper.SOURCE, myDbHelper.DESTINATION, myDbHelper.CARTYPE, myDbHelper.DATE, myDbHelper.TIME, myDbHelper.DNAME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String vvid =cursor.getString(cursor.getColumnIndex(myDbHelper.CID));
            String vvso =cursor.getString(cursor.getColumnIndex(myDbHelper.SOURCE));
            String vvdes=cursor.getString(cursor.getColumnIndex(myDbHelper.DESTINATION));
            String vvct=cursor.getString(cursor.getColumnIndex(myDbHelper.CARTYPE));

            String vvda=cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
            String vvti=cursor.getString(cursor.getColumnIndex(myDbHelper.TIME));
            String vvdn=cursor.getString(cursor.getColumnIndex(myDbHelper.DNAME));

            buffer.append(vvid +"   "+vvso+"   "+vvdes+"   "+vvct+"  "+vvda+"   "+vvti+"   "+vvdn+ " \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.CID+" = ?",whereArgs);
        return  count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "VoterGmail9";    // Database Name
        private static final String TABLE_NAME = "Book";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String ORDERID="OId";
        private static final String CID="CId";
        private static final String SOURCE = "Source";
        private static final String DESTINATION = "Destination";
        private static final String CARTYPE = "CarType";

        private static final String DATE = "Date";
        private static final String TIME= "Time";
        private static final String DNAME= "DriverName";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+ORDERID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CID+" VARCHAR(255),"+SOURCE+" VARCHAR(255),"+DESTINATION+" VARCHAR(255),"+CARTYPE+" VARCHAR(255),"+DATE+" VARCHAR(255),"+TIME+" VARCHAR(255),"+DNAME+" VARCHAR(225));";
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
