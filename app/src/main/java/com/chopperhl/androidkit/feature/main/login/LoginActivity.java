package com.chopperhl.androidkit.feature.main.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.base.BaseActivity;
import com.chopperhl.androidkit.base.Presenter;
import com.chopperhl.androidkit.common.Routers;
import com.chopperhl.androidkit.util.Util;


/**
 * Description:
 * Author chopperhl
 * Date 1/11/19
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
@Route(path = Routers.LOGIN)
@Presenter(LoginPresenter.class)
public class LoginActivity extends BaseActivity<LoginPresenter> {

    @BindView(R.id.btn)
    Button mBtnLogin;

    @Override
    public void initParams(Bundle params) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        setTitleText("登录");
        Util.setOnClickListener(v -> {
            ARouter.getInstance().build(Routers.MAIN)
                    .withString(KEY_PARAMS_1, "Hello Chopperhl")
                    .navigation();
            finish();
        }, mBtnLogin);
    }

    @Override
    public void doBusiness() {
    }
}
