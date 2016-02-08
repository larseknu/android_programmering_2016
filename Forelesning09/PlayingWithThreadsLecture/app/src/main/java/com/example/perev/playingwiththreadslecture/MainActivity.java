package com.example.perev.playingwiththreadslecture;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    PlaceholderFragment mFragment;
    Thread mWorkerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragment = new PlaceholderFragment();
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }

        StrictMode.enableDefaults();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void updateUI(String message){
        mFragment.setDisplayTextWithPost(message);
    }

    public void doWork(){
        mWorkerThread = new Thread(new Runnable() {
            public void run() {
                Log.d("doWork", "In Run");
                //mFragment.setDisplayTextWithPost("Test");

                Worker worker = new Worker(MainActivity.this);
                updateUI("Starting");

                Location location = worker.getLocation();
                updateUI("Retrieved Location");

                String address = "B R A Veien 4, 1783 Halden";

                worker.save(location, address, "FancyFileName.out");
                updateUI("Done saving");
            }
        });
        mWorkerThread.start();
    }

    public void doAsyncWork(){
        DownloadWebPageTask asyncTask = new DownloadWebPageTask();
        asyncTask.execute("http://www.it-stud.hiof.no/android/data/randomData.php");
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        int len = 500;

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            conn.connect();
            is = conn.getInputStream();

            String contentAsString = readIt(is, len);
            Log.d("Web", contentAsString);
            return contentAsString;

        }
        finally {
            if(is !=null){
                is.close();
            }
        }
    }

    public class DownloadWebPageTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls){
            String response = "";
            try{
                return downloadUrl(urls[0]);
            }catch (IOException e){
                return "Unable to retrieve web page";
            }
        }

        @Override
        protected void onPostExecute(String result){
            mFragment.setDisplayTextWithPost(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.do_work:
                doWork();
                Log.d("doWork", "Working");
                break;
            case R.id.do_async_work:
                doAsyncWork();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;

    }

    public static class PlaceholderFragment extends Fragment {
        private TextView mTextView;
        private String mTempText;

        public PlaceholderFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            mTextView = (TextView)rootView.findViewById(R.id.output_text_view);

            return rootView;
        }

        public void setDisplayText(String outputText){
            mTextView.setText(outputText);
        }

        public void setDisplayTextWithPost(String outputText){
            mTempText = outputText;
            mTextView.post(new Runnable() {
                public void run() {
                    mTextView.setText(mTempText);
                }
            });

        }
    }
}
