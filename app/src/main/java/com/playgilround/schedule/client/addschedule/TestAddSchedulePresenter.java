package com.playgilround.schedule.client.addschedule;

import android.content.Context;
import android.util.Log;

import com.playgilround.schedule.client.ScheduleApplication;
import com.playgilround.schedule.client.addschedule.model.ScheduleDataModel;
import com.playgilround.schedule.client.addschedule.view.AddScheduleAdapter;
import com.playgilround.schedule.client.data.FriendList;
import com.playgilround.schedule.client.data.ScheduleData;
import com.playgilround.schedule.client.data.repository.FriendRepository;
import com.playgilround.schedule.client.model.BaseResponse;
import com.playgilround.schedule.client.model.Friend;
import com.playgilround.schedule.client.model.ResponseMessage;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class TestAddSchedulePresenter implements TestAddScheduleContract.Presenter {

    private final Realm mRealm;
    private final Context mContext;
    private final TestAddScheduleContract.View mView;
    private final CompositeDisposable mCompositeDisposable;
    private ScheduleDataModel mScheduleDataModel;

    @Inject
    ScheduleData mSchedule;

    @Inject
    FriendRepository mFriendRepository;

    TestAddSchedulePresenter(Context context, TestAddScheduleContract.View view, ScheduleDataModel scheduleDataModel) {
        mView = view;
        mContext = context;
        mRealm = Realm.getDefaultInstance();

        mView.setPresenter(this);
        ((ScheduleApplication) mContext.getApplicationContext()).appComponent.getFriendInject(this);
        mCompositeDisposable = new CompositeDisposable();
        mScheduleDataModel = scheduleDataModel;
    }



    @Override
    public void start() {

    }

    @Override
    public void onClickNext(int position) {
        boolean check = false;

        switch (position) {
            case AddScheduleAdapter.TYPE_SCHEDULE_TITLE: {
                mSchedule.setTitle(mScheduleDataModel.getScheduleTitle());
                check = mSchedule.getTitle() != null;
            }
            case AddScheduleAdapter.TYPE_SCHEDULE_DATE: {
                mSchedule.setStartDate("2019-06-22T17:30:00");
                mSchedule.setEndDate("2019-06-22T17:30:00");
                check = true;
            }
            case AddScheduleAdapter.TYPE_SCHEDULE_MEMBER: {

            }
            case AddScheduleAdapter.TYPE_SCHEDULE_PLACE: {

            }
            case AddScheduleAdapter.TYPE_SCHEDULE_MEMO: {

            }
            case AddScheduleAdapter.TYPE_SCHEDULE_MAP: {

            }
            case AddScheduleAdapter.TYPE_SCHEDULE_RESULT: {

            }
        }
        mView.fieldCheck(check);
    }

    @Override
    public void onClickBack(int position) {
        switch (position) {
            case AddScheduleAdapter.TYPE_SCHEDULE_TITLE: {
                mSchedule.setTitle(null);
            }
            case AddScheduleAdapter.TYPE_SCHEDULE_DATE: {
                mSchedule.setStartDate(null);
                mSchedule.setEndDate(null);
            }
            case AddScheduleAdapter.TYPE_SCHEDULE_MEMBER: {
                mSchedule.setMember(null);
            }
            case AddScheduleAdapter.TYPE_SCHEDULE_PLACE: {
                mSchedule.setPlace(null);
            }
            case AddScheduleAdapter.TYPE_SCHEDULE_MEMO: {
                mSchedule.setMemo(null);
            }
            case AddScheduleAdapter.TYPE_SCHEDULE_MAP: {
                mSchedule.setMap(null);
            }
            case AddScheduleAdapter.TYPE_SCHEDULE_RESULT: {
                mSchedule.setResult(null);
            }
        }
    }

    @Override
    public void scheduleSave() {

    }

    @Override
    public void getLocalFriendList() {
        mRealm.executeTransaction(realm -> {
            RealmResults<Friend> friend = realm.where(Friend.class).findAll();

            if (friend.size() != 0) {
                mView.setLocalFriendData(friend);
            }
        });

    }
    @Override
    public void getFriendList() {
        Disposable disposable = mFriendRepository.getFriendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<FriendList>>() {
                    @Override
                    public void onSuccess(@NonNull BaseResponse<FriendList> response) {
                        Log.d("TEST", "onSuccess getFriendList");
                        mView.updateFriendInfo(response.getMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TEST", "onError getFriendList");
                        mView.noFriendInfo();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void rxUnSubscribe() {
        if (null != mCompositeDisposable) mCompositeDisposable.clear();
    }
}

