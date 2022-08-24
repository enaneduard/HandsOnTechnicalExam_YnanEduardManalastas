package com.example.handsontechnicalexam_manalastasynaneduard.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class State {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "IDState")
    private String IDState;

    @ColumnInfo(name = "StateName")
    private String StateName;

    @ColumnInfo(name = "categoryName")
    private String Year;

    @ColumnInfo(name = "Population")
    private String Population;

    @ColumnInfo(name = "Pinned")
    public boolean pinned;


    public State(){

    }

    public State(String IDState, String stateName, String year, String population) {
        this.IDState = IDState;
        StateName = stateName;
        Year = year;
        Population = population;
    }

    public String getIDState() {
        return IDState;
    }

    public void setIDState(String IDState) {
        this.IDState = IDState;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getPopulation() {
        return Population;
    }

    public void setPopulation(String population) {
        Population = population;
    }


}
