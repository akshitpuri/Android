package com.practice.bunktracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Mydatabasehelper extends SQLiteOpenHelper {
    static final String bunkedClasses = "bunkedClasses";
    private static final String CREATE = "CREATE TABLE SubjectData (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, minPercent TEXT NOT NULL, bunkedClasses TEXT NOT NULL, totalClasses TEXT NOT NULL ,percent TEXT NOT NULL )";
    static final String mydb = "SubjectDataBase";
    static final String minPercent = "minPercent";
    static final String NAME = "SubjectData";
    static final String percent = "percent";
    static final String name = "name";
    static final String totalClasses = "totalClasses";
    private static final int VERSION = 1;
    static final String _ID = "_id";
    static final String[] columnPercentageAndBunked = new String[]{_ID, percent, bunkedClasses};
    static final String[] columnSubjectNameandPercentage = new String[]{_ID, name, percent, minPercent};
    static final String[] columns = new String[]{_ID, name,minPercent, totalClasses, bunkedClasses,percent};
    public final Context mContext;

    public Mydatabasehelper(Context c) {
        super(c, mydb, null, 1);
        this.mContext = c;
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE);

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SubjectData");
        onCreate(sqLiteDatabase);
    }
}