package com.example.handsontechnicalexam_manalastasynaneduard.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.handsontechnicalexam_manalastasynaneduard.DB.AppDB;
import com.example.handsontechnicalexam_manalastasynaneduard.DB.State;

import java.util.List;

public class USAStatePopulationViewModel extends AndroidViewModel {
    private MutableLiveData<List<State>> listofState;
    private AppDB appdb;


    public USAStatePopulationViewModel(@NonNull Application application) {
        super(application);

        listofState = new MutableLiveData<>();

        appdb = appdb.getDBinstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<State>> getListStateObserver() {
        return listofState;
    }

    public void getAllCategoryList(){
        List<State> categoryList = appdb.statesDao().getAllList();
        if(categoryList.size()>0){
            listofState.postValue(categoryList);
        }else{
            listofState.postValue(null);
        }
    }

    public void insertState(State state) {

        appdb.statesDao().insertState(state);
        getAllCategoryList();
    }

    public void updateState(State state) {
        appdb.statesDao().updateState(state);
        getAllCategoryList();
    }

    public void deleteState(State state) {
        appdb.statesDao().deleteState(state);
        getAllCategoryList();
    }
}
