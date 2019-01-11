package com.chopperhl.androidkit.base;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import butterknife.BindView;

import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.widget.WebLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

/**
 * Description:
 * Author chopperhl
 * Date 9/11/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class BaseWebActivity<P extends BasePresenter> extends BaseActivity<P> {
    @BindView(R.id.content)
    LinearLayout mContent;
    public AgentWeb mAgentWeb;

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_base_web;
    }

    @Override
    public void initView() {
        WebLayout webLayout;
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(webLayout = new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go("about:blank");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                && webLayout.getWebView() != null) {
            webLayout.getWebView().getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
    }

    @Override
    public void doBusiness() {

    }

    /**
     * 子类复写该方法，已实现更多需求
     *
     * @return
     */
    protected WebViewClient getWebViewClient() {
        return new WebViewClient();
    }

    /**
     * 子类复写该方法，已实现更多需求
     *
     * @return
     */

    protected WebChromeClient getWebChromeClient() {
        return new WebChromeClient();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.destroy();
        }
    }
}
