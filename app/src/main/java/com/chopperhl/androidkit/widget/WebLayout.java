package com.chopperhl.androidkit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import com.chopperhl.androidkit.R;
import com.just.agentweb.IWebLayout;

/**
 * Description:
 * Author chopperhl
 * Date 9/11/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class WebLayout implements IWebLayout {
    private final LinearLayout mContent;
    private WebView mWebView;

    @SuppressLint("InflateParams")
    public WebLayout(Context context) {
        mContent = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.web_layout, null, false);
        mWebView = mContent.findViewById(R.id.webView);
    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mContent;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }
}
