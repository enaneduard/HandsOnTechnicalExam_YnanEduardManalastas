package com.example.handsontechnicalexam_manalastasynaneduard.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface StatesDao {
    @Query("Select * from State")
    List<State> getAllList();

    @Insert
    void insertState(State...State);

    @Update
    void updateState(State State);

    @Delete
    void deleteState(State State);
}
