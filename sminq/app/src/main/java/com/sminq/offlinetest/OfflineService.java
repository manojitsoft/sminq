package com.sminq.offlinetest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sminq.offlinetest.utils.OfflineDb;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manojkumar on 7/30/2017.
 */

public class OfflineService extends Service {

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    OfflineDb dbHelper;

    String[] records = new String[]{
        "Manoj", "Rajesh", "Kumar"
    };

    @Override
    public void onCreate() {
        dbHelper = new OfflineDb(getApplicationContext());
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = dbHelper.selectRecords();

                if(cursor != null && isNetworkConnected()) {
                    dbHelper.deleteRecords();
                }

                if(isNetworkConnected())  {
                    //TODO hit api to send the records here
                } else {
                    for(int i= 0; i < records.length; i++) {
                        dbHelper.createRecords(UUID.randomUUID().toString(), records[i]);
                    }
                }
            }
        }, 0, 5, TimeUnit.MINUTES);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        executor.shutdown();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
