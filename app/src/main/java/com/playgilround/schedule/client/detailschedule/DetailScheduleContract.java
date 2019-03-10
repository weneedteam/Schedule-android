package com.playgilround.schedule.client.detailschedule;

import com.google.android.gms.maps.model.LatLng;
import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

/**
 * 19-03-10
 */
public interface DetailScheduleContract {

    interface View extends BaseView<Presenter> {

        //주변 반경, 핀 지도 관련 표시
        void setMapMarker(LatLng destMap);
    }

    interface Presenter extends BasePresenter {

        //Map 준비 완료
        void setMapDisplay(double latitude, double longitude);
    }
}
