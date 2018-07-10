package com.example.foodandcalorietracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class FoodTrackerDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;  //
    private static final String DATABASE_NAME = "food_tracker.db";
    private static final String TABLE_NAME = "meals";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_TYPE,KEY_CALORIES,KEY_DATE,KEY_TIME};

    public FoodTrackerDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEALS_TABLE = "CREATE TABLE meals ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "type TEXT, " +
                "calories INTEGER, " +
                "date TEXT, " +
                "time TEXT)";

        db.execSQL(CREATE_MEALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS meals");
        this.onCreate(db);
    }

    public boolean addMeal(Meal meal){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, meal.getName());
        values.put(KEY_TYPE, meal.getType());
        values.put(KEY_CALORIES, meal.getCalories());
        values.put(KEY_DATE, meal.getDate());
        values.put(KEY_TIME, meal.getTime());

        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Meal getMeal(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_NAME, COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        //id, name type, calories, date, time, all we added is the cursor at end with index 5
        Meal meal = new Meal((Integer.parseInt(cursor.getString(0))), cursor.getString(0), cursor.getString(2),
                (Integer.parseInt(cursor.getString(3))), cursor.getString(4),cursor.getString(5));

        return meal;
    }

    public ArrayList<Meal> getTodaysMeals(String date) {
        ArrayList<Meal> allMeals = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_NAME +" ORDER BY ID DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(TABLE_NAME, COLUMNS, " date = ?", new String[] { date },
                        null, null, null, null);

        Meal meal = null;
        if (cursor.moveToFirst()){//cursor.getColumnName(5).equals(date)){
            do {
                meal = new Meal();
                meal.setId(Integer.parseInt(cursor.getString(0)));
                meal.setName(cursor.getString(1));
                meal.setType(cursor.getString(2));
                meal.setCalories(Integer.parseInt(cursor.getString(3)));
                meal.setDate(cursor.getString(4));
                meal.setTime(cursor.getString(5));

                allMeals.add(meal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allMeals;
    }

    //for displaying entire history of meals
    public ArrayList<Meal> getAllMeals() {
        ArrayList<Meal> allMeals = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY ID DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Meal meal = null;
        if (cursor.moveToFirst()) {
            do {
                meal = new Meal();
                meal.setId(Integer.parseInt(cursor.getString(0)));
                meal.setName(cursor.getString(1));
                meal.setType(cursor.getString(2));
                meal.setCalories(Integer.parseInt(cursor.getString(3)));
                meal.setDate(cursor.getString(4));
                meal.setTime(cursor.getString(5));

                allMeals.add(meal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allMeals;
    }

    public ArrayList<Meal> getLastMeal() {
        ArrayList<Meal> allMeals = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY ID DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Meal meal = null;
        if (cursor.moveToFirst()) {
            meal = new Meal();
            meal.setId(Integer.parseInt(cursor.getString(0)));
            meal.setName(cursor.getString(1));
            meal.setType(cursor.getString(2));
            meal.setCalories(Integer.parseInt(cursor.getString(3)));
            meal.setDate(cursor.getString(4));
            meal.setTime(cursor.getString(5));
            allMeals.add(meal);
        }
        cursor.close();
        return allMeals;
    }

    public int updateMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, meal.getName());
        values.put(KEY_TYPE, meal.getType());
        values.put(KEY_CALORIES, meal.getCalories());
        values.put(KEY_DATE, meal.getDate());
        values.put(KEY_TIME, meal.getTime());

        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] {String.valueOf(meal.getId())});
    }

    public void deleteMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(meal.getId())});
        db.close();
    }

    public void deleteAllMeals() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}