package com.example.comp90018.ui.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp90018.R;
import com.example.comp90018.ui.activity.UserAct;

import java.util.Map;

public class EmotionResultActivity extends AppCompatActivity {

    private HomeViewModel model;
    private ProgressBar p;
    private TextView tConf;
    private TextView tRes;
    private ImageView img;
    private ImageView icon;
    private String emotion;
    private Button confBtn;
    private Button wrongBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_res);
        initialize();
        // get ViewModel from intent
        StaticLiveData s = StaticLiveData.getInstance();
        // observer
        final Observer<Map<String,String>> resObserver = new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> stringStringMap) {
                // decide if livedata is null to prevent redundant rendering
                if (stringStringMap != null) {
                    if (p != null) {
                        p.setVisibility(View.GONE);
                    }
                    Log.e("observer", "onChanged: " + "onchange启动");
                    // set result
                    emotion = stringStringMap.get("emotion");

                    if (emotion == null) {
                        emotion = "NOT HUMAN";
                        icon.setBackgroundResource(R.drawable.unknown_emoji);
                    } else if (emotion.equals("More than one face detected") && icon != null) {
                        icon.setBackgroundResource(R.drawable.unknown_emoji);
                    } else {
                        // set ui when receive proper feedback
                        String conf = stringStringMap.get("confidence");
                        if (tConf != null) {
                            tConf.setText("Confidence: " + conf + "%");
                        }
                        switch (emotion){
                            case "HAPPY":
                                icon.setBackgroundResource(R.drawable.happy_emoji);
                                break;
                            case "SAD":
                                icon.setBackgroundResource(R.drawable.sad_emoji);
                                break;
                            case "ANGRY":
                                icon.setBackgroundResource(R.drawable.angry_emoji);
                                break;
                            case "CONFUSED":
                                icon.setBackgroundResource(R.drawable.confused_emoji);
                                break;
                            case "DISGUSTED":
                                icon.setBackgroundResource(R.drawable.disgusted_emoji);
                                break;
                            case "SURPRISED":
                                icon.setBackgroundResource(R.drawable.surprise_emoji);
                                break;
                            case "CALM":
                                icon.setBackgroundResource(R.drawable.calm_emoji);
                                break;
                            case "UNKNOWN":
                                icon.setBackgroundResource(R.drawable.unknown_emoji);
                                break;
                            case "FEAR":
                                icon.setBackgroundResource(R.drawable.fear_emoji);
                                break;
                            default:
                                icon.setBackgroundResource(R.drawable.smile_face);
                        }
                    }
                    p.setVisibility(View.GONE);
                    icon.setVisibility(View.VISIBLE);
                    confBtn.setVisibility(View.VISIBLE);
                    wrongBtn.setVisibility(View.VISIBLE);
                    if (tRes != null) {
                        tRes.setText(emotion);
                    }
                    s.setValue(null);
                }
            }
        };

        // start observation
        s.observe(this,resObserver);

        // click listener
        findViewById(R.id.emo_res_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressContBtn();
            }
        });

        findViewById(R.id.emo_res_wrong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressWrongBtn();
            }
        });
    }

    private void pressWrongBtn() {
        startActivity(new Intent(this, UserAct.class).putExtra("emotion","manual_input"));
    }

    private void pressContBtn() {
        startActivity(new Intent(this, UserAct.class).putExtra("emotion",emotion));
    }

    /**
     * set the scanned photo in view
     */
    private void initialize(){
        p = findViewById(R.id.emo_res_progress_bar);
        icon = findViewById(R.id.emo_res_icon);
        tConf = findViewById(R.id.emo_res_conf);
        tRes = findViewById(R.id.emo_res_txt);
        img = findViewById(R.id.emo_res_img);
        confBtn = findViewById(R.id.emo_res_continue);
        wrongBtn = findViewById(R.id.emo_res_wrong);

        confBtn.setVisibility(View.GONE);
        wrongBtn.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        String path = getIntent().getStringExtra("path");
        Log.e("img", "initialize: " + path );
        img.setImageBitmap(BitmapFactory.decodeFile(path));
        tConf.setText("");
        tRes.setText("");

    }

    @Override
    protected void onPause() {
        super.onPause();
        tConf.setText("");
        tRes.setText("");
        icon.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
        confBtn.setVisibility(View.GONE);
        wrongBtn.setVisibility(View.GONE);
        EmotionResultActivity.this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tConf.setText("");
        tRes.setText("");
        icon.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
        confBtn.setVisibility(View.GONE);
        wrongBtn.setVisibility(View.GONE);
        EmotionResultActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tConf.setText("");
        tRes.setText("");
        icon.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
        confBtn.setVisibility(View.GONE);
        wrongBtn.setVisibility(View.GONE);
    }
}
