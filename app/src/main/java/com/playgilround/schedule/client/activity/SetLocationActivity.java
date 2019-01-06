package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.playgilround.schedule.client.R;

/**
 * 19-01-01
 * 위치 관련 Activity
 */
public class SetLocationActivity extends Activity implements OnMapReadyCallback,
        MaterialSearchBar.OnSearchActionListener, View.OnClickListener {

    double latitude; //위도
    double longitude; //경도

    static final String TAG = SetLocationActivity.class.getSimpleName();

    private GoogleMap mMap;
    private Geocoder geocoder;

    private MaterialSearchBar searchBar;

    /**
     * Material Search Bar 검색 버튼 클릭 시,
     * 2번 검색되는 버그가 있어 flag 추가 (isSearch)
     * 검색 버튼 클릭 시,
     * SearchBar 텍스트가 이전과 같을 경우
     * 검색이 안되는 버그가 있어 flag 추가 (isInit).
     */
    private boolean isInit = false;
    private boolean isSearch = true;

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

        //Material Search Bar 관련 작업
        searchBar = findViewById(R.id.searchBar);
        searchBar.setHint("위치 검색");
        searchBar.setSpeechMode(false);

        searchBar.setOnSearchActionListener(this);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                isInit = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.tvConfirm).setOnClickListener(this);
        findViewById(R.id.tvCancel).setOnClickListener(this);
    }

    //SearchBar Clicked
    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                Log.d(TAG, "Button Navigation MaterialSearchBar");
                break;

            case MaterialSearchBar.BUTTON_SPEECH:
                Log.d(TAG, "Button speech MaterialSearchBar..");
                break;
        }
    }

    //SearchBar inputText changed
    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    //SearchBar SearchButton Click
    @Override
    public void onSearchConfirmed(CharSequence text) {
        if (isSearch) {
            if (isInit) {
                isSearch = false;
            }
        } else {
            isSearch = true;
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.d(TAG, "Location Test ->" + latitude + "//" + longitude);
            finishLocation();
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

        Log.d(TAG, "onMapReady latitude ->" + latitude + longitude);
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

    //위도 경도 탐색완료.
    private void finishLocation() {
        FragmentManager fragmentManager = getFragmentManager();

        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                finish();
                break;

            case R.id.tvConfirm:
                break;
        }
    }

}
