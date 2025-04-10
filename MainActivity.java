package com.example.project;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

import kotlin.collections.AbstractMutableList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textviewGameStatus;
    private final int[] imageviewcardids = {R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.button10,R.id.button11,R.id.button12};
    private final int[] drawablecardids = {R.drawable.anime1,R.drawable.anime2,R.drawable.anime3,R.drawable.anime4,R.drawable.anime5,R.drawable.anime6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textviewGameStatus = findViewById(R.id.textView);

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupnewgame();
            }
        });
        setupnewgame();
        }

    private void setupnewgame(){
        ArrayList<Integer> shuffledrawableids = new ArrayList<>();

        for(int drawableid : drawablecardids) {
            shuffledrawableids.add(drawableid);
            shuffledrawableids.add(drawableid);
        }
        //Collections.shuffle(shuffledrawableids);

        for(int i = 0; i < shuffledrawableids.size();i++){
            int imageviewid = imageviewcardids[i];
            int drawableid = shuffledrawableids.get(i);
            cardinfo cardinfo = new cardinfo(imageviewid,drawableid);
            ImageView imageView = findViewById(imageviewid);
            imageView.setImageResource(R.drawable.img);
            imageView.setTag(cardinfo);
            imageView.setOnClickListener(this);

        }
        cardinfo1 = null;
        matchcount = 0;
        iswaiting = false;
        textviewGameStatus.setText("Match Count:"+ matchcount);

    }

    cardinfo cardinfo1 = null;
    int matchcount = 0;
    boolean iswaiting = false;

    private void covercard(cardinfo cardinfo){
        ImageView imageView = findViewById(cardinfo.getImageviewid());
        imageView.setImageResource(R.drawable.img);
        cardinfo.setIsflipped(false);
        cardinfo.setIsmatched(false);

    }


    @Override
    public void onClick(View v) {
        if(!iswaiting){
            ImageView imageView = (ImageView) v;
            cardinfo cardinfo = (cardinfo) imageView.getTag();
            Log.d("demo","onClick" + cardinfo);

            if(!cardinfo.isIsflipped() && !cardinfo.isIsmatched()){
                imageView.setImageResource(cardinfo.getDrawableid());
                cardinfo.setIsflipped(true);

                if (cardinfo1==null){
                    cardinfo1 = cardinfo;
                }else {
                    if(cardinfo.getDrawableid()==cardinfo1.getDrawableid()){
                        matchcount++;
                        cardinfo1.setIsmatched(true);
                        cardinfo.setIsmatched(true);

                        textviewGameStatus.setText("Match Count:"+ matchcount);

                        if(matchcount == 6){
                            textviewGameStatus.setText("Game completed!");

                        }
                        cardinfo1 = null;
                    }else{
                        iswaiting = true;

                        imageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iswaiting = false;
                                covercard(cardinfo);
                                covercard(cardinfo1);
                                cardinfo1 = null;
                            }
                        },1000);



                    }

                }

            }

        }
    }
}

