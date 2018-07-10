package com.example.foodandcalorietracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.util.Calendar;

public class InsertActivity extends AppCompatActivity {

    //meal, static ints for storing date and time, database
    Meal meal;
    static int Day, Month, Year;
    static int Hour, Minute;
    public static String aMpM;
    FoodTrackerDatabase mFoodTrackerDatabase;// = new FoodTrackerDatabase(this);

    //name, choice, cals, time, date, and add meal button
    public EditText mealName;
    public Spinner mealSpinner;
    public EditText calorieConsumed;
    public Button timePicker;
    public Button datePicker;
    public Button addMeal;

    //displays for time and date
    public static TextView displayDate;
    public static TextView displayTime;

    //if user wants to choose date
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            displayDate = (TextView) getActivity().findViewById(R.id.DisplayDate);
            displayDate.setText("");

            Day = day;
            Month = month;
            Year = year;

            displayDate.setText(displayDate.getText() + String.valueOf(year) + "-" + String.valueOf(++month) + "-" + String.valueOf(day));
        }
    }

    //if user wants to choose time
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            //int hour = c.get(Calendar.HOUR);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            displayTime = (TextView) getActivity().findViewById(R.id.DisplayTime);
            displayTime.setText("");

            //Get the AM or PM for current time
            aMpM = "AM";
            if(hourOfDay >11)
            {
                aMpM = "PM";
            }
            //Make the 24 hour time format to 12 hour time format
            int currentHour;
            if(hourOfDay>11) {
                currentHour = hourOfDay -12;
            }
            else
            {
                currentHour = hourOfDay;
            }

            Hour = currentHour;
            Minute = minute;
            if(currentHour==0)
            {
                currentHour+=12;
            }

            if(minute<10)
            {
                displayTime.setText(displayTime.getText()+ String.valueOf(currentHour) + ":" + "0"+String.valueOf(minute) + " " + aMpM);
            }
            else
            {
                displayTime.setText(displayTime.getText()+ String.valueOf(currentHour) + ":" + String.valueOf(minute) + " " + aMpM);
            }
        }
    }

    //for displaying default time
    void setInitialTime()
    {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        displayTime = (TextView)findViewById(R.id.DisplayTime);
        displayTime.setText("");

        //Get the AM or PM for current time
        aMpM = "AM";
        if(hour >11)
        {
            aMpM = "PM";
        }
        //Make the 24 hour time format to 12 hour time format
        int currentHour;
        if(hour>11) {
            currentHour = hour -12;
        }
        else
        {
            currentHour = hour;
        }

        Hour = currentHour;
        Minute = minute;

        if(currentHour==0)
        {
            currentHour+=12;
        }

        if(minute<10)
        {
            displayTime.setText(displayTime.getText()+ String.valueOf(currentHour) + ":" + "0"+String.valueOf(minute) + " " + aMpM);
        }
        else
        {
            displayTime.setText(displayTime.getText()+ String.valueOf(currentHour) + ":" + String.valueOf(minute) + " " + aMpM);
        }
    }

    //for displaying default date
    void setInitialDate()
    {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        displayDate = (TextView)findViewById(R.id.DisplayDate);
        displayDate.setText("");

        Day = day;
        Month = month;
        Year = year;

        displayDate.setText(displayDate.getText() + String.valueOf(year) + "-" + String.valueOf(++month) + "-" + String.valueOf(day));
    }

    //text watcher for checking if empty fields
    public TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmpty();
        }
    };

    void checkFieldsForEmpty()
    {
        addMeal = (Button)findViewById(R.id.AddMeal);

        String checkCal = calorieConsumed.getText().toString();
        String checkMeal = mealName.getText().toString();

        if(checkCal.equals("") || checkMeal.equals(""))
        {
            addMeal.setBackgroundColor(Color.rgb(238,238,238));
            addMeal.setEnabled(false);
        }
        else
        {
            addMeal.setBackgroundColor(Color.rgb(255,64,129));
            addMeal.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        mFoodTrackerDatabase = new FoodTrackerDatabase(this);
        //mFoodTrackerDatabase.getWritableDatabase();
        //mFoodTrackerDatabase.getReadableDatabase();
        //SettingsDatabase.getWritableDatabase();
        //SettingsDatabase.getReadableDatabase();
        //setContentView(R.layout.activity_insert);

        setTitle("Insert");

        Button insert = (Button) findViewById(R.id.Insert);
        Button history = (Button) findViewById(R.id.History);
        Button home = (Button) findViewById(R.id.Home);
        Button settings = (Button) findViewById(R.id.Settings);

        insert.setBackgroundColor(Color.rgb(255,64,129));

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertActivity.this, InsertActivity.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertActivity.this, MainActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertActivity.this, HistoryActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertActivity.this, SettingsActivity.class));
            }
        });

        //time
        timePicker = (Button) findViewById(R.id.TimePicker);
        timePicker.setBackgroundColor(Color.rgb(255,64,129));
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        //date
        datePicker = (Button) findViewById(R.id.DatePicker);
        datePicker.setBackgroundColor(Color.rgb(255,64,129));
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"datePicker");
            }
        });

        //mealspinner
        mealSpinner = (Spinner)findViewById(R.id.MealSpinner);
        String[] mealTypes = new String[]{"Breakfast", "Lunch", "Dinner", "Snack", "Drink"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mealTypes);
        mealSpinner.setAdapter(adapter);

        //calories(number) and meal name(string)
        calorieConsumed = (EditText)findViewById(R.id.CalorieConsumed);
        mealName = (EditText) findViewById(R.id.MealName);

        calorieConsumed.addTextChangedListener(mTextWatcher);
        mealName.addTextChangedListener(mTextWatcher);
        setInitialTime();
        setInitialDate();
        checkFieldsForEmpty();

        addMeal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //spinner choice, calories, date and time
                String choice = mealSpinner.getSelectedItem().toString();

                int calories = Integer.parseInt(calorieConsumed.getText().toString());

                String thisDate = String.valueOf(Year) + "-" + String.valueOf(++Month) + "-" + String.valueOf(Day);

                String thisTime;

                if(Hour==0)
                {
                    Hour+=12;
                }
                if(Minute<10)
                {
                    thisTime = String.valueOf(Hour) + ":" + "0" + String.valueOf(Minute) + aMpM;
                }
                else
                {
                    thisTime = String.valueOf(Hour)+ ":" + String.valueOf(Minute) + aMpM;
                }

                meal = new Meal(mealName.getText().toString(), choice, calories, thisDate, thisTime);

                boolean isInserted = mFoodTrackerDatabase.addMeal(meal);

                if (isInserted == true) {
                    Toast.makeText(InsertActivity.this, "Meal Inserted!", Toast.LENGTH_SHORT).show();
                    Intent intentInput = new Intent(InsertActivity.this, InsertActivity.class);
                    startActivity(intentInput);
                }
                else
                {
                    Toast.makeText(InsertActivity.this, "Meal Not Inserted!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
