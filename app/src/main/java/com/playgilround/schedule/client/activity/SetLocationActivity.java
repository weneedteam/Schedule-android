package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playgilround.schedule.client.R;

/**
 * 19-01-01
 * 위치 관련 Activity
 */
public class SetLocationActivity extends Activity implements OnMapReadyCallback {

    double latitude; //위도
    double longitude; //경도

    static final String TAG = SetLocationActivity.class.getSimpleName();

    private GoogleMap mMap;
    private Geocoder geocoder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

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

    @Override
    public void onMapReady(final GoogleMap map) {
        mMap = map;

        geocoder = new Geocoder(this);

        MarkerOptions markerOptions = new MarkerOptions();

        LatLng destMap = new LatLng(latitude, longitude);

        markerOptions.position(destMap);

        //반경 500M 원
        CircleOptions circle = new CircleOptions().center(destMap)
                .radius(500)     //반지름 단위 : m
                .strokeWidth(0f) //선 없음
                .fillColor(Color.parseColor("#880000ff")); //배경색
        markerOptions.title("내 위치");
        markerOptions.snippet("내 위치");
        map.addMarker(markerOptions);
        map.addCircle(circle);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(destMap, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

}
