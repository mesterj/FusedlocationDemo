package proba.joco.hu.fusedlocationdemo;

import android.app.Service;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

/**
 * Created by Joco on 2015.01.18..
 */
public class MyLocationManager  {

    private LocationManager locMan;
    private Context ctx;
    private LocationListener listener;

    public MyLocationManager (Context aCtx, LocationListener aListener) {
        ctx = aCtx;
        listener = aListener;
        locMan = (LocationManager) ctx.getSystemService(Service.LOCATION_SERVICE);

    }

    public void startLocationMonitoring(){
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER,60,10,listener);
        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,60,1,listener);
    }

    public void stopLocationMonitoring(){
        locMan.removeUpdates(listener);
    }
}
