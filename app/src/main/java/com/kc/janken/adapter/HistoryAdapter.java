package com.kc.janken.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.janken.R;
import com.kc.janken.model.HistoryModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HistoryModel> historyModelArrayList;
    private View view;
    private Fragment fragment;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModelArrayList) {
        this.context = context;
        this.historyModelArrayList = historyModelArrayList;
    }

    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModelArrayList, Fragment fragment) {
        this.context = context;
        this.historyModelArrayList = historyModelArrayList;
        this.fragment = fragment;
        Log.d("lengg", this.historyModelArrayList.size()+ "");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_history, viewGroup, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            HistoryModel historyModel = historyModelArrayList.get(i);

            viewHolder.no.setText(Integer.toString(i+1));
            viewHolder.humanScore.setText(Integer.toString(historyModel.getHumanScore()));
            viewHolder.gpuScore.setText(Integer.toString(historyModel.getGPUScore()));
    }

    @Override
    public int getItemCount() {
        Log.d("==============", "getItemCount: " + historyModelArrayList.size());
        return historyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView no, humanScore, gpuScore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            humanScore = itemView.findViewById(R.id.hHumanScore);
            gpuScore = itemView.findViewById(R.id.hGPUScore);
        }
    }
}
