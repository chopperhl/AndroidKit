package com.chopperhl.androidkit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import com.chopperhl.androidkit.util.DeviceHelper;
import com.chopperhl.androidkit.util.Util;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * Description:
 * Author chopperhl
 * Date 11/26/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
@SuppressLint("ViewConstructor")
public class SimpleTabNavigator extends CommonNavigator {

    public SimpleTabNavigator(ViewPager mViewPager, String[] tabTitles) {
        super(mViewPager.getContext());
        setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
                titleView.setPadding(Util.dp2px(5), 0, Util.dp2px(5), 0);
                titleView.setNormalColor(Color.GRAY);
                titleView.setSelectedColor(Color.BLACK);
                titleView.setText(tabTitles[index]);
                titleView.setWidth((DeviceHelper.getScreenWidth() / tabTitles.length));
                titleView.setOnClickListener(view -> mViewPager.setCurrentItem(index));
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(Util.dp2px(2.0f));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
    }


}
