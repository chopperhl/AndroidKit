package com.chopperhl.androidkit.base;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import butterknife.BindView;
import com.chopperhl.androidkit.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

/**
 * Description: 继承该类 已快速完成列表页面的编写
 * Author chopperhl
 * Date 6/4/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public abstract class BaseListActivity<D, P extends BaseListPresenter> extends BaseActivity<P>
        implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnMoreListener {

    @BindView(R.id.recyclerView)
    public EasyRecyclerView mRecyclerView;

    public RecyclerArrayAdapter<D> mAdapter;

    public int mPage = 1;

    @Override
    public int bindLayout() {
        return R.layout.activity_base_list;
    }

    @Override
    public void initView() {
        mRecyclerView.setAdapter(mAdapter = new RecyclerArrayAdapter<D>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return BaseListActivity.this.createViewHolder(parent, viewType);
            }

            @Override
            public int getViewType(int position) {
                return getListViewType(position);
            }
        });
        configRecyclerView();
        if (mPresenter != null) mPresenter.setUsePager(usePager());
        if (usePager()) mAdapter.setMore(R.layout.view_more, this);
    }


    /**
     * 类似  {@link RecyclerArrayAdapter#getViewType(int)}
     *
     * @param position
     * @return
     */
    protected int getListViewType(int position) {
        return 0;
    }

    /**
     * {@link EasyRecyclerView}的基础设置
     *
     * 可在子类中修改
     */
    protected void configRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration decoration = new DividerDecoration(getColorRes(R.color.color_FAFAFA), 2);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setRefreshListener(this);
    }

    /**
     * 是否使用分页封装
     *
     * @return
     */
    protected boolean usePager() {
        return false;
    }

    /**
     * 子类中复写该方法
     *
     * 并在{@link BaseViewHolder} 中处理数据显示
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BaseViewHolder createViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onMoreClick() {
        //暂不做处理
    }

    /**
     * 在调用{@link RecyclerArrayAdapter#addAll(Object[])} 方法之后 根据返回数据 判断是否需要加载分页
     *
     * 不需要的情况调用 {@link RecyclerArrayAdapter#stopMore()} 避免继续触发该方法
     */
    @Override
    public void onMoreShow() {
        if (mPresenter == null) return;
        mPage++;
        mPresenter.requestApi(mPage);
    }

    @Override
    public void onRefresh() {
        if (mPresenter == null) return;
        mPage = 1;
        mPresenter.requestApi(mPage);
    }

    @Override
    public void showRefreshView() {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void dismissRefreshView() {
        mRecyclerView.setRefreshing(false);
    }


    @Override
    public void dealError(Throwable t) {
        super.dealError(t);
        if (mPage == 1) mRecyclerView.showError();
        if (usePager()) mAdapter.stopMore();
    }

    @Override
    public void clearList() {
        mAdapter.clear();
    }
}
