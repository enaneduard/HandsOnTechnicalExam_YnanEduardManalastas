package com.example.handsontechnicalexam_manalastasynaneduard.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {State.class,}, version= 1)
public abstract class AppDB extends RoomDatabase {
    public abstract StatesDao statesDao();

    public static AppDB INSTANCE;
    public static AppDB getDBinstance(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDB.class,"UsStateDB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
