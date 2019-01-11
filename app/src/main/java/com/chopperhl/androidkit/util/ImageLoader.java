package com.chopperhl.androidkit.util;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chopperhl.androidkit.R;



/**
 * Description: 统一的图片加载管理
 * Author chopperhl
 * Date 7/3/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class ImageLoader {
    private ImageLoader() {
    }

    private static ImageLoader instance;

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) instance = new ImageLoader();
            }
        }
        return instance;
    }


    /**
     * 加载圆图
     *
     * @param imageView
     * @param url
     * @param placeHolder
     */
    public void loadCircleCrop(ImageView imageView, String url, int placeHolder) {
        Bitmap bmp = BitmapUtil.circleCrop(placeHolder);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.overrideOf(imageView.getWidth(), imageView.getMaxHeight()))
                .apply(RequestOptions.placeholderOf(BitmapUtil.bitmap2Drawable(bmp)))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }




    /**
     * 剪切缩放加载
     * 可设置占位图
     *
     * @param imageView
     * @param uri
     */
    public void loadNormal(ImageView imageView, String uri, int placeHolder) {
        Glide.with(imageView.getContext())
                .load(uri)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.placeholderOf(placeHolder))
                .into(imageView);
    }

    /**
     * 剪切缩放加载
     *
     * @param imageView
     * @param uri
     */
    public void loadNormal(ImageView imageView, String uri) {
        loadNormal(imageView, uri, R.drawable.pic_none);
    }


    /**
     * 加载本地gif
     *
     * @param imageView
     * @param res
     */
    public void loadGifLocal(ImageView imageView, int res) {
        Glide.with(imageView.getContext())
                .asGif()
                .load(res)
                .into(imageView);
    }
}

