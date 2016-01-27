package com.example.perev.playingwithlayoutslecture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

public class HierarchyViewerActivity extends AppCompatActivity {

    private View smileysViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hierarchy_viewer);
    }

    public void toggleSmileys(View view){
        if(smileysViewStub == null){
            smileysViewStub = ((ViewStub)findViewById(R.id.happySmileysViewStub)).inflate();
        }

        boolean visibleViewStub = (smileysViewStub.getVisibility() == View.VISIBLE);
        smileysViewStub.setVisibility(visibleViewStub ? View.GONE : View.VISIBLE);
    }
}
