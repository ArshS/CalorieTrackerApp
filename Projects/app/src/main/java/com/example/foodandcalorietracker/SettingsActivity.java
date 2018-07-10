package com.example.foodandcalorietracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public EditText insertFirstName;
    public EditText insertLastName;
    public EditText insertWeight;
    public EditText insertFeet;
    public EditText insertInches;
    public EditText insertAge;
    public RadioGroup mfRadioGroup;
    public RadioButton male;
    public RadioButton female;
    public Spinner activitySpinner;
    public Spinner goalsSpinner;
    public Button clearDatabase;
    public Button save;
    public TextView bmrDisplay;

    User user;
    FoodTrackerDatabase mFoodTrackerDatabase;// = new FoodTrackerDatabase(this);
    UserSettingsDatabase UserDatabase;// = new UserSettingsDatabase(this);

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

    void checkFieldsForEmpty() {
        save = (Button) findViewById(R.id.SaveProfile);

        String checkFName = insertFirstName.getText().toString();
        String checkLName = insertLastName.getText().toString();
        String checkWeight = insertWeight.getText().toString();
        String checkHeight = insertFeet.getText().toString();
        String checkAge = insertAge.getText().toString();

        if (checkFName.equals("") || checkLName.equals("") || checkWeight.equals("") || checkHeight.equals("")||checkAge.equals(""))
        {
            save.setBackgroundColor(Color.rgb(238,238,238));
            save.setEnabled(false);
        } else {
            save.setBackgroundColor(Color.rgb(255,64,129));
            save.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mFoodTrackerDatabase=new FoodTrackerDatabase(this);
        //mFoodTrackerDatabase.getWritableDatabase();
        //mFoodTrackerDatabase.getReadableDatabase();
        UserDatabase= new UserSettingsDatabase(this);
        //UserDatabase.getWritableDatabase();
        //UserDatabase.getReadableDatabase();

        setTitle("Settings");

        Button insert = (Button)findViewById(R.id.Insert);
        Button history = (Button)findViewById(R.id.History);
        Button home = (Button)findViewById(R.id.Home);
        Button settings = (Button)findViewById(R.id.Settings);

        settings.setBackgroundColor(Color.rgb(255,64,129));

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SettingsActivity.this, InsertActivity.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SettingsActivity.this, HistoryActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
            }
        });


        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        clearDatabase = (Button)findViewById(R.id.ClearDatabase);
        clearDatabase.setBackgroundColor(Color.rgb(255,64,129));
        clearDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog show = builder.setTitle("Clear Meal History")
                        .setMessage("Are you sure you want to delete all meals?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                mFoodTrackerDatabase.deleteAllMeals();
                                Toast.makeText(SettingsActivity.this, "Meals cleared!!!", Toast.LENGTH_LONG).show();
                                Intent intentInput = new Intent(SettingsActivity.this, SettingsActivity.class);
                                startActivity(intentInput);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        activitySpinner = (Spinner)findViewById(R.id.ActivitySpinner);
        String[] activityTypes = new String[]{"Little to no exercise", "Light exercise(1-3 days per week)", "Moderate exersise(3-5 days per week)"
                , "Heavy exercise(6-7 days per week)", "Very Heavy exercise(twice per day, extra heavy workouts)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activityTypes);
        activitySpinner.setAdapter(adapter);

        goalsSpinner = (Spinner)findViewById(R.id.GoalsSpinner);
        String[] goalTypes = new String[]{"Maintain current weight" ,"Lose .5kg per week" ,"Lose 1kg per week","Gain .5kg per week","Gain 1kg per week"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, goalTypes);
        goalsSpinner.setAdapter(adapter2);

        insertFirstName = (EditText)findViewById(R.id.InsertFirstName);
        insertLastName = (EditText)findViewById(R.id.InsertLastName);
        insertWeight = (EditText)findViewById(R.id.InsertWeight);
        insertFeet = (EditText)findViewById(R.id.InsertFeet);
        insertInches = (EditText)findViewById(R.id.InsertInches);
        insertAge = (EditText)findViewById(R.id.InsertAge);
        bmrDisplay = (TextView)findViewById(R.id.BMRDisplay);

        male = (RadioButton)findViewById(R.id.Male);
        female = (RadioButton)findViewById(R.id.Female);
        mfRadioGroup = (RadioGroup)findViewById(R.id.MFRadioGroup);

        insertFirstName.addTextChangedListener(mTextWatcher);
        insertLastName.addTextChangedListener(mTextWatcher);
        insertWeight.addTextChangedListener(mTextWatcher);
        insertFeet.addTextChangedListener(mTextWatcher);
        insertAge.addTextChangedListener(mTextWatcher);
        checkFieldsForEmpty();

        //check if new user
        User searchUser = UserDatabase.getUsers();
        if(searchUser!=null) {
            insertFirstName.setText(searchUser.getFirstName().toString());
            insertLastName.setText(searchUser.getLastName().toString());
            insertWeight.setText(String.valueOf(searchUser.getWeight()));
            double height = searchUser.getHeight();
            int feet = (int) height / 12;
            int inches = (int) height % 12;
            insertFeet.setText(String.valueOf(feet));
            insertInches.setText(String.valueOf(inches));
            insertAge.setText(String.valueOf(searchUser.getAge()));
            bmrDisplay.setText(String.valueOf((int)searchUser.getBMR()));

            if(searchUser.getGender().equals("M"))
            {
                male.toggle();
            }
            else if(searchUser.getGender().equals("F"))
            {
                female.toggle();
            }
            if(searchUser.getActivity().equals("Little to no exercise")) {
                activitySpinner.setSelection(0);
            }
            else if (searchUser.getActivity().equals("Light exercise(1-3 days per week)"))
            {
                activitySpinner.setSelection(1);
            }
            else if (searchUser.getActivity().equals("Moderate exersise(3-5 days per week)"))
            {
                activitySpinner.setSelection(2);
            }
            else if (searchUser.getActivity().equals("Heavy exercise(6-7 days per week)"))
            {
                activitySpinner.setSelection(3);
            }
            else if (searchUser.getActivity().equals("Very Heavy exercise(twice per day, extra heavy workouts)"))
            {
                activitySpinner.setSelection(4);
            }

            if(searchUser.getGoals().equals("Maintain current weight")) {
                goalsSpinner.setSelection(0);
            }
            else if (searchUser.getGoals().equals("Lose .5kg per week"))
            {
                goalsSpinner.setSelection(1);
            }
            else if (searchUser.getGoals().equals("Lose 1kg per week"))
            {
                goalsSpinner.setSelection(2);
            }
            else if (searchUser.getGoals().equals("Gain .5kg per week"))
            {
                goalsSpinner.setSelection(3);
            }
            else if (searchUser.getGoals().equals("Gain 1kg per week"))
            {
                goalsSpinner.setSelection(4);
            }
        }
        else
        {
            insertFirstName.setText("");
            insertLastName.setText("");
            insertWeight.setText("");
            insertFeet.setText("");
            insertInches.setText("");
            insertAge.setText("");
            bmrDisplay.setText("");
            male.toggle();
        }

        save = (Button)findViewById(R.id.SaveProfile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = insertFirstName.getText().toString();
                String lastName = insertLastName.getText().toString();
                String gender="";
                if(male.isChecked()==true)
                {
                    gender = "M";
                }
                else if(female.isChecked()==true)
                {
                    gender = "F";
                }
                int age = Integer.parseInt(insertAge.getText().toString());
                double weight = Double.parseDouble(insertWeight.getText().toString());
                double feet = Double.parseDouble(insertFeet.getText().toString());
                double inches;


                if(insertInches.getText().toString().equals(""))
                {
                    inches = 0;
                }
                else
                {
                    inches = Double.parseDouble(insertInches.getText().toString());
                }
                double height = (feet*12) + inches;

                String activityChoice = activitySpinner.getSelectedItem().toString();
                String goalsChoice = goalsSpinner.getSelectedItem().toString();

                user = new User(firstName,lastName,height,weight,age,gender,activityChoice,goalsChoice);

                UserDatabase.clearUser();

                boolean isInserted = UserDatabase.addUser(user);

                if (isInserted == true) {
                    Toast.makeText(SettingsActivity.this, "User Updated!", Toast.LENGTH_SHORT).show();
                    Intent intentInput = new Intent(SettingsActivity.this, SettingsActivity.class);
                    startActivity(intentInput);
                }
                else
                {
                    Toast.makeText(SettingsActivity.this, "User Not Updated!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
