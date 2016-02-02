package com.example.perev.playingwithactionbarlecture;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            toggleActionBar();
        }

        return super.onTouchEvent(event);
    }

    private void toggleActionBar(){
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            if(actionBar.isShowing()){
                actionBar.hide();
            }
            else{
                actionBar.show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_option1:
                Toast.makeText(this, "Option 1", Toast.LENGTH_SHORT).show();
                setActionBarLogo();
                return true;
            case R.id.menu_option2:
                Toast.makeText(this, "Option 2", Toast.LENGTH_SHORT).show();
                setActionBarTitleAndSubtitle();
                return true;
            case R.id.menu_option3:
                toggleTitleAndSubtitle();
                Toast.makeText(this, "Option 3", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarLogo(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.awesomeface);
    }

    public void setActionBarTitleAndSubtitle(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Test");
        actionBar.setSubtitle("Testing subtitle");
    }

    public void toggleTitleAndSubtitle(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void onExitClicked(MenuItem item) {
        finish();
    }
}
