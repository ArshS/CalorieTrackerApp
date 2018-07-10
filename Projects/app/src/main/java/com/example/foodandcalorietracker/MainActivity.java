package com.example.foodandcalorietracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int second = c.get(Calendar.SECOND);
    int minute = c.get(Calendar.MINUTE);

    FoodTrackerDatabase mFoodTrackerDatabase = new FoodTrackerDatabase(this);
    UserSettingsDatabase SettingsDatabase = new UserSettingsDatabase(this);
    TextView displayMessage, totalCaloriesAllowed,caloriesConsumed,caloriesRemaining,displayMeal;

    public void displayAllowedCalories()
    {
        totalCaloriesAllowed = (TextView)findViewById(R.id.TotalCaloriesAllowed);
        if(SettingsDatabase.getUsers()!=null) {
            int dailyCalories = (int) SettingsDatabase.getUsers().getDailyCalories();
            String display;
            display = "Daily Calorie Amount: " + dailyCalories;
            totalCaloriesAllowed.setText(display);
        }
    }

    public void displayCaloriesConsumed()
    {
        if(SettingsDatabase.getUsers()!=null){// && mFoodTrackerDatabase.get) {
            int m = month + 1;
            String mealDate = String.valueOf(year) + "-" + String.valueOf(m) + "-" + String.valueOf(day);
            caloriesConsumed = findViewById(R.id.CaloriesConsumed);
            ArrayList<Meal> results = mFoodTrackerDatabase.getTodaysMeals(mealDate);

            int total = 0;
            for (Meal result : results) {
                total += result.getCalories();
            }
            String display;
            display = "Calories Consumed Today: " + total;
            caloriesConsumed.setText(display);
        }
    }

    public void displayCaloriesRemaining()
    {
        if(SettingsDatabase.getUsers()!=null) {
            int m = month + 1;
            caloriesRemaining = (TextView) findViewById(R.id.CaloriesRemaining);
            int dailyCalories = (int) SettingsDatabase.getUsers().getDailyCalories();
            String mealDate = String.valueOf(year) + "-" + String.valueOf(m) + "-" + String.valueOf(day);
            caloriesConsumed = findViewById(R.id.CaloriesConsumed);
            ArrayList<Meal> results = mFoodTrackerDatabase.getTodaysMeals(mealDate);

            int total = 0;
            for (Meal result : results) {
                total += result.getCalories();
            }

            if ((dailyCalories - total) > dailyCalories * .75) {
                caloriesRemaining.setTextColor(Color.GREEN);
            } else if ((dailyCalories - total) > dailyCalories * .50) {
                caloriesRemaining.setTextColor(Color.rgb(255, 215, 0));
            } else if ((dailyCalories - total) > dailyCalories * .25) {
                caloriesRemaining.setTextColor(Color.rgb(255, 165, 0));
            } else {
                caloriesRemaining.setTextColor(Color.rgb(255, 0, 0));
            }

            String display;
            display = "Calories Remaining: " + (dailyCalories - total);
            caloriesRemaining.setText(display);
        }
    }

    public void displayLastMeal()
    {
        if(SettingsDatabase.getUsers()!=null) {
            ArrayList<Meal> results = mFoodTrackerDatabase.getLastMeal();
            displayMeal = findViewById(R.id.DisplayMeal);

            for (Meal result : results) {
                displayMeal.append("Date: " + result.getDate() + "\n");
                displayMeal.append("Time: " + result.getTime() + "\n");
                displayMeal.append("Name: " + result.getName() + "\n");
                displayMeal.append("Type: " + result.getType() + "\n");
                displayMeal.append("Calories: " + result.getCalories() + "\n\n");
            }
        }
    }

    public void displayMessage()
    {
        if(SettingsDatabase.getUsers()!=null) {
            String fName = SettingsDatabase.getUsers().getFirstName();
            String lName = SettingsDatabase.getUsers().getLastName();
            displayMessage = findViewById(R.id.DisplayMessage);
            int min = 0;
            int max = 5;
            String Message;
            String[] messages = new String[]{", how are you today?",
                    "! Staying fit I hope!",
                    ". Strive for progress, not perfection!",
                    ". Hustle for that muscle!",
                    ". Enjoying the weather?"};
            String m1 = "Hello, ";
            Random r = new Random();
            int i1 = r.nextInt(max - min) + min;
            Message = m1 + fName + messages[i1];
            displayMessage.setText(Message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Home");

        Button insert = (Button)findViewById(R.id.Insert);
        Button history = (Button)findViewById(R.id.History);
        Button home = (Button)findViewById(R.id.Home);
        Button settings = (Button)findViewById(R.id.Settings);

        home.setBackgroundColor(Color.rgb(255,64,129));

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        displayMessage();

        displayAllowedCalories();

        displayCaloriesConsumed();

        displayCaloriesRemaining();

        displayLastMeal();
    }

}
