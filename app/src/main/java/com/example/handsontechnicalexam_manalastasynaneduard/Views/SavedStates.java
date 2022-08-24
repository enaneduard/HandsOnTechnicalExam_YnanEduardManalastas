package com.example.handsontechnicalexam_manalastasynaneduard.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handsontechnicalexam_manalastasynaneduard.Adapter.StateAdapter;
import com.example.handsontechnicalexam_manalastasynaneduard.DB.State;
import com.example.handsontechnicalexam_manalastasynaneduard.R;
import com.example.handsontechnicalexam_manalastasynaneduard.ViewModel.USAStatePopulationViewModel;

import java.util.List;

public class SavedStates extends AppCompatActivity {

    StateAdapter stateAdapter;
    RecyclerView recyclerViewState;
    private USAStatePopulationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_states);
        initViewModel();
        viewModel.getAllCategoryList();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerViewState = findViewById(R.id.recyclerViewState);
        recyclerViewState.setLayoutManager(new LinearLayoutManager(this));
        stateAdapter = new StateAdapter(this, new StateAdapter.StateAdapterCallBack() {
            @Override
            public void itemClick(State state) {
                detailedView(state);
            }
        });

    }

    private void detailedView(State state){

        AlertDialog dialogBuilder = new AlertDialog.Builder(SavedStates.this).create();
        View popUpStateDetails = getLayoutInflater().inflate(R.layout.cardview_statedetails, null);
        dialogBuilder.setView(popUpStateDetails);
        dialogBuilder.show();

        TextView stateName = popUpStateDetails.findViewById(R.id.stateName);
        TextView stateID = popUpStateDetails.findViewById(R.id.stateID);
        TextView stateYear = popUpStateDetails.findViewById(R.id.stateyear);
        TextView statePopulation = popUpStateDetails.findViewById(R.id.statepopulation);
        ImageButton closeButton = popUpStateDetails.findViewById(R.id.imgbtnClose);
        AppCompatButton saveButton = popUpStateDetails.findViewById(R.id.insertdelete);
        stateName.setText("State: " + state.getStateName());
        stateID.setText("ID: " +state.getIDState());
        stateYear.setText("Year: " +state.getYear());
        statePopulation.setText("Population: " +state.getPopulation());

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });

        saveButton.setText("Delete");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteState(state);
                Toast.makeText(SavedStates.this, "Data Successfully Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(USAStatePopulationViewModel.class);
        viewModel.getListStateObserver().observe(this, new Observer<List<State>>() {
            @Override
            public void onChanged(List<State> categories) {
                if(categories!=null){
                    stateAdapter.setStateList(categories);
                    recyclerViewState.setAdapter(stateAdapter);
                }else{
                    Toast.makeText(SavedStates.this, "none", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}