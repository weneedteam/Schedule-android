package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.playgilround.schedule.client.R;

import java.io.IOException;
import java.util.List;

import static com.playgilround.schedule.client.activity.AddScheduleActivity.LOCATION_OK;

/**
 * 19-01-01
 * 위치 관련 Activity
 */
public class SetLocationActivity extends Activity implements OnMapReadyCallback,
        MaterialSearchBar.OnSearchActionListener, View.OnClickListener {

    double latitude; //위도
    double longitude; //경도

    double searchLatitude;
    double searchLongitude;
    String searchLocation;

    //현재 SearchBar 에 적힌 텍스트 확인.
    String strSearchBar;
    static final String TAG = SetLocationActivity.class.getSimpleName();

    public static final String LOCATION_CURRENT = "current";
    public static final String LOCATION_DESTINATION = "destination";

    public static final String INTENT_EXTRA_LOCATION = "location";
    public static final String INTENT_EXTRA_LATITUDE = "latitude";
    public static final String INTENT_EXTRA_LONGITUDE = "longitude";

    private GoogleMap mMap;
    private Geocoder geocoder;

    private MaterialSearchBar searchBar;

    ProgressDialog progress;


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
            progress = new ProgressDialog(this);
            progress.setCanceledOnTouchOutside(false);
            progress.setTitle(getString(R.string.text_location));

            progress.setMessage(getString(R.string.text_find_current_location));
            progress.show();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        //Material Search Bar 관련 작업
        searchBar = findViewById(R.id.searchBar);
        searchBar.setHint(getString(R.string.text_search_location));
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
                strSearchBar = editable.toString();
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
                break;

            case MaterialSearchBar.BUTTON_SPEECH:
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
                List<Address> addressList = null;

                try {
                    addressList = geocoder.getFromLocationName(text.toString(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addressList.size() != 0) {
                    String title = addressList.get(0).getFeatureName();
                    String snippet = addressList.get(0).getCountryName();

                    searchLatitude = addressList.get(0).getLatitude();
                    searchLongitude = addressList.get(0).getLongitude();

                    //현재 자기 위치 좌표 생성
                    LatLng currentMap = new LatLng(latitude, longitude);

                    //검색 된 위치 좌표 생성
                    LatLng searchMap = new LatLng(searchLatitude, searchLongitude);

                    //Create Marker
                    MarkerOptions mOption = new MarkerOptions();
                    mOption.title(title);
                    mOption.snippet(snippet);
                    mOption.position(searchMap);
                    mOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                    //Add Marker
                    mMap.addMarker(mOption);

                    //내 위치와 목적지 거리 계산
                    Location currentLocation = new Location(LOCATION_CURRENT);
                    currentLocation.setLatitude(latitude);
                    currentLocation.setLongitude(longitude);

                    Location destLocation = new Location(LOCATION_DESTINATION);
                    destLocation.setLatitude(searchLatitude);
                    destLocation.setLongitude(searchLongitude);

                    double distance = currentLocation.distanceTo(destLocation);

                    //해당된 좌표로 화면 줌.
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchMap, setZoomLevel(distance)));

                    //내위치 -> 목적지 거리를 선으로 표시.
                    mMap.addPolyline(new PolylineOptions().add(currentMap, searchMap).width(5).color(Color.RED));

                    searchLocation = text.toString();

                } else if (addressList.size() == 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_error_msg_find_location), Toast.LENGTH_LONG).show();
                }

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
        progress.cancel();
        MarkerOptions markerOptions = new MarkerOptions();

        LatLng destMap = new LatLng(latitude, longitude);

        markerOptions.position(destMap);

        //반경 500M 원
        CircleOptions circle = new CircleOptions().center(destMap)
                .radius(500)     //반지름 단위 : m
                .strokeWidth(0f) //선 없음
                .fillColor(getResources().getColor(R.color.color_map_background));//배경색
        markerOptions.title(getString(R.string.text_my_location));
        markerOptions.snippet(getString(R.string.text_my_location));
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

    /**
     * Zoom level 0 1:20088000.56607700 meters
     * Zoom level 1 1:10044000.28303850 meters
     * Zoom level 2 1:5022000.14151925 meters
     * Zoom level 3 1:2511000.07075963 meters
     * Zoom level 4 1:1255500.03537981 meters
     * Zoom level 5 1:627750.01768991 meters
     * Zoom level 6 1:313875.00884495 meters
     * Zoom level 7 1:156937.50442248 meters
     * Zoom level 8 1:78468.75221124 meters
     * Zoom level 9 1:39234.37610562 meters
     * Zoom level 10 1:19617.18805281 meters
     * Zoom level 11 1:9808.59402640 meters
     * Zoom level 12 1:4909.29701320 meters
     * Zoom level 13 1:2452.14850660 meters
     * Zoom level 14 1:1226.07425330 meters
     * Zoom level 15 1:613.03712665 meters
     * Zoom level 16 1:306.51856332 meters
     * Zoom level 17 1:153.25928166 meters
     * Zoom level 18 1:76.62964083 meters
     * Zoom level 19 1:38.31482042 meters
     * <p>
     * 목적지와, 내위치거리를 계산해서,
     * Google map zoom level을 결정함.
     */
    public int setZoomLevel(Double distance) {
        if (distance < 38.31482042) {
            return 19;
        } else if (distance < 76.62964083) {
            return 18;
        } else if (distance < 153.25928166) {
            return 17;
        } else if (distance < 306.51856332) {
            return 16;
        } else if (distance < 613.03712665) {
            return 15;
        } else if (distance < 1226.07425330) {
            return 14;
        } else if (distance < 2452.14850660) {
            return 13;
        } else if (distance < 4909.29701320) {
            return 12;
        } else if (distance < 9808.59402640) {
            return 11;
        } else if (distance < 19617.18805281) {
            return 10;
        } else if (distance < 39234.37610562) {
            return 9;
        } else if (distance < 78468.75221124) {
            return 8;
        } else if (distance < 156937.50442248) {
            return 7;
        } else if (distance < 313875.00884495) {
            return 6;
        } else if (distance < 627750.01768991) {
            return 5;
        } else if (distance < 1255500.03537981) {
            return 4;
        } else if (distance < 2511000.07075963) {
            return 3;
        } else if (distance < 5022000.14151925) {
            return 2;
        } else if (distance < 10044000.28303850) {
            return 1;
        } else if (distance < 20088000.56607700) {
            return 0;
        } else {
            return 0;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                finish();
                break;

            case R.id.tvConfirm:
                //Search Bar 텍스트와, 지정된 Location 이 같을 때만 finish
                if (strSearchBar.equals(searchLocation)) {
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_EXTRA_LOCATION, searchLocation);
                    intent.putExtra(INTENT_EXTRA_LATITUDE, searchLatitude);
                    intent.putExtra(INTENT_EXTRA_LONGITUDE, searchLongitude);

                    Log.d(TAG, "tvConfirm --> " + searchLocation + "--" + searchLatitude + "--" + searchLongitude);
                    setResult(LOCATION_OK, intent);
                    finish();
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_msg_check_location), Toast.LENGTH_LONG).show();
                    break;
                }

        }
    }

}
