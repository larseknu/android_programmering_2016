package com.example.perev.playingwiththreadslecture;

import android.content.Context;
import android.location.Location;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by perev on 08/02/2016.
 */
public class Worker {
    Context mContext;

    public Worker(Context context){
        mContext = context;
    }

    private void addDelay(){
        try{
            Thread.sleep(3000);
        }
        catch (InterruptedException e){
            e.printStackTrace();;
        }
    }

    private Location createLocationManually(){
        Location lastLocation = new Location("Hiof");
        Date now = new Date();
        lastLocation.setTime(now.getTime());
        lastLocation.setLatitude(59.128229);
        lastLocation.setLongitude(11.352860);

        return lastLocation;
    }

    public Location getLocation(){
        Location location = null;

        location = createLocationManually();

        addDelay();
        return location;
    }

    public void save(Location location, String address, String fileName){
        try{
            File targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            if(!targetDir.exists()){
                targetDir.mkdir();
            }

            File outFile = new File(targetDir, fileName);
            FileWriter fileWriter = new FileWriter(outFile, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            String outline = String.format(Locale.getDefault(), "%s - %f/%f\n",
                    new SimpleDateFormat("dd.MM.yyyy HH:mm",
                            Locale.getDefault()).format(location.getTime()),
                            location.getLatitude(),
                            location.getLongitude());

            writer.write(outline);
            writer.write(address + "\n");

            writer.flush();
            writer.close();
            fileWriter.close();

        }
        catch (Exception e){
            Log.e("Worker.save", "Error");
        }
    }
}
