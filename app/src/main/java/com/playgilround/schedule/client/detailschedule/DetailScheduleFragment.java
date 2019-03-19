package com.playgilround.schedule.client.detailschedule;

import android.animation.Animator;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.addschedule.AddScheduleActivity;
import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * 19-01-23
 * 저장된 스케줄 정보가 나오는 Activity
 */
public class DetailScheduleFragment extends android.app.DialogFragment implements OnMapReadyCallback, DetailScheduleContract.View {

    @BindView(R.id.ivMap)
    ImageView ivMap;

    @BindView(R.id.ivBtnLaunch)
    ImageButton ivBtn;

    @BindView(R.id.linearBackView)
    LinearLayout backView;

    @BindView(R.id.linearBackButton)
    LinearLayout backBtnView;

    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.tvScheduleTitle)
    TextView tvTitle;

    @BindView(R.id.tvScheduleLocation)
    TextView tvLocation;

    static String strDate;
    static int scheduleId;
    Realm realm;

    float pixelDensity;
    Animation animation;

    String strDay;
    String strTitle;
    String strLocation;

    private double latitude;
    private double longitude;

    private double currentLatitude;
    private double currentLongitude;

    boolean flag = true;
    boolean firstFlag = true;

    ProgressDialog progress;

    private GoogleMap mMap;

    private MapFragment mapFragment;
    public static final String INTENT_MODIFY_ID = "modifyId";
    static final String TAG = DetailScheduleFragment.class.getSimpleName();

    private DetailScheduleContract.Presenter mPresenter;

    public static DetailScheduleFragment getInstance(String date, int id) {
        strDate = date;
        scheduleId = id;

        return new DetailScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_schedule, container);
        ButterKnife.bind(this, rootView);

        Context mContext = getContext();

        new DetailSchedulePresenter(mContext, this);

        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        pixelDensity = getResources().getDisplayMetrics().density;

        animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_anim);
        findScheduleInfo();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        progress = new ProgressDialog(getContext());
        progress.setTitle(getString(R.string.text_location));
        progress.setMessage(getString(R.string.text_find_current_location));
        progress.setCancelable(false);
        progress.show();
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (firstFlag) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

                finishLocation();
            }
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

    private void findScheduleInfo() {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm -> {
            Schedule schedule = realm.where(Schedule.class).equalTo("id", scheduleId).findFirst();
            DateTime dateTime = new DateTime(Long.valueOf(schedule.getTime()), DateTimeZone.getDefault());
            strDay = dateTime.toString(getString(R.string.text_date_day_time));
            strTitle = schedule.getTitle();
            strLocation = schedule.getLocation();

            latitude = schedule.getLatitude();
            longitude = schedule.getLongitude();

            tvTime.setText(strDay);
            tvTitle.setText(strTitle);
            tvLocation.setText(strLocation);

        });
    }

    @OnClick(R.id.ivBtnLaunch)
    public void onButtonLaunch() {

        int x = ivMap.getRight();
        int y = ivMap.getBottom();

        x -= ((28 * pixelDensity) + (16 * pixelDensity));

        int hypotenuse = (int) Math.hypot(ivMap.getWidth(), ivMap.getHeight());

        if (flag) {
            ivBtn.setBackgroundResource(R.drawable.rounded_cancel_button);
            ivBtn.setImageResource(R.drawable.ic_chevron_left_black_24dp);

            FrameLayout.LayoutParams paramters = (FrameLayout.LayoutParams)
                    backView.getLayoutParams();

            paramters.height = ivMap.getHeight();
            backView.setLayoutParams(paramters);

            //Animation 효과
            Animator anim = ViewAnimationUtils.createCircularReveal(backView, x, y, 0, hypotenuse);
            anim.setDuration(700);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    backBtnView.setVisibility(View.VISIBLE);
                    backBtnView.startAnimation(animation);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            backView.setVisibility(View.VISIBLE);
            anim.start();

            flag = false;
        } else {
            //다시 클릭시 되돌아 오기.
            ivBtn.setBackgroundResource(R.drawable.rounded_button);
            ivBtn.setImageResource(R.drawable.ic_chevron_right_black_24dp);

            Animator anim = ViewAnimationUtils.createCircularReveal(backView, x, y, hypotenuse, 0);
            anim.setDuration(400);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    backView.setVisibility(View.GONE);
                    backBtnView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            anim.start();
            flag = true;
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        //저장된 위치가 없을 경우엔 현재 위치가 표시 되도록.
        if (latitude == 0.0) {
            latitude = currentLatitude;
            longitude = currentLongitude;
        }
        mPresenter.setMapDisplay(latitude, longitude);
    }

    private void finishLocation() {
        // Todo:: Android X에 맞춰서 코드 정리 할 필요가 있음.
        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        firstFlag = false;
    }

    @Override
    public void setMapMarker(LatLng destMap) {

        progress.cancel();
        MarkerOptions destMarker = new MarkerOptions().title(getString(R.string.text_destination))
                .snippet(getString(R.string.text_destination)).position(destMap).icon(null);

        mMap.addMarker(destMarker);
        mMap.addCircle(new CircleOptions().center(destMap).radius(500).strokeWidth(0f).fillColor(getResources().getColor(R.color.color_map_background)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destMap, 15));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @OnClick(R.id.btnDelete)
    void onScheduleDelete() {
        realm.executeTransaction(realm -> {
            Schedule schedule = realm.where(Schedule.class).equalTo("id", scheduleId).findFirst();
            schedule.deleteFromRealm();
            getActivity().finish();
        });
    }

    @OnClick(R.id.btnModify)
    void onScheduleModify() {
        startActivity(new Intent(getActivity(), AddScheduleActivity.class)
                .putExtra(INTENT_MODIFY_ID, scheduleId));
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapFragment != null)
            getFragmentManager().beginTransaction().remove(mapFragment).commit();
    }

    @Override
    public void setPresenter(DetailScheduleContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
