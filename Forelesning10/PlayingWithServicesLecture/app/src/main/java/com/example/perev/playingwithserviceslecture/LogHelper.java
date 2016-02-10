package com.example.perev.playingwithserviceslecture;

import android.util.Log;

import java.util.Locale;

/**
 * Created by perev on 10/02/2016.
 */
public class LogHelper {
    public static void ProcessAndThreadId(String label){
        String logMessage = String.format(Locale.getDefault(),
                "%s, Process ID: %d, Thread: %d",
                label,
                android.os.Process.myPid(),
                android.os.Process.myTid());
        Log.i("com.example.perev", logMessage);
    }

}
