package com.playgilround.schedule.client.calendar.util;

import com.playgilround.schedule.client.calendarschedule.CalendarScheduleFragment;
import com.playgilround.schedule.client.chat.ChatFragment;
import com.playgilround.schedule.client.friend.FriendFragment;
import com.playgilround.schedule.client.map.MapFragment;
import com.playgilround.schedule.client.setting.SettingFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FriendFragment tab1 = new FriendFragment();
                return tab1;
            case 1:
                ChatFragment tab2 = new ChatFragment();
                return tab2;
            case 2:
                CalendarScheduleFragment tab3 = new CalendarScheduleFragment();
                return tab3;
            case 3:
                MapFragment tab4 = new MapFragment();
                return tab4;
            case 4:
                SettingFragment tab5 = new SettingFragment();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
