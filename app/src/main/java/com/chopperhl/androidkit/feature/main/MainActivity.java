package com.chopperhl.androidkit.feature.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.base.BaseActivity;
import com.chopperhl.androidkit.common.ActivityManager;
import com.chopperhl.androidkit.common.Routers;
import com.chopperhl.androidkit.feature.main.home.HomeFragment;
import com.chopperhl.androidkit.util.NotificationUtil;
import com.chopperhl.androidkit.util.update.UpdateHelper;

/**
 * Description:
 * Author chopperhl
 * Date 1/11/19
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
@Route(path = Routers.MAIN)
public class MainActivity extends BaseActivity {
    @BindView(R.id.navigation)
    TabLayout mTabLayout;
    @Autowired(name = KEY_PARAMS_1)
    String hello;
    private HomeFragment mFragment1;
    private HomeFragment mFragment2;
    private HomeFragment mFragment3;

    private long exitTime;

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setTitleText(R.string.tab_home);
        if (NotificationUtil.isNotificationEnabled()) {
            UpdateHelper.INSTANCE.checkUpdate(false);
        } else {
            NotificationUtil.ask2SetNotification(this, (dialog, witch) -> UpdateHelper.INSTANCE.checkUpdate(false));
        }
        mFragment1 = HomeFragment.newInstance();
        mFragment2 = HomeFragment.newInstance();
        mFragment3 = HomeFragment.newInstance();

        TabLayout.Tab tab1 = mTabLayout.newTab().setCustomView(R.layout.item_tab_message);
        TabLayout.Tab tab2 = mTabLayout.newTab().setCustomView(R.layout.item_tab_home);
        TabLayout.Tab tab3 = mTabLayout.newTab().setCustomView(R.layout.item_tab_mine);

        mTabLayout.addTab(tab1);
        mTabLayout.addTab(tab2);
        mTabLayout.addTab(tab3);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                hideAllFragment();
                if (tab == tab1) showFragment(mFragment1);
                if (tab == tab2) showFragment(mFragment2);
                if (tab == tab3) showFragment(mFragment3);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        hideAllFragment();
        showFragment(mFragment1);
        tab1.select();
    }

    @Override
    public void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            addFragment(R.id.content, fragment);
        }
        super.showFragment(fragment);
    }


    private void hideAllFragment() {
        if (mFragment1 != null && mFragment1.isAdded()) hideFragment(mFragment1);
        if (mFragment2 != null && mFragment2.isAdded()) hideFragment(mFragment2);
        if (mFragment3 != null && mFragment3.isAdded()) hideFragment(mFragment3);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(R.string.double_click_to_exit);
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManager.INSTANCE.finishAll();
        }
    }

    @Override
    public void doBusiness() {

    }
}
