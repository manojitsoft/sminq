package com.sminq.location;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manojkumar on 7/31/2017.
 */

public class NotificationService extends Service {

    GPSTracker trackerService;

    boolean mBound = false;

    GeoFence[] fences = new GeoFence[]{
        new GeoFence(12.3456, 77.6543),
        new GeoFence(12.3452, 77.2132),
        new GeoFence(12.3123, 78.6897)
    };

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void onCreate() {
        Intent action = new Intent(NotificationService.this, GPSTracker.class);
        startService(action);
        bindService(action, mConnection, BIND_AUTO_CREATE);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(mBound && trackerService.location() != null) {
                    //TODO Check in existing list of fences and if exist fire notification or not.
                }
            }
        }, 0, 5, TimeUnit.MINUTES);
        super.onCreate();
        super.onCreate();
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            GPSTracker.LocationBinder binder = (GPSTracker.LocationBinder) service;
            trackerService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onDestroy() {
        executor.shutdown();
        unbindService(mConnection);
        mBound = false;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
