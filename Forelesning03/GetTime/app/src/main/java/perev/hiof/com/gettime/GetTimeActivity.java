package perev.hiof.com.gettime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_time);

        TextView textView = (TextView) findViewById(R.id.textViewTime);

        Intent intent = getIntent();

        //uncomment to test
        //intent.setAction("perev.hiof.com.action.SHOW_TIME");
        //intent.setAction("perev.hiof.com.action.SHOW_DATE");


        String action = intent.getAction();

        SimpleDateFormat dateFormat = null;

        if(action.equals("perev.hiof.com.action.SHOW_TIME")){
            dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        }
        else if(action.equals("perev.hiof.com.action.SHOW_DATE")){
            dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        }
        else {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        }

        if(dateFormat != null){
            long now = (new Date()).getTime();
            textView.setText(dateFormat.format(now));
        }

    }
}
