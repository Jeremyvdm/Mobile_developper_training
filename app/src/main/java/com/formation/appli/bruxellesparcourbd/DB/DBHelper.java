package com.formation.appli.bruxellesparcourbd.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "com.formation.appli.bruxellesparcourbd.DB_parcourtBD";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
