package com.playgilround.schedule.client.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.playgilround.schedule.client.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();

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


        setUpViewPager();

        mTabLayout.setupWithViewPager(mViewPager);
        setUpTabLayout();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void setUpTabLayout() {
        ViewGroup.LayoutParams layoutParams = new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View friendView = LayoutInflater.from(this).inflate(R.layout.item_tab_layout, null);
        friendView.findViewById(R.id.image_tab).setBackgroundResource(R.drawable.image_nav_bar_friend);
        friendView.setLayoutParams(layoutParams);
        mTabLayout.getTabAt(0).setCustomView(friendView);

        View chatView = LayoutInflater.from(this).inflate(R.layout.item_tab_layout, null);
        chatView.findViewById(R.id.image_tab).setBackgroundResource(R.drawable.image_nav_bar_chat);
        chatView.setLayoutParams(layoutParams);
        mTabLayout.getTabAt(1).setCustomView(chatView);

        View calendarView = LayoutInflater.from(this).inflate(R.layout.item_tab_layout, null);
        calendarView.findViewById(R.id.image_tab).setBackgroundResource(R.drawable.image_nav_bar_calendar);
        calendarView.setLayoutParams(layoutParams);
        mTabLayout.getTabAt(2).setCustomView(calendarView);

        View mapView = LayoutInflater.from(this).inflate(R.layout.item_tab_layout, null);
        mapView.findViewById(R.id.image_tab).setBackgroundResource(R.drawable.image_nav_bar_map);
        mapView.setLayoutParams(layoutParams);
        mTabLayout.getTabAt(3).setCustomView(mapView);

        View settingView = LayoutInflater.from(this).inflate(R.layout.item_tab_layout, null);
        settingView.findViewById(R.id.image_tab).setBackgroundResource(R.drawable.image_nav_bar_setting);
        settingView.setLayoutParams(layoutParams);
        mTabLayout.getTabAt(4).setCustomView(settingView);
    }

    private void setUpViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Todo:: Add Fragment.
        adapter.addFragment(new SampleFragment());
        adapter.addFragment(new SampleFragment());
        adapter.addFragment(new SampleFragment());
        adapter.addFragment(new SampleFragment());
        adapter.addFragment(new SampleFragment());
        mViewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList = new ArrayList<>();

        ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        public void addFragment(Fragment fragment) {
            mList.add(fragment);
        }
    }
}

