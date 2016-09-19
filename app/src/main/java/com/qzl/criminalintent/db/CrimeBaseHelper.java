package com.qzl.criminalintent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库的助手类
 * Created by Qzl on 2016-09-11.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSON = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行语句，创建数据库
        db.execSQL("CREATE TABLE " + CrimeDbSchema.CrimeTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                CrimeDbSchema.CrimeTable.Cols.UUID + "," +
                CrimeDbSchema.CrimeTable.Cols.TITLE + "," +
                CrimeDbSchema.CrimeTable.Cols.DATE + "," +
                CrimeDbSchema.CrimeTable.Cols.SOLVED + "," +
                CrimeDbSchema.CrimeTable.Cols.SUSPECT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
