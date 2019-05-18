package com.playgilround.schedule.client.tutorial;

import android.content.Context;

import com.playgilround.schedule.client.data.repository.UsersRepository;

/**
 * 19-02-20
 * Tutorial 관련 데이터 처리 Presenter
 */
public class TutorialPresenter implements TutorialContract.Presenter {

    private final Context mContext;
    private final TutorialContract.View mView;
    private final UsersRepository mUsersRepository;

    TutorialPresenter(Context context, TutorialContract.View view, UsersRepository usersRepository) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
        mUsersRepository = usersRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void checkLogin() {
        if (mUsersRepository.getCurrentUser(mContext) != null) {
            mView.skipTutorial();
        }
    }
}
