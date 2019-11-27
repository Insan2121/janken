package com.kc.janken;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.kc.janken.adapter.HistoryAdapter;
import com.kc.janken.model.HistoryModel;

import java.util.ArrayList;

import io.isfaaghyth.rak.Rak;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private RecyclerView historyRC;
    private ArrayList<HistoryModel> modelArrayList = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView img;
    private Fragment fragment;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Rak.initialize(getContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.hideImgHitory();

        historyRC = view.findViewById(R.id.recycler);
        img = view.findViewById(R.id.imgBack);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonBack();
            }
        });

        historyRC.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        historyRC.setHasFixedSize(true);
        historyRC.setLayoutManager(new LinearLayoutManager(getContext()));
        setRecyclcer();
    }

    void setRecyclcer(){
        ArrayList<HistoryModel> tempArray = Rak.grab("arrayHistory");
        Log.d("humanScore : ", tempArray.get(0).getHumanScore() +"");
        Log.d("gpuScore : ", tempArray.get(0).getGPUScore() +"");
        if (tempArray != null){
            for (int i = 0; i < tempArray.size(); i++) {
                HistoryModel historyModel = tempArray.get(i);
                int humanScore = historyModel.getHumanScore();
                Log.d("human===", humanScore+"");
                int gpuScore = historyModel.getGPUScore();
                Log.d("score===!", gpuScore+ " ");
                modelArrayList.add(new HistoryModel(humanScore, gpuScore));
            }
            historyAdapter = new HistoryAdapter(getContext(), modelArrayList, this);
            historyRC.setAdapter(historyAdapter);
            historyRC.setHasFixedSize(true);
            historyAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(getContext(), "Just Enjoyed", Toast.LENGTH_SHORT).show();
        }
    }

    void buttonBack(){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new HomeFragment()).commit();
    }
}
