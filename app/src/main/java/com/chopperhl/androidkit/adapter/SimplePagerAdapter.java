package com.chopperhl.androidkit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.entity.BasePagerEntity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author chopperhl
 * Date 9/6/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class SimplePagerAdapter<T extends BasePagerEntity> extends LoopPagerAdapter {
    private List<T> pagerList = new ArrayList<>();

    public SimplePagerAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    public void setPagerData(List<T> pagerList) {
        this.pagerList = pagerList;
        notifyDataSetChanged();
    }


    public T getItem(int pos) {
        return pagerList.get(pos);
    }

    @Override
    public View getView(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_pager_container, container, false);
        ImageView imageView = view.findViewById(R.id.iv_banner);
        String url = pagerList.get(position).getImageUrl();
        Glide.with(container.getContext()).load(url).into(imageView);
        return view;
    }


    @Override
    public int getRealCount() {
        return pagerList.size();
    }
}
