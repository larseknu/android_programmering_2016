package com.example.perev.playingwithserviceslecture;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by perev on 09/02/2016.
 */
public class Worker {

    Context mContext;
    private final boolean mUseGpsToGetLocation = true;
    HandlerThread mGpsHandlerThread;
    LocationListener mLocationListener;
    LocationManager mLocationManager;

    public Worker(Context context) {
        mContext = context;
    }

    public void MonitorGpsInBackground() {
        if (mUseGpsToGetLocation) {

            //https://developer.android.com/reference/android/os/HandlerThread.html
            mGpsHandlerThread = new HandlerThread("GPSThread");
            mGpsHandlerThread.start();

            //https://developer.android.com/reference/android/location/LocationListener.html
            mLocationListener = new NoOpLocationListener();

            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            //If-statement required by Android 6.0 to check if permissions is met, since 6.0 checks permissions during run-time, and not on installation.

            //https://developer.android.com/reference/android/location/LocationManager.html#requestLocationUpdates(java.lang.String, long, float, android.location.LocationListener, android.os.Looper)
            //https://developer.android.com/reference/android/os/Looper.html
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener, mGpsHandlerThread.getLooper());
        }
    }

    public void stopGpsMonitoring() {
        if (mLocationManager != null)
            //If-statement required by Android 6.0 to check if permissions is met, since 6.0 checks permissions during run-time, and not on installation.
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        mLocationManager.removeUpdates(mLocationListener);

        if (mGpsHandlerThread != null)
            mGpsHandlerThread.quit();
    }

    public Location getLocation() {
        Location lastLocation = null;

        if (mUseGpsToGetLocation) {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            //If-statement required by Android 6.0 to check if permissions is met, since 6.0 checks permissions during run-time, and not on installation.
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return lastLocation = createLocationManually();
            }
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if(lastLocation == null)
            lastLocation = createLocationManually();

        addDelay();
        return lastLocation;
    }

    //From documentation:
    //Geocoding is the process of transforming a street address or other description of a location into a (latitude, longitude) coordinate.
    //Reverse geocoding is the process of transforming a (latitude, longitude) coordinate into a (partial) address
    //https://developer.android.com/reference/android/location/Geocoder.html
    //TLDR: input location with latitude/longitude, get address back
    public String reverseGeocode(Location location) {
        String addressDescription = null;

        try  {

            Geocoder geocoder = new Geocoder(mContext);
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            if(!addressList.isEmpty())
            {
                Address firstAddress = addressList.get(0);

                StringBuilder addressBuilder = new StringBuilder();
                for (int i = 0; i <= firstAddress.getMaxAddressLineIndex(); i++)
                {
                    if(i != 0)
                        addressBuilder.append(", ");
                    addressBuilder.append(firstAddress.getAddressLine(i));
                }
                addressDescription = addressBuilder.toString();
            }
            else {
                return "B.R.A. veien 4, 1757 Halden";
            }
        }
        catch (IOException ex) {
            Log.e("Worker.reverseGeocode", "IOException Error");
        }
        catch (Exception ex) {
            Log.e("Worker.reverseGeocode", "Error");
        }

        addDelay();
        return addressDescription;
    }

    public void save(Location location, String address, String fileName) {
        try {
            File targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if(!targetDir.exists())
                targetDir.mkdirs();

            File outFile = new File(targetDir, fileName);
            FileWriter fileWriter = new FileWriter(outFile, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            String outLine = String.format(Locale.getDefault(), "%s - %f/%f\n",
                    new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(location.getTime()),
                    location.getLatitude(),
                    location.getLongitude());
            writer.write(outLine);
            writer.write(address + "\n");

            writer.flush();
            writer.close();
            fileWriter.close();
        }
        catch (Exception ex){
            Log.e("Worker.save", "Error");
        }

        addDelay();
    }

    private Location createLocationManually() {
        Location lastLocation = new Location("Hiof");
        Date now = new Date();
        lastLocation.setTime(now.getTime());
        lastLocation.setLatitude(59.128229);
        lastLocation.setLongitude(11.352860);

        return lastLocation;
    }

    private void addDelay() {
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //https://developer.android.com/reference/android/location/LocationListener.html
    class NoOpLocationListener implements LocationListener{

        public void onLocationChanged(Location location) {
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String s) {
        }

        public void onProviderDisabled(String s) {
        }
    }
}
