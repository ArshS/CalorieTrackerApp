package com.example.foodandcalorietracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity {

    FoodTrackerDatabase mFoodTrackerDatabase;
    Button allMeals;
    Button todaysMeal;
    DatePicker datePicker;

    static int Day, Month, Year;
    String thisDate;

    String getDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Day = day;
        Month = month;
        Year = year;

        thisDate = String.valueOf(Year) + "-" + String.valueOf(++Month) + "-" + String.valueOf(Day);
        return thisDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mFoodTrackerDatabase = new FoodTrackerDatabase(this);
        //mFoodTrackerDatabase.getWritableDatabase();
        //mFoodTrackerDatabase.getReadableDatabase();
        //SettingsDatabase.getWritableDatabase();
        //SettingsDatabase.getReadableDatabase();
        //setContentView(R.layout.activity_history);

        setTitle("History");

        Button insert = (Button)findViewById(R.id.Insert);
        Button history = (Button)findViewById(R.id.History);
        Button home = (Button)findViewById(R.id.Home);
        Button settings = (Button)findViewById(R.id.Settings);

        history.setBackgroundColor(Color.rgb(255,64,129));

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(HistoryActivity.this, InsertActivity.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(HistoryActivity.this, MainActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(HistoryActivity.this, HistoryActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(HistoryActivity.this, SettingsActivity.class));
            }
        });

      //  mFoodTrackerDatabase = new FoodTrackerDatabase(this);

        allMeals = (Button) findViewById(R.id.AllMeals);
        allMeals.setBackgroundColor(Color.rgb(255,64,129));
        allMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ArrayList<Meal> results = mFoodTrackerDatabase.getAllMeals();

                StringBuffer buffer = new StringBuffer();
                for (Meal result : results)
                {
                    buffer.append("Date: " + result.getDate() + "\n");
                    buffer.append("Time: " + result.getTime() + "\n");
                    buffer.append("Name: " + result.getName() + "\n");
                    buffer.append("Type: " + result.getType() + "\n");
                    buffer.append("Calories: " + result.getCalories() + "\n\n");
                }
                showMessage("All Meals", buffer.toString());
            }
        });

        datePicker = (DatePicker)findViewById(R.id.datePicker);

        todaysMeal = (Button)findViewById(R.id.TodaysMeals);
        todaysMeal.setBackgroundColor(Color.rgb(255,64,129));
        todaysMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                //date picker date
                String mealDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);

                //search from history activity from the database and get todays meals for
                //the date picked from the datepicker
                ArrayList<Meal> results = mFoodTrackerDatabase.getTodaysMeals(mealDate);

                StringBuffer buffer = new StringBuffer();
                for (Meal result : results)
                {
                    buffer.append("Date: " + result.getDate() + "\n");
                    buffer.append("Time: " + result.getTime() + "\n");
                    buffer.append("Name: " + result.getName() + "\n");
                    buffer.append("Type: " + result.getType() + "\n");
                    buffer.append("Calories: " + result.getCalories() + "\n\n");
                }
                showMessage("Selected Days Meals", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
