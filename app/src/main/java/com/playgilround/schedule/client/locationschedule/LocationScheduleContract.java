package com.playgilround.schedule.client.locationschedule;

import android.location.Location;

import com.applandeo.materialcalendarview.EventDay;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

public interface LocationScheduleContract {

    interface View extends BaseView<Presenter> {

        //주변 반경, 핀 지도 관련 표시
        void setMapMarker(LatLng destMap);

        //검색 된 결과 지도 표시
        void setMapSearchConfirmed(String title, String snippet, LatLng currentMap, LatLng searchMap, int zoomLevel);
    }

    interface Presenter extends BasePresenter {

        // text로 장소 검색.
         void onSearchConfirmed(CharSequence text);

        // Google Map 준비 완료
        void setMapDisplay(final GoogleMap map, double latitude, double longitude);
    }
}
