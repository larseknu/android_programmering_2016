package com.example.perev.playingwithsqlitetest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.perev.playingwithsqlitetest.database.ShowDataSource;

import java.util.List;
import java.util.Random;

public class ShowListActivitiy extends AppCompatActivity {

    private ShowDataSource dataSource;
    private int showNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_activitiy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataSource = new ShowDataSource(this);
        dataSource.open();

        final ListView listView = (ListView)findViewById(R.id.list);


        List<Show> shows = dataSource.getAllShows();

        ShowListAdapter adapter = new ShowListAdapter(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.addAll(shows);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Show show = (Show) listView.getAdapter().getItem(position);
                //Toast.makeText(this, show.getId() + " " + show.getTitle(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), EpisodesActivity.class);
                intent.putExtra(EpisodesActivity.SHOW_ID, show.getId());
                intent.putExtra(EpisodesActivity.SHOW_TITLE, show.getTitle());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteShowDialog(position);
                return true;
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_list_activitiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.add:
                itemAddOnClick();
                break;
            case R.id.delete:
                itemDeleteOnClick();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;

        }
        return true;
    }

    private class CreateShow extends AsyncTask<String, Void, Show> {

        @Override
        protected Show doInBackground(String... shows) {
            Show show = dataSource.createShow(shows[showNumber++%shows.length], new Random().nextInt(15)+2000, null);
            dataSource.createEpisode(show.getId(), "Pilot", 1, 1, "Brilliant physicist roommates Leonard and Sheldon meet their new neighbor Penny, who begins showing them that as much as they know about science, they know little about actual living.");
            dataSource.createEpisode(show.getId(), "The Big Bran Hypothesis", 2, 1, "Brilliant physicist roommates Leonard and Sheldon meet their new neighbor Penny, who begins showing them that as much as they know about science, they know little about actual living.");

            return show;
        }

        @Override
        protected void onPostExecute(Show show) {

            ListView listView = (ListView)findViewById(R.id.list);
            ShowListAdapter adapter = (ShowListAdapter) listView.getAdapter();

            adapter.add(show);
        }
    }

    public void itemAddOnClick() {
        CreateShow createShow = new CreateShow();
        createShow.execute(getResources().getStringArray(R.array.shows));
    }

    public void itemDeleteOnClick() {

        ListView listView = (ListView)findViewById(R.id.list);
        ShowListAdapter adapter = (ShowListAdapter) listView.getAdapter();

        while (adapter.getCount() > 0) {
            Show show = adapter.getItem(0);
            dataSource.deleteShow(show);
            adapter.remove(show);
        }
    }

    private void deleteShowDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete entry?")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ListView listView = (ListView)findViewById(R.id.list);
                        ShowListAdapter adapter = (ShowListAdapter) listView.getAdapter();
                        Show show = adapter.getItem(position);
                        dataSource.deleteShow(show);
                        adapter.remove(show);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
