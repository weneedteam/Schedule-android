package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.playgilround.schedule.client.R;

/**
 * 19-01-01
 * 위치 관련 Activity
 */
public class SetLocationActivity extends Activity {

    double latitude; //위도
    double longitude; //경도

    static final String TAG = SetLocationActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            //GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);

            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.d(TAG, "Location Test ->" + latitude + "//" + longitude);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}
