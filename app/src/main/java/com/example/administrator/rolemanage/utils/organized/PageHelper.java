package com.example.administrator.rolemanage.utils.organized;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 参考 https://github.com/Hankkin/PageLayoutDemo
 * by zna
 * <pre>
 *      PageHelper pageHelper = new PageHelper.Builder(this, llParent, tvComment,this)
 *      .setNetWorkErrorLayout(R.layout.view_failure,R.id.btnRefreshNetwork)
 *      .setNoDataLayout(R.layout.view_none_info,R.id.btnRefreshNoData)
 *      .create();
 *
 *      pageHelper.show(PageHelper.NET_WORK_ERROR_VIEW);
 * </pre>
 */

public class PageHelper {

    public static final int CONTENT_VIEW = 1;
    public static final int ERROR_VIEW = 2;
    public static final int NO_DATA_VIEW = 3;
    public static final int NET_WORK_ERROR_VIEW = 4;

    private View.OnClickListener mOnClickListener;
    private View mContentView, mErrorView, mNoDataView, mNetWorkErrorView;
    private ViewGroup mParentView;
    private Context mContext;
    private int mErrorLayoutId, mNoDataLayoutId, mNetWorkErrorLayoutId;
    private int[] mErrorViewClickIds, mNoDataViewClickIds, mNetWorkErrorViewClickIds;


    protected PageHelper(Context context) {
        mContext = context;
    }


    public void setOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }


    public void setParentView(ViewGroup parentView) {
        this.mParentView = parentView;
    }

    public void setContentView(View mContentView) {
        this.mContentView = mContentView;
    }

    public View getErrorView() {
        return mErrorView;
    }

    public View getNoDataView() {
        return mNoDataView;
    }

    public View getNetWorkErrorView() {
        return mNetWorkErrorView;
    }

    public void setErrorLayout(@LayoutRes int errorLayoutId, int... clickIds) {
        mErrorLayoutId = errorLayoutId;
        mErrorViewClickIds = clickIds;
    }

    public void setNoDataLayout(@LayoutRes int noDataLayoutId, int... clickIds) {
        mNoDataLayoutId = noDataLayoutId;
        mNoDataViewClickIds = clickIds;
    }

    public void setNetWorkErrorLayout(@LayoutRes int netWorkErrorLayoutId, int... clickIds) {
        mNetWorkErrorLayoutId = netWorkErrorLayoutId;
        mNetWorkErrorViewClickIds = clickIds;
    }

    public void show(int viewType) {

        //第一次show的时候加载view
        switch (viewType) {
            case CONTENT_VIEW:
                break;
            case ERROR_VIEW:
                if (mErrorLayoutId != 0 && mErrorView == null) {
                    mErrorView = LayoutInflater.from(mContext).inflate(mErrorLayoutId, null);
                    addView(mErrorView, mErrorViewClickIds);
                }
                break;
            case NO_DATA_VIEW:
                if (mNoDataLayoutId != 0 && mNoDataView == null) {
                    mNoDataView = LayoutInflater.from(mContext).inflate(mNoDataLayoutId, null);
                    addView(mNoDataView, mNoDataViewClickIds);
                }
                break;
            case NET_WORK_ERROR_VIEW:
                if (mNetWorkErrorLayoutId != 0 && mNetWorkErrorView == null) {
                    mNetWorkErrorView = LayoutInflater.from(mContext).inflate(mNetWorkErrorLayoutId, null);
                    addView(mNetWorkErrorView, mNetWorkErrorViewClickIds);
                }
                break;
        }

        //显示view
        mContentView.setVisibility(viewType == CONTENT_VIEW ? View.VISIBLE : View.GONE);
        if (mErrorView != null) {
            mErrorView.setVisibility(viewType == ERROR_VIEW ? View.VISIBLE : View.GONE);
        }
        if (mNoDataView != null) {
            mNoDataView.setVisibility(viewType == NO_DATA_VIEW ? View.VISIBLE : View.GONE);
        }
        if (mNetWorkErrorView != null) {
            mNetWorkErrorView.setVisibility(viewType == NET_WORK_ERROR_VIEW ? View.VISIBLE : View.GONE);
        }

    }


    private void setAllGone() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
        if (mNoDataView != null) {
            mNoDataView.setVisibility(View.GONE);
        }
        if (mNetWorkErrorView != null) {
            mNetWorkErrorView.setVisibility(View.GONE);
        }
        if (mContentView != null) {
            mContentView.setVisibility(View.GONE);
        }
    }

    private void addView(View view, int... clickIds) {
        mParentView.addView(view);
        view.setVisibility(View.GONE);
        setClickIds(view, clickIds);
    }

    private void setClickIds(View view, int... clickIds) {
        //setOnClickListener。点击事件在Activity处理，这里只findViewById。
        for (int clickId : clickIds) {
            View childView = view.findViewById(clickId);
            if (childView == null) {
                continue;
            }
            if (mOnClickListener != null) {
                childView.setOnClickListener(mOnClickListener);
            }
        }
    }

    public static class Builder {
        private Context mContext;
        private View.OnClickListener mOnClickListener;
        private View mContentView;
        private int mErrorLayoutId, mNoDataLayoutId, mNetWorkErrorLayoutId;
        private ViewGroup mParentView;
        private int[] mErrorViewClickIds, mNoDataViewClickIds, mNetWorkErrorViewClickIds;

        public Builder(Context context, ViewGroup parentView, View contentView, View.OnClickListener onClickListener) {
            mContext = context;
            mParentView = parentView;
            mContentView = contentView;
            mOnClickListener = onClickListener;
            if (parentView == null || mContentView == null) {
                throw new NullPointerException("NullPointerException");
            }
        }

        private void initDefault() {

        }

        public Builder setOnClickListener() {
            return this;
        }


        public Builder setErrorLayout(@LayoutRes int errorLayoutId, int... clickIds) {
            mErrorLayoutId = errorLayoutId;
            mErrorViewClickIds = clickIds;
            return this;
        }

        public Builder setNoDataLayout(@LayoutRes int noDataLayoutId, int... clickIds) {
            mNoDataLayoutId = noDataLayoutId;
            mNoDataViewClickIds = clickIds;
            return this;
        }

        public Builder setNetWorkErrorLayout(@LayoutRes int netWorkErrorLayoutId, int... clickIds) {
            mNetWorkErrorLayoutId = netWorkErrorLayoutId;
            mNetWorkErrorViewClickIds = clickIds;
            return this;
        }


        public PageHelper create() {
            PageHelper pageHelper = new PageHelper(mContext);
            pageHelper.setOnClickListener(mOnClickListener);
            pageHelper.setParentView(mParentView);
            pageHelper.setContentView(mContentView);
            pageHelper.setErrorLayout(mErrorLayoutId, mErrorViewClickIds);
            pageHelper.setNetWorkErrorLayout(mNetWorkErrorLayoutId, mNetWorkErrorViewClickIds);
            pageHelper.setNoDataLayout(mNoDataLayoutId, mNoDataViewClickIds);
            return pageHelper;
        }

    }


}
