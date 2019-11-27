package com.kc.janken;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import io.isfaaghyth.rak.Rak;

public class MainActivity extends AppCompatActivity {
    private static ImageView imgHistory;

    FragmentTransaction fragmentTransaction;
    HistoryFragment historyFragment;
    HomeFragment homeFragment;
    private Context context;
    private  static  MainActivity mainMain;

    public static MainActivity getInstance(){
        if (mainMain == null){
            mainMain = new MainActivity();
        }
        return mainMain;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Rak.initialize(getApplicationContext());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        imgHistory = findViewById(R.id.imgHistory);

        historyFragment = new HistoryFragment();
        homeFragment = new HomeFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, homeFragment);
        fragmentTransaction.commit();

        imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, historyFragment);
                fragmentTransaction.commit();
            }
        });
    }

   static void hideImgHitory(){
        imgHistory.setVisibility(View.INVISIBLE);
    }
   static void showImgHistory(){
        imgHistory.setVisibility(View.VISIBLE);
    }
}
