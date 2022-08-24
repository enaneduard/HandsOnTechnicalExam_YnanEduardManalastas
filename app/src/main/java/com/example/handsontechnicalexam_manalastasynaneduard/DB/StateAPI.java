package com.example.handsontechnicalexam_manalastasynaneduard.DB;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.handsontechnicalexam_manalastasynaneduard.Utils.NetworkUtils;
import com.example.handsontechnicalexam_manalastasynaneduard.Views.USAStatePopulation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StateAPI {

    public ArrayList<State> state = new ArrayList<>();

    public StateAPI(){
        Uri uri = Uri.parse("https://datausa.io/api/data?drilldowns=State&measures=Population&year=latest")
                .buildUpon().build();

        try {
            URL url = new URL(uri.toString());
            new StateAPI.DoTask(new StateInterface() {
                @Override
                public void onResponse(ArrayList<State> result) {
                    state = result;
                }
            }).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    class DoTask extends AsyncTask<URL,Void, String> {

        StateInterface caller;

        DoTask(StateInterface caller) {
            this.caller = caller;
        }

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

            caller.onResponse(stateList);






        }
    }
}
