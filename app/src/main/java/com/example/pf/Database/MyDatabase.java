package com.example.pf.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 3, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "item_database";
    private static final String LOG_TAG =  MyDatabase.class.getSimpleName();
    private static MyDatabase instance;

    public static MyDatabase getInstance(Context context){
        if(instance == null){
            Log.d(LOG_TAG, "Creating instance of database");
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration()./*allowMainThreadQueries().*/build();  //Fallbacktdm is because I don´t want to handle migration and don´t need to preserve my database data
        }
        Log.d(LOG_TAG, "Getting database instance");
        return instance;
    }

    public abstract DaoInterface dao();
}
