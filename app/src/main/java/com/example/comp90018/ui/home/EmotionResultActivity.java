package com.example.comp90018.ui.home;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp90018.R;

import java.util.Map;

public class EmotionResultActivity extends AppCompatActivity {

    private HomeViewModel model;
    private ProgressBar p;
    private TextView tConf;
    private TextView tRes;
    private ImageView img;
    private ImageView icon;

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
                    String emotion = stringStringMap.get("emotion");

                    if (emotion == null) {
                        emotion = "NOT HUMAN";
                        p.setVisibility(View.GONE);
                        icon.setBackgroundResource(R.drawable.smile_face);
                        icon.setVisibility(View.VISIBLE);
                    } else if (emotion.equals("More than one face detected") && icon != null) {
                        //TODO: set icon of unknown
                    } else {
                        String conf = stringStringMap.get("confidence");
                        if (tConf != null) {
                            tConf.setText("Confidence: " + conf + "%");
                        }
                        icon.setBackgroundResource(R.drawable.smile_face);
                        p.setVisibility(View.GONE);
                        icon.setVisibility(View.VISIBLE);
                    }
                    if (tRes != null) {
                        tRes.setText(emotion);
                    }
                    s.setValue(null);
                }
            }
        };

        // start observation
        s.observe(this,resObserver);
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


        p.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        String path = getIntent().getStringExtra("path");
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        tConf.setText("");
        tRes.setText("");
        icon.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tConf.setText("");
        tRes.setText("");
        icon.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
    }
}
