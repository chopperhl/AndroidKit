package com.chopperhl.androidkit.feature.main.home;

import android.os.Bundle;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.base.BaseFragment;
/**
 * Description:
 * Author chopperhl
 * Date 1/11/19
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class HomeFragment extends BaseFragment {


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initParams(Bundle params) {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void doBusiness() {

    }

}
