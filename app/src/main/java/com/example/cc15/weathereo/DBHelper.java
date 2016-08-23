package com.example.cc15.weathereo;

/**
 * Created by cc15 on 23/8/16.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String WEATHER_TABLE_NAME = "weather";
    public static final String WEATHER_COLUMN_ID = "id";
    public static final String WEATHER_COLUMN_DAYNDATE = "dayndate";
    public static final String WEATHER_COLUMN_TYPE = "type";
    public static final String WEATHER_COLUMN_MAXTEMP = "maxtemp";
    public static final String WEATHER_COLUMN_MINTEMP = "mintemp";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " +WEATHER_TABLE_NAME+ "("+WEATHER_COLUMN_ID + " integer primary key," + WEATHER_COLUMN_DAYNDATE + " text,"+ WEATHER_COLUMN_TYPE + " text," + WEATHER_COLUMN_MAXTEMP + " text," + WEATHER_COLUMN_MINTEMP + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + WEATHER_TABLE_NAME);
        onCreate(db);
    }

    public boolean refresh(){

        SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("DROP TABLE " + WEATHER_TABLE_NAME);
       onCreate(db);
        return true;
    }

    public boolean insertData  (ArrayList<String> dayanddate, ArrayList<String> weathertype, ArrayList<String> maxtemp, ArrayList<String> mintemp) {

        SQLiteDatabase db = this.getWritableDatabase();
        int i = 0;
        while (i < dayanddate.size()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(WEATHER_COLUMN_DAYNDATE, dayanddate.get(i));
            contentValues.put(WEATHER_COLUMN_TYPE, weathertype.get(i));
            contentValues.put(WEATHER_COLUMN_MAXTEMP, maxtemp.get(i));
            contentValues.put(WEATHER_COLUMN_MINTEMP, mintemp.get(i));
            db.insert(WEATHER_TABLE_NAME, null, contentValues);
            i++;
        }
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " +WEATHER_TABLE_NAME + " where id=" + id + "", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, WEATHER_TABLE_NAME);
        return numRows;
    }

    /*public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }*/

    /*public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }*/

    public ArrayList<String> getData(String columnname)
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + WEATHER_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(columnname)));
            res.moveToNext();
        }
        return array_list;
    }
}