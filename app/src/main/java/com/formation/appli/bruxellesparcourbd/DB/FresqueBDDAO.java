package com.formation.appli.bruxellesparcourbd.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.formation.appli.bruxellesparcourbd.model.Coordonees;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */

public class FresqueBDDAO {
    //region table and column name
    public final static String FRESQUETABLE = "fresque_bd";
    public final static String COLUMN_FRESQUEID = "_fresqueID";
    public final static String COLUMN_TITRE = "titre";
    public final static String COLUMN_AUTEUR ="auteur";
    public final static String COLUMN_YEAR = "year";
    private final static String COLUMN_LONGITUDE = "longitude";
    private final static String COLUON_LATITUDE = "latitude";
    private final static String COLUMN_RESSOURCEIMAGE = "ressourceImage";
    //endregion

    //region SQLite Request
    public final static String CREATE_FRESQUE_TABLE = "CREATE TABLE IF NOT EXIST " + FresqueBDDAO.FRESQUETABLE + " { /n" +
            FresqueBDDAO.COLUMN_FRESQUEID + " INTEGER PRIMARY KEY AUTOINCREMENT , /n" +
            FresqueBDDAO.COLUMN_TITRE + " STRING NOT NULL, /n" +
            FresqueBDDAO.COLUMN_AUTEUR + " STRING NOT NULL, /n" +
            FresqueBDDAO.COLUMN_YEAR + "INTEGER NOT NULL, /n" +
            FresqueBDDAO.COLUMN_LONGITUDE + "STRING NOT NULL, /n"+
            FresqueBDDAO.COLUMN_LONGITUDE + "STRING NOT NULL, /n"+
            FresqueBDDAO.COLUMN_RESSOURCEIMAGE + "STRING NOT NULL, /n"+
            " } ";

    public static String DELETE_FRESQUE_TABLE = "DROP TABLE IF EXSITE " + FresqueBDDAO.FRESQUETABLE;
    //endregion

    //region Acces DB
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public FresqueBDDAO(Context context){
        this.context = context;
    }

    public FresqueBDDAO OpenReadable(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public FresqueBDDAO OpenWritable(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
        dbHelper.close();
    }

    //endregion

    public FresqueBD insertFresqueBd(FresqueBD fbd){
        ContentValues cv = fresqueBDContentValue(fbd);
        long fresqueId = db.insert(FRESQUETABLE,null,cv);
        fbd.set_fresqueId((int) fresqueId);
        if(fresqueId != -1){
            return fbd;
        }
        else return null;
    }

    private ContentValues fresqueBDContentValue(FresqueBD fbd) {
        ContentValues cv = fresqueBDContentValue(fbd);
        cv.put(COLUMN_TITRE,fbd.getTitre());
        cv.put(COLUMN_AUTEUR,fbd.getAuteur());
        cv.put(COLUMN_YEAR,fbd.getYears());
        Coordonees coordonees = fbd.getCoordonees();
        cv.put(COLUMN_LONGITUDE, coordonees.getLongitude());
        cv.put(COLUON_LATITUDE, coordonees.getLatitude());
        cv.put(COLUMN_RESSOURCEIMAGE,fbd.getRessourceImage());
        return cv;
    }

    public void deleteFresqueBd(FresqueBD fbd){
        String whereClause = COLUMN_FRESQUEID + " = " + fbd.get_fresqueId();
        db.delete(FRESQUETABLE,whereClause,null);
    }

    public long updateFrequeBD(FresqueBD fbd){
        ContentValues cv = new ContentValues();
        String whereClause = COLUMN_FRESQUEID + " = " + fbd.get_fresqueId();
        long fresqueId = db.insert(FRESQUETABLE,null,cv);
        return fresqueId;
    }

    private FresqueBD CursorToUser(Cursor c){
        int fresque_id = c.getInt(c.getColumnIndex(COLUMN_FRESQUEID));
        String titre = c.getString(c.getColumnIndex(COLUMN_TITRE));
        String auteur = c.getString(c.getColumnIndex(COLUMN_AUTEUR));
        int year = c.getInt(c.getColumnIndex(COLUMN_YEAR));
        double longitude = c.getDouble(c.getColumnIndex(COLUMN_LONGITUDE));
        double latitude = c.getDouble(c.getColumnIndex(COLUON_LATITUDE));
        Coordonees coordonees = new Coordonees(longitude,latitude);
        String ressoureIamge = c.getString(c.getColumnIndex(COLUMN_RESSOURCEIMAGE));


        return new FresqueBD(fresque_id,titre,auteur,year,coordonees,ressoureIamge);
    }

    public FresqueBD getUserById(int id){
        String whereClause = COLUMN_FRESQUEID + " = " + id;
        Cursor c = db.query(FRESQUETABLE, null, whereClause, null, null, null, null);

        int count = c.getCount();
        if(count>0){
            c.moveToFirst();
            FresqueBD fbd = CursorToUser(c);
            return fbd;
        }
        return null;

    }
}
