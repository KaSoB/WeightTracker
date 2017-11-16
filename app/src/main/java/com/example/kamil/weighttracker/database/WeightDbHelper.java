package com.example.kamil.weighttracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kamil.weighttracker.model.Result;

import java.util.ArrayList;

/**
 * Created by Kamil on 11.11.2017.
 */

public class WeightDbHelper extends SQLiteOpenHelper{
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WeightTableInfo.TABLE_NAME +
                    " (" + WeightTableInfo._ID + " INTEGER PRIMARY KEY, " +
                    WeightTableInfo.COLUMN_NAME_WEIGHT + " INTEGER, " +
                    WeightTableInfo.COLUMN_NAME_DATE + " INTEGER)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WeightTableInfo.TABLE_NAME;
    public static  final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Weights.db";

    public WeightDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db,oldVersion,newVersion);
    }

    public boolean insertResult(Result result){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WeightTableInfo.COLUMN_NAME_WEIGHT, result.getWeightInGrams());
        values.put(WeightTableInfo.COLUMN_NAME_DATE, result.getDataLong());

        db.insert(WeightTableInfo.TABLE_NAME,null,values);
        db.close();
        return true;
    }
    public boolean updateResult(int id, Result result) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WeightTableInfo.COLUMN_NAME_WEIGHT, result.getWeightInGrams());
        values.put(WeightTableInfo.COLUMN_NAME_DATE, result.getDataLong());

        db.update(WeightTableInfo.TABLE_NAME, values, "_ID = ? ", new String[]{Integer.toString(id)});
        db.close();
        return true;
    }
    public boolean deleteResult(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(WeightTableInfo.TABLE_NAME,
                "_ID = ? ",
                new String[]{Integer.toString(id)});
        db.close();
        return result != -1;
    }
    public ArrayList<Result> getData() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                WeightTableInfo._ID,
                WeightTableInfo.COLUMN_NAME_WEIGHT,
                WeightTableInfo.COLUMN_NAME_DATE
        };


        Cursor cursor = db.query(
                WeightTableInfo.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Result> results= new ArrayList<Result>();


        int itemID;
        long date;
        int weight;
        Result result;
        while(cursor.moveToNext()) {
            itemID = cursor.getInt(cursor.getColumnIndexOrThrow(WeightTableInfo._ID));
            date = cursor.getLong(cursor.getColumnIndexOrThrow(WeightTableInfo.COLUMN_NAME_DATE));
            weight = cursor.getInt(cursor.getColumnIndexOrThrow(WeightTableInfo.COLUMN_NAME_WEIGHT));
            result = new Result(itemID,weight,date);
            results.add(result);
        }
        return results;
    }

}
