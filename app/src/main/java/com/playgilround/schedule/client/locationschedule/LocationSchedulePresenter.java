package com.playgilround.schedule.client.locationschedule;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.playgilround.schedule.client.model.LocationInfo;
import com.playgilround.schedule.client.model.ZoomLevel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.playgilround.schedule.client.locationschedule.LocationScheduleActivity.LOCATION_CURRENT;
import static com.playgilround.schedule.client.locationschedule.LocationScheduleActivity.LOCATION_DESTINATION;

/**
 * 19-02-18
 * 위치 관련 데이터 처리 MVP
 */
public class LocationSchedulePresenter implements LocationScheduleContract.Presenter {

    private static final String TAG = LocationSchedulePresenter.class.getSimpleName();

    private final LocationScheduleContract.View mView;
    private final Context mContext;

    private GoogleMap mMap;
    private Geocoder geocoder;

    private double latitude; //위도
    private double longitude; //경도

    private double searchLatitude;
    private double searchLongitude;
    private String searchLocation;

    LocationSchedulePresenter(Context context, LocationScheduleContract.View view) {
        mView = view;
        mContext = context;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void setMapDisplay(double latitude, double longitude) {
        geocoder = new Geocoder(mContext);
        this.latitude = latitude;
        this.longitude = longitude;

        LatLng destMap = new LatLng(latitude, longitude);

        mView.setMapMarker(destMap);
    }

    //장소 검색
    @Override
    public void onSearchConfirmed(CharSequence text) {
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

            //내 위치와 목적지 거리 계산
            Location currentLocation = new Location(LOCATION_CURRENT);
            currentLocation.setLatitude(latitude);
            currentLocation.setLongitude(longitude);

            Location destLocation = new Location(LOCATION_DESTINATION);
            destLocation.setLatitude(searchLatitude);
            destLocation.setLongitude(searchLongitude);

            double distance = currentLocation.distanceTo(destLocation);

            //해당 좌표로 화면 줌
            int zoomLevel = ZoomLevel.getZoomLevel(distance);

            searchLocation = text.toString();

            mView.setMapSearchConfirmed(title, snippet, currentMap, searchMap, zoomLevel);
        } else  {
            // 장소 결과가 없을 경우 View 로 어떻게 넘길 지?
            // Toast.makeText(getApplicationContext(), getString(R.string.toast_error_msg_find_location), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public ArrayList<LocationInfo> getLocationInfo() {
        ArrayList<LocationInfo> arrLocationInfo = new ArrayList<>();

        arrLocationInfo.add(new LocationInfo(searchLocation, searchLatitude, searchLongitude));

        return arrLocationInfo;
    }
}
