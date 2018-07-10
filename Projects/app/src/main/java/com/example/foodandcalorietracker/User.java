package com.example.foodandcalorietracker;

public class User {
    private int mId;
    private String uFirstName;
    private String uLastName;
    private double uHeight;
    private double uWeight;
    private double uBMR;
    private double uDailyCalories;
    private int uAge;
    private String uGender;
    private String uActivity;
    private String uGoals;

    public User() {
    }

    public User(int id, String firstname, String lastname, double height, double weight,int age, String gender,String activity,String goals){
        this.mId = id;
        this.uFirstName = firstname;
        this.uLastName = lastname;
        this.uHeight = height;
        this.uWeight = weight;
        this.uAge = age;
        this.uGender = gender;
        this.uActivity = activity;
        this.uGoals = goals;
        calculateBMR();
        solveDailyCalorieAmount();
        solveDesiredCaloricIntake();

    }

    public User(String firstname, String lastname, double height, double weight,int age, String gender,String activity,String goals){
        this.uFirstName = firstname;
        this.uLastName = lastname;
        this.uHeight = height;
        this.uWeight = weight;
        this.uAge = age;
        this.uGender = gender;
        this.uActivity = activity;
        this.uGoals = goals;
        calculateBMR();
        solveDailyCalorieAmount();
        solveDesiredCaloricIntake();
    }

    //setters
    public void setId(int id)
    {
        this.mId = id;
    }

    public void setFirstName(String firstname)
    {
        this.uFirstName = firstname;
    }

    public void setLastName(String lastName)
    {
        this.uLastName = lastName;
    }

    public void setHeight(double height)
    {
        this.uHeight = height;
    }

    public void setWeight(double weight)
    {
        this.uWeight = weight;
    }

    public void setAge(int age)
    {
        this.uAge = age;
    }

    public void setGender(String gender)
    {
        this.uGender = gender;
    }

    public void setActivity(String activity)
    {
        this.uActivity = activity;
    }

    public void setGoals(String goals){this.uGoals = goals;}


    //getters
    public int getId()
    {
        return this.mId;
    }

    public String getFirstName()
    {
        return this.uFirstName;
    }

    public String getLastName()
    {
        return this.uLastName;
    }

    public double getHeight()
    {
        return this.uHeight;
    }

    public double getWeight(){return this.uWeight;}

    public int getAge()
    {
        return this.uAge;
    }

    public String getGender()
    {
        return this.uGender;
    }

    public String getActivity()
    {
        return this.uActivity;
    }

    public double getBMR()
    {
        return this.uBMR;
    }

    public double getDailyCalories()
    {
        return this.uDailyCalories;
    }

    public String getGoals(){return this.uGoals;}

    public void calculateBMR()
    {
        double weight = uWeight * .453592;
        double height = uHeight * 2.54;
        if(uGender.equals("M"))
        {
            uBMR = 88.362 + (13.397 * weight) + (4.799 * height ) - (5.677 * uAge);
        }
        else if(uGender.equals("F"))
        {
            uBMR = 447.593 + (9.247 * weight) + (3.098 * height ) - (4.330 * uAge);
        }
    }

    public void solveDailyCalorieAmount()
    {
        if(uActivity.equals("Little to no exercise")) {
            uDailyCalories = uBMR * 1.2;
        }
        else if (uActivity.equals("Light exercise(1-3 days per week)"))
        {
            uDailyCalories = uBMR * 1.375;
        }
        else if (uActivity.equals("Moderate exersise(3-5 days per week)"))
        {
            uDailyCalories = uBMR * 1.55;
        }
        else if (uActivity.equals("Heavy exercise(6-7 days per week)"))
        {
            uDailyCalories = uBMR * 1.725;
        }
        else if (uActivity.equals("Very Heavy exercise(twice per day, extra heavy workouts)"))
        {
            uDailyCalories = uBMR * 1.9;
        }
    }

    public void solveDesiredCaloricIntake()
    {
        if(uGoals.equals("Maintain current weight"))
        {
            uDailyCalories = uDailyCalories * 1;
        }
        else if(uGoals.equals("Lose .5kg per week"))
        {
            uDailyCalories = uDailyCalories * .8020585907;
        }
        else if(uGoals.equals("Lose 1kg per week"))
        {
            uDailyCalories = uDailyCalories * .6017418844;
        }
        else if(uGoals.equals("Gain .5kg per week"))
        {
            uDailyCalories = uDailyCalories * 1.1979414093;
        }
        else if(uGoals.equals("Gain 1kg per week"))
        {
            uDailyCalories = uDailyCalories * 1.3958828187;
        }
    }
}

