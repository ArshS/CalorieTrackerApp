package com.example.foodandcalorietracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserSettingsDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "user_database.db";
    private static final String TABLE_NAME = "settings";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_AGE = "age";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_ACTIVITY = "activity";
    private static final String KEY_GOALS = "goals";
    private static final String KEY_BMR = "BMR";
    private static final String KEY_DAILYCALORIES = "dailycalories";
    private static final String[] COLUMNS = {KEY_ID,KEY_FIRSTNAME,KEY_LASTNAME,KEY_GENDER,KEY_AGE,KEY_WEIGHT,KEY_HEIGHT,KEY_ACTIVITY,KEY_GOALS,KEY_BMR,KEY_DAILYCALORIES};

    public UserSettingsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SETTINGS_TABLE = "CREATE TABLE settings ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstname TEXT, " +
                "lastname TEXT, " +
                "gender TEXT," +
                "age INTEGER," +
                "weight DOUBLE, " +
                "height DOUBLE, " +
                "activity TEXT," +
                "goals TEXT,"+
                "BMR DOUBLE,"+
                "dailycalories)";

        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS settings");
        this.onCreate(db);
    }

    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, user.getFirstName());
        values.put(KEY_LASTNAME, user.getLastName());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_WEIGHT, user.getWeight());
        values.put(KEY_HEIGHT, user.getHeight());
        values.put(KEY_ACTIVITY, user.getActivity());
        values.put(KEY_GOALS, user.getGoals());
        values.put(KEY_BMR, user.getBMR());
        values.put(KEY_DAILYCALORIES ,user.getDailyCalories());

        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void clearUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }


    public User getUsers() {

        String query = "SELECT  * FROM " + TABLE_NAME +" ORDER BY ID DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        User user = null;
        if (cursor.moveToFirst()){
            user = new User();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setFirstName(cursor.getString(1));
            user.setLastName(cursor.getString(2));
            user.setGender(cursor.getString(3));
            user.setAge(Integer.parseInt(cursor.getString(4)));
            user.setWeight(Double.parseDouble(cursor.getString(5)));
            user.setHeight(Double.parseDouble(cursor.getString(6)));
            user.setActivity(cursor.getString(7));
            user.setGoals(cursor.getString(8));
            user.calculateBMR();
            user.solveDailyCalorieAmount();
            user.solveDesiredCaloricIntake();
        }
        cursor.close();
        return user;
    }
}