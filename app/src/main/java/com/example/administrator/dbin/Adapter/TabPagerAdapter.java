package com.example.administrator.dbin.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.dbin.Fragment.DataFragment;
import com.example.administrator.dbin.Fragment.RouteFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter{

    CharSequence charSequence[];

    public TabPagerAdapter(FragmentManager fm, CharSequence[] charSequence_) {
        super(fm);
        this.charSequence = charSequence_;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1)
            return new RouteFragment();
        else
            return new DataFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return charSequence[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
