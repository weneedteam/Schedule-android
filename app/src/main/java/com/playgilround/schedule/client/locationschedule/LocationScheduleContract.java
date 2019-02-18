package com.playgilround.schedule.client.locationschedule;

import com.applandeo.materialcalendarview.EventDay;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

public interface LocationScheduleContract {

    interface View extends BaseView<Presenter> {

        //주변 반경, 핀 지도 관련 표시
        void setMapMarker(LatLng destMap, MarkerOptions markerOptions);
    }

    interface Presenter extends BasePresenter {

        // text로 장소 검색.
        // void onSearchConfirmed(CharSequence text);

        // Google Map 준비 완료
        void setMapDisplay(final GoogleMap map, double latitude, double longitude);
    }
}
