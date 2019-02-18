package com.playgilround.schedule.client.locationschedule;

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
import com.playgilround.schedule.client.model.ZoomLevel;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.playgilround.schedule.client.addschedule.AddScheduleActivity.LOCATION_OK;

/**
 * 19-01-01
 * 위치 관련 Activity
 */
public class LocationScheduleActivity extends Activity implements OnMapReadyCallback,
        MaterialSearchBar.OnSearchActionListener, LocationScheduleContract.View{

    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    double latitude; //위도
    double longitude; //경도

    double searchLatitude;
    double searchLongitude;
    String searchLocation;

    //현재 SearchBar 에 적힌 텍스트 확인.
    String strSearchBar;
    static final String TAG = LocationScheduleActivity.class.getSimpleName();

    public static final String LOCATION_CURRENT = "current";
    public static final String LOCATION_DESTINATION = "destination";

    public static final String INTENT_EXTRA_LOCATION = "location";
    public static final String INTENT_EXTRA_LATITUDE = "latitude";
    public static final String INTENT_EXTRA_LONGITUDE = "longitude";

    private GoogleMap mMap;
    private Geocoder geocoder;

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

    private LocationScheduleContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ButterKnife.bind(this);

        new LocationSchedulePresenter(this, this);
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
//                mPresenter.onSearchConfirmed(text);
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
                    int zoomLevel = ZoomLevel.getZoomLevel(distance);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchMap, zoomLevel));

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
            Log.d(TAG, "LocationListener ->" + latitude + "//" + longitude);

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
        mPresenter.setMapDisplay(mMap, latitude, longitude);
    }

    @Override
    public void setMapMarker(LatLng destMap, MarkerOptions markerOptions) {
        progress.cancel();
        //반경 500M 원
        CircleOptions circle = new CircleOptions().center(destMap)
                .radius(500)     //반지름 단위 : m
                .strokeWidth(0f) //선 없음
                .fillColor(getResources().getColor(R.color.color_map_background));//배경색
        markerOptions.title(getString(R.string.text_my_location));
        markerOptions.snippet(getString(R.string.text_my_location));
        mMap.addMarker(markerOptions);
        mMap.addCircle(circle);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destMap, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    //위도 경도 탐색완료.
    private void finishLocation() {
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.tvCancel)
    void onCancelClick() {
        finish();
    }

    @OnClick(R.id.tvConfirm)
    void onConfirmClick() {
        //Search Bar 텍스트와, 지정된 Location이 같을 때만 finish
        if (strSearchBar == null || searchLocation == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_msg_null_location), Toast.LENGTH_LONG).show();
        } else if (strSearchBar.equals(searchLocation)) {
            Intent intent = new Intent();
            intent.putExtra(INTENT_EXTRA_LOCATION, searchLocation);
            intent.putExtra(INTENT_EXTRA_LATITUDE, searchLatitude);
            intent.putExtra(INTENT_EXTRA_LONGITUDE, searchLongitude);
            setResult(LOCATION_OK, intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_msg_check_location), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPresenter(LocationScheduleContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
