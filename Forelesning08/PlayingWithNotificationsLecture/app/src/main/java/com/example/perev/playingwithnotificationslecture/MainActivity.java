package com.example.perev.playingwithnotificationslecture;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int NOTIFICATION_ID = 1;

    private int _mNotificationCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void btnNotificationOnClick(View view){
        String title = "Avengers!";
        String text = "Assemble!";

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction("Notification");
        intent.putExtra(DetailsActivity.TITLE_EXTRA, title);
        intent.putExtra(DetailsActivity.BODY_TEXT_EXTRA, text);

        NotificationCompat.Builder builder = initBasicBuilder(title, text, intent);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }

    public void btnPersonalNotificationOnClick(View view){
        String title = "Avengers, assemble!";
        String text = "Help needed!";

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction("PersonalNotification");
        intent.putExtra(DetailsActivity.TITLE_EXTRA, title);
        intent.putExtra(DetailsActivity.BODY_TEXT_EXTRA, text);

        NotificationCompat.Builder builder = initBasicBuilder(title, text, intent);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_captain_america));
        builder.setPriority(Notification.PRIORITY_MAX);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID+1, notification);
    }

    public void btnMultiNotificationOnClick(View view){
        String title = "Message from Hulk";
        String text = "I need help!";

        String detailText1 = "I need help!";
        String detailText2 = "I'm getting angry!";
        String detailText3 = "AAAARRGGHH!";
        ++_mNotificationCount;

        ArrayList<String> textValues = new ArrayList<String>();
        textValues.add(detailText1);
        textValues.add(detailText2);
        textValues.add(detailText3);

        Intent intent = new Intent(this, ListActivity.class);
        intent.setAction("MultiNotifications");
        intent.putExtra(ListActivity.TITLE_EXTRA, title);
        intent.putExtra(ListActivity.TEXT_VALUES_EXTRA, textValues);

        NotificationCompat.Builder builder = initBasicBuilder(title, text, intent);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_hulk))
                .setNumber(_mNotificationCount)
                .setTicker("You recieved another message from Hulk");

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID+2, notification);

    }

    public void btnBigTextNotificationOnClick(View view){
        String title = "Tony Stark";
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum interdum est erat, a venenatis enim consectetur sed. Nam auctor tortor ac erat maximus, ut luctus ipsum iaculis. Vivamus blandit consequat quam eu dignissim. Integer quis ligula quis nibh pretium auctor et ac risus. Sed pretium porta arcu non accumsan. Suspendisse potenti. Donec ac mauris nec orci volutpat malesuada. In efficitur, augue vitae varius efficitur, arcu nisl maximus leo, ut sodales mi tortor pharetra ante. Nam suscipit dignissim orci, sit amet malesuada dolor placerat nec. Aliquam erat volutpat. Curabitur facilisis augue a lectus sodales accumsan. Donec tincidunt porta lorem sed porta. In id quam magna. Nam malesuada malesuada malesuada. Donec vitae pulvinar quam, a tincidunt erat.";
        String bigSummary = "Lorem ipsum";

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction("BigTextNotification");
        intent.putExtra(DetailsActivity.TITLE_EXTRA, title);
        intent.putExtra(DetailsActivity.BODY_TEXT_EXTRA, text);

        NotificationCompat.Builder builder = initBasicBuilder(title, text, intent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_iron_man));

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(title)
                .setSummaryText(bigSummary)
                .bigText(text);
        builder.setStyle(bigTextStyle);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID+3, notification);
    }

    public void btnBigPictureNotificationOnClick(View view){
        String title = "Avengers - to New York!";
        String text ="New York is under attack!";

        Intent intent = new Intent(this, PictureActivity.class);
        intent.setAction("BigPictureNotification");
        intent.putExtra(PictureActivity.TITLE_EXTRA, title);
        intent.putExtra(PictureActivity.IMAGE_TEXT_EXTRA, text);
        intent.putExtra(PictureActivity.IMAGE_RESOURCE_ID_EXTRA, R.drawable.avengers_new_york);

        NotificationCompat.Builder builder = initBasicBuilder(title, text, intent);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_iron_man));
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.avengers_new_york))
                .setSummaryText(text)
                .setBigContentTitle(title);

        builder.setStyle(bigPictureStyle);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID + 4, notification);

    }

    private NotificationCompat.Builder initBasicBuilder(String title, String text, Intent intent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_avengers)
                .setContentTitle(title)
                .setContentText(text);

        if(intent != null){
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }

        return builder;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
