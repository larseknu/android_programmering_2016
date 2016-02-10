package com.example.perev.playingwithserviceslecture;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;

/**
 * Created by perev on 10/02/2016.
 */
public class OnDemandWorkerService extends IntentService {

    public OnDemandWorkerService(){
        super("OnDemandWorker");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogHelper.ProcessAndThreadId("OnDemandWorker.onHandleIntent");

        String fileName = intent.getStringExtra("fileName");
        if(fileName == null){
            fileName = "ServiceOutputFile.out";
        }

        Worker worker = new Worker(this);

        Location location = worker.getLocation();

        String address = worker.reverseGeocode(location);

        worker.save(location, address, fileName);
    }
}
