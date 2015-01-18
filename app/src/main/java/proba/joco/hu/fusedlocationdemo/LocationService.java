package proba.joco.hu.fusedlocationdemo;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Joco on 2015.01.18..
 */
public class LocationService extends Service implements LocationListener{

    public static final String BR_NEW_LOCATION = "BR_NEW_LOCATION";
    public static final String KEY_LOCATION = "KEY_LOCATION";

    public MyLocationManager myLocMan = null;
    public boolean locationMonitorRunning = false;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!locationMonitorRunning) {
            myLocMan = new MyLocationManager(getApplicationContext(),this);
            locationMonitorRunning=true;
            myLocMan.startLocationMonitoring();
        }
        return START_STICKY;
    }



    @Override
    public void onLocationChanged(Location location) {
        Intent i = new Intent(BR_NEW_LOCATION);
        i.putExtra(KEY_LOCATION,location);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
