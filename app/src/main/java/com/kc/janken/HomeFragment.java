package com.kc.janken;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.janken.model.HistoryModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import io.isfaaghyth.rak.Rak;

import static com.kc.janken.data.Data.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ImageView imgHuman, imgGPU;
    TextView scoreHuman, scoreGPU;
    Button btnStart, btnStop;
    ImageView refreshImage;
    View view;



    int tempScoreHuman = 0;
    int tempScoreGPU = 0;

    int indexHuman;
    int indexGpu;

    String nameHuman = "";
    String nameGPU = "";
    boolean stop = false;

    private int[] imgs = new int[]{R.drawable.scisor, R.drawable.paper, R.drawable.rock};

    private ArrayList<HistoryModel> modelArrayList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Rak.initialize(getContext());

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        int a = getRandomGpu(imgs);
        Log.d("momo : ", a+"");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tempScoreHuman = 0;
        tempScoreGPU = 0;

        MainActivity.showImgHistory();
        imgHuman = view.findViewById(R.id.imgHuman);
        imgGPU = view.findViewById(R.id.imgGPU);

        scoreHuman = view.findViewById(R.id.tempScoreHuman);
        scoreGPU = view.findViewById(R.id.tempScoreGPU);

        btnStart = view.findViewById(R.id.btnStart);
        btnStop = view.findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RunAnimationHuman();
                RunAnimationGpu();
                if (tempScoreHuman + tempScoreGPU == 3){
                    saveToHistory(tempScoreHuman, tempScoreGPU);
                    resetScore(tempScoreHuman, tempScoreGPU);

                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    stopLoadingHuman();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopLoadingGpu();
                            compareResult(nameHuman, nameGPU);
                        }
                    },200);

            }
        });

    }

    public static int getRandomHuman(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static int getRandomGpu(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public void RunAnimationHuman()
    {
        //The onClick method has to be present and must take the above parameter.
        startLoadingHuman();

    }

    public void RunAnimationGpu()
    {
        //The onClick method has to be present and must take the above parameter.
        startLoadingGpu();
    }

    private void startLoadingHuman() {

        refreshImage = view.findViewById(R.id.imgHuman);
        refreshImage.setImageDrawable(getResources().getDrawable(R.drawable.loading));
        Animation rotateLoading = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        refreshImage.clearAnimation();
        refreshImage.setAnimation(rotateLoading);
    }

    private void stopLoadingHuman(){
        indexHuman = getRandomHuman(imgs);
        Log.d("haha", indexHuman +" ");
        refreshImage = view.findViewById(R.id.imgHuman);
        if (refreshImage.getAnimation() != null)
        {
            refreshImage.clearAnimation();
            refreshImage.setImageDrawable(getResources().getDrawable(indexHuman));
        }
        this.nameHuman = getImage(indexHuman);
        Log.d("imagehuman", this.nameHuman);

    }

    private void startLoadingGpu() {
        refreshImage = view.findViewById(R.id.imgGPU);
        refreshImage.setImageDrawable(getResources().getDrawable(R.drawable.loading));
        Animation rotateLoading = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        refreshImage.clearAnimation();
        refreshImage.setAnimation(rotateLoading);
    }

    private void stopLoadingGpu(){
        indexGpu = getRandomGpu(imgs);
        refreshImage = view.findViewById(R.id.imgGPU);
        if (refreshImage.getAnimation() != null)
        {
            refreshImage.clearAnimation();
            refreshImage.setImageDrawable(getResources().getDrawable(indexGpu));
        }
        this.nameGPU = getImage(indexGpu);
        Log.d("imagegpu", this.nameGPU);
    }

    String getImage(int index){
        switch (index){
            case R.drawable.scisor:
                return "gunting";
            case R.drawable.rock:
                return "batu";
            case R.drawable.paper:
                return "kertas";
        }
        return "null";
    }


        void compareResult(String imgHuman, String imgGPU){
            if (imgHuman.equalsIgnoreCase("gunting")
            && imgGPU.equalsIgnoreCase("gunting")){
                //human 0 gpu 0

                this.tempScoreHuman = this.tempScoreHuman + 0;
                this.tempScoreGPU = this.tempScoreGPU + 0;
                setScore();
                Toast.makeText(getContext(), "Draw (Scisor)", Toast.LENGTH_SHORT).show();
        }else if (imgHuman.equalsIgnoreCase("gunting")
                    && imgGPU.equalsIgnoreCase("kertas")){
                //human++
                /*hF.tempScoreHuman = hF.tempScoreHuman + 1;
                hF.tempScoreGPU = hF.tempScoreGPU + 0;
*/
                this.tempScoreHuman = this.tempScoreHuman + 1;
                this.tempScoreGPU = this.tempScoreGPU + 0;
                setScore();
                Toast.makeText(getContext(), "Human Win (Scisor)", Toast.LENGTH_SHORT).show();
            }else if (imgHuman.equalsIgnoreCase("gunting")
                    && imgGPU.equalsIgnoreCase("batu")){
                // gpu ++
                /*hF.tempScoreHuman = hF.tempScoreHuman + 0;
                hF.tempScoreGPU = hF.tempScoreGPU + 1;*/

                this.tempScoreHuman = this.tempScoreHuman + 0;
                this.tempScoreGPU = tempScoreGPU +1;
                setScore();
                Toast.makeText(getContext(), "GPU Win (Rock)", Toast.LENGTH_SHORT).show();
            }else if (imgHuman.equalsIgnoreCase("kertas")
                    && imgGPU.equalsIgnoreCase("kertas")){
                // 0 0
                /*hF.tempScoreHuman = hF.tempScoreHuman + 0;
                hF.tempScoreGPU = hF.tempScoreGPU + 0;*/

                this.tempScoreHuman = this.tempScoreHuman + 0;
                this.tempScoreGPU = this.tempScoreGPU +0;
                setScore();
                Toast.makeText(getContext(), "Draw (paper)", Toast.LENGTH_SHORT).show();
            }else if (imgHuman.equalsIgnoreCase("kertas")
                    && imgGPU.equalsIgnoreCase("gunting")){
                //gpu ++
                /*hF.tempScoreHuman = hF.tempScoreHuman + 0;
                hF.tempScoreGPU = hF.tempScoreGPU + 1;*/

                this.tempScoreHuman = this.tempScoreHuman + 0;
                this.tempScoreGPU = this.tempScoreGPU +1;
                setScore();
                Toast.makeText(getContext(), "GPU Win (Scisor)", Toast.LENGTH_SHORT).show();
            }else if (imgHuman.equalsIgnoreCase("kertas")
                    && imgGPU.equalsIgnoreCase("batu")){
                // human++
                /*hF.tempScoreHuman = hF.tempScoreHuman + 1;
                hF.tempScoreGPU = hF.tempScoreGPU + 0;*/

                this.tempScoreHuman = this.tempScoreHuman + 1;
                this.tempScoreGPU = this.tempScoreGPU +0;
                setScore();
                Toast.makeText(getContext(), "Human Win (Paper)", Toast.LENGTH_SHORT).show();
            }else if (imgHuman.equalsIgnoreCase("batu")
                    && imgGPU.equalsIgnoreCase("batu")){
                // 0 0
                /*hF.tempScoreHuman = hF.tempScoreHuman + 0;
                hF.tempScoreGPU = hF.tempScoreGPU + 0;*/

                this.tempScoreHuman = this.tempScoreHuman + 0;
                this.tempScoreGPU = this.tempScoreGPU +0;
                setScore();
                Toast.makeText(getContext(), "Draw (Rock)", Toast.LENGTH_SHORT).show();
            }else if (imgHuman.equalsIgnoreCase("batu")
                    && imgGPU.equalsIgnoreCase("gunting")){
                //human ++
                /*hF.tempScoreHuman = hF.tempScoreHuman + 1;
                hF.tempScoreGPU = hF.tempScoreGPU + 0;*/

                this.tempScoreHuman = this.tempScoreHuman + 1;
                this.tempScoreGPU = this.tempScoreGPU + 0;
                setScore();
                Toast.makeText(getContext(), "Human Win (Rock)", Toast.LENGTH_SHORT).show();
            }else if (imgHuman.equalsIgnoreCase("batu")
                    && imgGPU.equalsIgnoreCase("kertas")){
                //gpu ++
                /*hF.tempScoreHuman = hF.tempScoreHuman + 0;
                hF.tempScoreGPU = hF.tempScoreGPU + 1;*/

                this.tempScoreHuman = this.tempScoreHuman + 0;
                this.tempScoreGPU = this.tempScoreGPU +1;
                setScore();
                Toast.makeText(getContext(), "GPU Win (Paper)", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
            }

    }

    void setScore(){
        scoreHuman.setText(Integer.toString(this.tempScoreHuman));
        scoreGPU.setText(Integer.toString(this.tempScoreGPU));
    }

    void resetScore(int scoreHuman, int scoreGPu){
        if (scoreHuman + scoreGPu == 3){
            this.tempScoreHuman = 0;
            this.tempScoreGPU = 0;
        }
    }

    void saveToHistory(int humanScore, int gpuScore){
        HistoryModel model = new HistoryModel(humanScore, gpuScore);

        if (Rak.grab("arrayHistory") != null){
            modelArrayList = Rak.grab("arrayHistory");
            modelArrayList.add(model);
        }
        Rak.entry("arrayHistory", modelArrayList);

        cekArray();
    }

    void cekArray(){
        ArrayList<HistoryModel> a = Rak.grab("arrayHistory");
        Log.d("aa", a.get(0).getHumanScore() + "");
    }

}
