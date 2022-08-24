package com.example.handsontechnicalexam_manalastasynaneduard.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handsontechnicalexam_manalastasynaneduard.Adapter.StateAdapter;
import com.example.handsontechnicalexam_manalastasynaneduard.DB.State;
import com.example.handsontechnicalexam_manalastasynaneduard.R;
import com.example.handsontechnicalexam_manalastasynaneduard.Utils.NetworkUtils;
import com.example.handsontechnicalexam_manalastasynaneduard.ViewModel.USAStatePopulationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class USAStatePopulation extends AppCompatActivity {

    StateAdapter stateAdapter;
    RecyclerView recyclerViewState;
    private USAStatePopulationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usastatepopulation);
        apiCall();
        initViewModel();

        recyclerViewState = findViewById(R.id.recyclerViewState);
        recyclerViewState.setLayoutManager(new LinearLayoutManager(this));
        stateAdapter = new StateAdapter(this, new StateAdapter.StateAdapterCallBack() {
            @Override
            public void itemClick(State state) {
                detailedView(state);
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(USAStatePopulation.this, SavedStates.class);
//                intent.putExtra("category_id", category.uid);
//                intent.putExtra("category_name", category.categoryName);

                startActivity(intent);
            }
        });



    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(USAStatePopulationViewModel.class);
        viewModel.getListStateObserver().observe(this, new Observer<List<State>>() {
            @Override
            public void onChanged(List<State> categories) {

            }
        });
    }

    private void detailedView(State state){

        AlertDialog dialogBuilder = new AlertDialog.Builder(USAStatePopulation.this).create();
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.insertState(state);
                Toast.makeText(USAStatePopulation.this, "Data Successfully Saved", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void apiCall(){
        Uri uri = Uri.parse("https://datausa.io/api/data?drilldowns=State&measures=Population&year=latest")
                .buildUpon().build();

        try {
            URL url = new URL(uri.toString());
            new DoTask().execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    class DoTask extends AsyncTask<URL,Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String data = null;
            try{
                data = NetworkUtils.makeHttpRequest(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String s){
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void parseJson(String data) throws JSONException {
            JSONObject jsonObject = null;
            try{
                jsonObject = new JSONObject(data);

            }catch(JSONException e){
                e.printStackTrace();
            }

            JSONArray statesArray = jsonObject.getJSONArray("data");


            ArrayList<State> stateList = new ArrayList<State>();
            for(int i=0; i<statesArray.length();i++){
                JSONObject stateobject = statesArray.getJSONObject(i);
                State state  = new State();
                state.setIDState(stateobject.getString("ID State"));
                state.setPopulation(stateobject.getString("Population"));
                state.setStateName(stateobject.getString("State"));
                state.setYear(stateobject.getString("Year"));
                stateList.add(state);
            }

            stateAdapter.setStateList(stateList);
            recyclerViewState.setAdapter(stateAdapter);


        }
    }
}