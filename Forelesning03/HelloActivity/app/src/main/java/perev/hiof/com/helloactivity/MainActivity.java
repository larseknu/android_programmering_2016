package perev.hiof.com.helloactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String tag = "Livssyklus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(tag, "Inne i onCreate()");
    }

    public void startSecondActivity(View view) {
        //startActivity(new Intent("perev.hiof.com.second_activity"));
        startActivity(new Intent(this, SecondActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "Inne i onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, "Inne i onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "Inne i onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "Inne i onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "Inne i onDestroy()");
    }
}
