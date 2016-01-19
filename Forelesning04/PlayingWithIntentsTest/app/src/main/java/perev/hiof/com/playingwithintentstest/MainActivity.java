package perev.hiof.com.playingwithintentstest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startOtherActivity(View view)
    {
        // Call OtherActivity with an explicit intent
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }

    public void implicitlyStartOtherActivity(View view)
    {
        // Call OtherActivity with an implicit intent
        Intent intent = new Intent("perev.hiof.com.action.OTHER_ACTIVITY");
        startActivity(intent);
    }

    public void getTime(View view)
    {
        // Get date with an implicit intent
        Intent intent = new Intent("perev.hiof.com.action.SHOW_TIME");
        // Sets a flag that the opened activity should be deleted from the history stack when the user navigates away from it
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void getDate(View view)
    {
        // Get time with an implicit intent
        Intent intent = new Intent("perev.hiof.com.action.SHOW_DATE");
        startActivity(intent);
    }

    public void openWebsite(View view)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = Uri.parse("http://www.hiof.no");
        intent.setData(uri);
        startActivity(intent);
    }

    public void runServiceJob(View view){
        counter++;
        MyIntentService.startActionProduce(this, "Android Clones", counter);
    }

}
