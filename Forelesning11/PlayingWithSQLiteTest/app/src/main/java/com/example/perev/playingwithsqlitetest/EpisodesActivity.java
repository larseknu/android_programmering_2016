package com.example.perev.playingwithsqlitetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.perev.playingwithsqlitetest.database.ShowDataSource;

import java.util.List;

public class EpisodesActivity extends AppCompatActivity {

    public static String SHOW_ID = "show id";
    public static String SHOW_TITLE = "show title";

    private ShowDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataSource = new ShowDataSource(this);
        dataSource.open();

        Intent intent = getIntent();
        int showId = intent.getIntExtra(SHOW_ID, -1);

        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Season 1");

        setTitle(intent.getStringExtra(SHOW_TITLE));

        List<Episode> episodes = dataSource.getAllEpisodes(showId);

        ListView listView = (ListView)findViewById(R.id.list_episodes);

        if (showId != -1) {
            EpisodeListAdapter adapter = new EpisodeListAdapter (this, android.R.layout.simple_list_item_1);
            listView.setAdapter(adapter);

            if(!episodes.isEmpty())
                adapter.addAll(episodes);
        }
        else
            header.setText("No episodes found");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

}
