package proba.joco.hu.fusedlocationdemo;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Joco on 2015.01.18..
 */
public class FragmentLocation extends Fragment {

    private TextView tvLatValue,tvLngValue;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location,null);

        View infolat = rootView.findViewById(R.id.tile_lat);
        TextView tvLatHead = (TextView) infolat.findViewById(R.id.tvHead);
        tvLatValue = (TextView) infolat.findViewById(R.id.tvValue);
        tvLatHead.setText("Latitude: ");

        View infolng = rootView.findViewById(R.id.tile_lng);
        TextView tvLngHead = (TextView) infolng.findViewById(R.id.tvHead);
        tvLngValue = (TextView) infolng.findViewById(R.id.tvValue);
        tvLngHead.setText("Longitude: ");

        Switch switchService = (Switch) rootView.findViewById(R.id.switchLocService);

        if (isMyServiceRunning(LocationService.class))
            switchService.setChecked(true);

        switchService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Intent i = new Intent(getActivity(),LocationService.class);
                    getActivity().startService(i);
                } else {
                    Intent i = new Intent(getActivity(),LocationService.class);
                    getActivity().stopService(i);
                }

            }
        });

         return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mPosReceiver,new IntentFilter(LocationService.BR_NEW_LOCATION));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mPosReceiver);
    }

    private BroadcastReceiver mPosReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location loc = intent.getParcelableExtra(LocationService.KEY_LOCATION);
            tvLatValue.setText("" + loc.getLatitude());
            tvLngValue.setText("" + loc.getLongitude());
        }
    };

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
