package com.playgilround.schedule.client.locationschedule;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

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

    double latitude; //위도
    double longitude; //경도

    LocationSchedulePresenter(Context context, LocationScheduleContract.View view) {
        mView = view;
        mContext = context;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void setMapDisplay(final GoogleMap map, double latitude, double longitude) {
        mMap = map;
        geocoder = new Geocoder(mContext);

        MarkerOptions markerOptions = new MarkerOptions();

        LatLng destMap = new LatLng(latitude, longitude);

        markerOptions.position(destMap);

        mView.setMapMarker(destMap, markerOptions);
    }
}
