package com.example.handsontechnicalexam_manalastasynaneduard.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handsontechnicalexam_manalastasynaneduard.DB.State;
import com.example.handsontechnicalexam_manalastasynaneduard.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private Context context;
    private List<State> statelist;
    private StateAdapterCallBack stateAdapterCallBack;


    public StateAdapter(Context context, StateAdapterCallBack stateAdapterCallBack){
        this.context = context;
        this.stateAdapterCallBack = stateAdapterCallBack;
    }

    public void setStateList(List<State> statelist){
        this.statelist = statelist;

    }



    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_statepopulation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position) {
        List<Integer> solution = new ArrayList<>();
        solution.add(Color.parseColor("#CCD5EE"));
        solution.add(Color.parseColor("#D0E1D1"));
        solution.add(Color.parseColor("#F5EFE8"));
        solution.add(Color.parseColor("#E9EAEC"));
        Collections.shuffle(solution);


        holder.layoutColor.setBackgroundColor(solution.get(1));
        holder.stateID.setText(statelist.get(position).getIDState());
        holder.statename.setText(statelist.get(position).getStateName());
        setAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        if(statelist == null || statelist.size() == 0)
            return 0;
        else
            return statelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView statename;
        TextView stateID;
        LinearLayout layoutColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            statename = itemView.findViewById(R.id.stateName);
            stateID = itemView.findViewById(R.id.stateID);
            layoutColor = itemView.findViewById(R.id.layoutColor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stateAdapterCallBack.itemClick(statelist.get(getAdapterPosition()));
                }
            });

        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);

    }

    public interface  StateAdapterCallBack {
        void itemClick(State state);
    }
}
