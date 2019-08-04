package com.playgilround.schedule.client.main;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.calendarschedule.CalendarScheduleFragment;
import com.playgilround.schedule.client.chat.ChatFragment;
import com.playgilround.schedule.client.friend.FriendFragment;
import com.playgilround.schedule.client.main.view.MainViewPagerAdapter;
import com.playgilround.schedule.client.map.MapFragment;
import com.playgilround.schedule.client.setting.SettingFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.view_pager_main)
    ViewPager mViewPager;

    @BindView(R.id.nav_main)
    TabLayout mTabLayout;

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new MainPresenter(this);

        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(R.drawable.image_nav_bar_friend, new FriendFragment());
        pagerAdapter.addFragment(R.drawable.image_nav_bar_chat, new ChatFragment());
        pagerAdapter.addFragment(R.drawable.image_nav_bar_calendar, new CalendarScheduleFragment());
        pagerAdapter.addFragment(R.drawable.image_nav_bar_map, new MapFragment());
        pagerAdapter.addFragment(R.drawable.image_nav_bar_setting, new SettingFragment());
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        for (int i =0; i< mViewPager.getAdapter().getCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(pagerAdapter.getMImageList().get(i));
        }
    }
    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

