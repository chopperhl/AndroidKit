package com.chopperhl.androidkit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author chopperhl
 * Date 6/15/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class TabHostAdapter extends FragmentStatePagerAdapter {
    private List<? extends Fragment> mTaskFragments;
    private String[] mTabTitles;

    public TabHostAdapter(FragmentManager fm, List<? extends Fragment> fragments, String[] tabTitles) {
        super(fm);
        this.mTaskFragments = (fragments == null ? new ArrayList<>() : fragments);
        this.mTabTitles = (tabTitles == null ? new String[0] : tabTitles);
    }

    @Override
    public Fragment getItem(int position) {
        return mTaskFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTaskFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
