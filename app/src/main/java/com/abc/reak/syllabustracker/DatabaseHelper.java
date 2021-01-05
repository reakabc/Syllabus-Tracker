package com.abc.reak.syllabustracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CHAPTER_TABLE = "CHAPTERS";
    public static final String CHAPTER_ID = "CHAPTER_ID";
    public static final String CHAPTER_NAME = "CHAPTER_NAME";
    public static final String SUBJECT = "SUBJECT";
    public static final String PERCENTAGE = "PERCENTAGE";
    public static final String JEE_PERCENTAGE = "JEE_PERCENTAGE";
    public static final String NEET_PERCENTAGE = "NEET_PERCENTAGE";
    public static final String IS_THEORY_COMPLETED = "IS_THEORY_COMPLETED";
    public static final String IS_NUMERICAL_COMPLETED = "IS_NUMERICAL_COMPLETED";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE "+CHAPTER_TABLE+" ("+CHAPTER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CHAPTER_NAME+" TEXT, "+SUBJECT+" TEXT, "+PERCENTAGE+" REAL, "+JEE_PERCENTAGE+" REAL, "+NEET_PERCENTAGE+" REAL, "+IS_THEORY_COMPLETED+" INTEGER, "+IS_NUMERICAL_COMPLETED+" INTEGER)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Chapter chapter){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CHAPTER_NAME, chapter.getName());
        cv.put(SUBJECT, chapter.getSubject());
        cv.put(PERCENTAGE, chapter.getPercentage());
        cv.put(JEE_PERCENTAGE, chapter.getJee_percentage());
        cv.put(NEET_PERCENTAGE, chapter.getNeet_percentage());
        cv.put(IS_THEORY_COMPLETED, chapter.isIs_theory_completed() ? 1 : 0);
        cv.put(IS_NUMERICAL_COMPLETED, chapter.isIs_numerical_completed() ? 1 : 0);

        long insert = db.insert(CHAPTER_TABLE, null, cv);

        return insert >= 0;
    }

    public List<Chapter> selectAll(){

        List<Chapter> returnList  = new ArrayList<>();

        String queryString = "SELECT * FROM "+CHAPTER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){

            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String subject = cursor.getString(2);
                float percentage = cursor.getInt(3);
                float jee_percentage = cursor.getInt(4);
                int neet_percentage = cursor.getInt(5);
                boolean isTheoryCompleted = cursor.getInt(6) == 1 ? true : false;
                boolean isNumericalCompleted = cursor.getInt(7) == 1 ? true : false;

                Chapter chapter = new Chapter(id, name, subject, percentage, jee_percentage, neet_percentage, isTheoryCompleted, isNumericalCompleted);
                returnList.add(chapter);

            }while (cursor.moveToNext());

        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public List<Chapter> selectSubjectWise(String subject){

        List<Chapter> returnList  = new ArrayList<>();

        String queryString = "SELECT * FROM "+CHAPTER_TABLE+" WHERE "+SUBJECT+" = '"+subject+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){

            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                //String subject = cursor.getString(2);
                float percentage = cursor.getInt(3);
                float jee_percentage = cursor.getInt(4);
                int neet_percentage = cursor.getInt(5);
                boolean isTheoryCompleted = cursor.getInt(6) == 1;
                boolean isNumericalCompleted = cursor.getInt(7) == 1;

                Chapter chapter = new Chapter(id, name, subject, percentage, jee_percentage, neet_percentage, isTheoryCompleted, isNumericalCompleted);
                returnList.add(chapter);

            }while (cursor.moveToNext());

        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public boolean markTheoryAsDone(Chapter chapter){

        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "UPDATE "+CHAPTER_TABLE+" SET "+IS_THEORY_COMPLETED+" = '1' WHERE "+CHAPTER_ID+" = '"+chapter.getId()+"'";
        Cursor cursor = db.rawQuery(deleteQuery, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }

    public boolean markTheoryAsNotDone(Chapter chapter){

        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "UPDATE "+CHAPTER_TABLE+" SET "+IS_THEORY_COMPLETED+" = '0' WHERE "+CHAPTER_ID+" = '"+chapter.getId()+"'";
        Cursor cursor = db.rawQuery(deleteQuery, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }

    public boolean markNumericalAsDone(Chapter chapter) {

        SQLiteDatabase db = getWritableDatabase();
        String updateQuery = "UPDATE " + CHAPTER_TABLE + " SET " + IS_NUMERICAL_COMPLETED + " = '1' WHERE " + CHAPTER_ID + " = '" + chapter.getId()+"'";
        Cursor cursor = db.rawQuery(updateQuery, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean markNumericalAsNotDone(Chapter chapter) {

        SQLiteDatabase db = getWritableDatabase();
        String updateQuery = "UPDATE " + CHAPTER_TABLE + " SET " + IS_NUMERICAL_COMPLETED + " = '0' WHERE " + CHAPTER_ID + " = '" + chapter.getId()+"'";
        Cursor cursor = db.rawQuery(updateQuery, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    public float[] getCompletedPercentage(String subject){

        float[] result = new float[3];

        SQLiteDatabase db = getWritableDatabase();
        String sumQuery = "SELECT SUM(" + PERCENTAGE + ") as percentage,  SUM("+JEE_PERCENTAGE+") as jee_percentage, SUM("+NEET_PERCENTAGE+") as neet_percentage FROM " + CHAPTER_TABLE + " WHERE " + SUBJECT + " = '" + subject + "' AND "+IS_THEORY_COMPLETED+" = '1' AND "+IS_NUMERICAL_COMPLETED+"= '1'";
        Cursor cursor = db.rawQuery(sumQuery, null);
        if (cursor.moveToFirst()) {

            float percentage = cursor.getFloat(cursor.getColumnIndex("percentage"));
            float jee_percentage = cursor.getFloat(cursor.getColumnIndex("jee_percentage"));
            float neet_percentage = cursor.getFloat(cursor.getColumnIndex("neet_percentage"));

            result[0] = percentage;
            result[1] = jee_percentage;
            result[2] = neet_percentage;

        } else {
            return null;
        }

        return result;

    }

}
