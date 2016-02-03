package com.example.perev.playingwithnotificationslecture;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PictureActivity extends AppCompatActivity {

    public static final String TITLE_EXTRA = "title extra";
    public static final String IMAGE_TEXT_EXTRA = "image text extra";
    public static final String IMAGE_RESOURCE_ID_EXTRA = "image resource id extra";

    public static final int IMAGE_RESOURCE_ID_NOT_SET = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent createIntent = getIntent();
        String title = createIntent.getStringExtra(TITLE_EXTRA);
        String imageText = createIntent.getStringExtra(IMAGE_TEXT_EXTRA);
        int imageResourceId = createIntent.getIntExtra(IMAGE_RESOURCE_ID_EXTRA, IMAGE_RESOURCE_ID_NOT_SET);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        if(title != null){
            setTitle(title);
        }
        if(imageText != null){
            ((TextView)findViewById(R.id.imageTextView)).setText(imageText);
            imageView.setContentDescription(imageText);
        }
        if(imageResourceId != IMAGE_RESOURCE_ID_NOT_SET){
            imageView.setImageResource(imageResourceId);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
