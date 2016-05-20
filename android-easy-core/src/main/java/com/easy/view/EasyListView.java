package com.easy.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easy.util.LogUtil;
import com.easy.util.ScreenUtil;

/**
 * Created by 11397 on 2016/5/19.
 */
public abstract class EasyListView extends ListView implements AbsListView.OnScrollListener, AdapterView
        .OnItemClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener, View
        .OnClickListener {
    public static boolean DEBUG_MODE = true;

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_PULLING_NOT_ENOUGH = 2;
    public static final int STATUS_PULLING_ENOUGH = 3;
    public static final int STATUS_REFRESHING = 4;
    public static final int STATUS_REFRESH_FAIL = 5;
    public static final int STATUS_LOADING = 6;
    public static final int STATUS_LOAD_FAIL = 7;

    public interface OnRefreshListener {
        void onRefreshing();
    }

    public interface OnLoadPageListener {
        void onLoadingPage(int page);
    }

    private int pullEnoughDistance;
    private int page = 0;
    private int status = STATUS_NORMAL;
    private boolean isNoMore;
    private View header;
    private View footer;
    private int originalHeaderHeight;
    private int originalHeaderPaddingTop;

    private OnScrollListener customScrollListener;
    private OnItemClickListener customItemClickListener;
    private OnItemLongClickListener customItemLongClickListener;
    private OnItemSelectedListener customItemSelectedListener;
    private OnRefreshListener onRefreshListener;
    private OnLoadPageListener onLoadPageListener;

    public EasyListView(Context context) {
        super(context);
        init(context);
    }

    public EasyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EasyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        super.setOnScrollListener(this);
        pullEnoughDistance = ScreenUtil.getLongSide(context) / 7;
        initHeader(context);
        initFooter(context);
    }

    private void initHeader(Context context) {
        if (header != null) {
            removeHeaderView(header);
        }

        header = inflateRefreshHeader();
        if (header != null) {
            header.setOnClickListener(this);
            header.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            originalHeaderHeight = header.getMeasuredHeight();
            originalHeaderPaddingTop = header.getPaddingTop();
            addHeaderView(header);
            onRefreshCancel();
        }
    }

    private void initFooter(Context context) {
        if (footer != null) {
            removeFooterView(footer);
        }

        footer = inflateLoadFooter();
        if (footer != null) {
            footer.setOnClickListener(this);
            addFooterView(footer);
            onNoMore();
        }
    }

    /**
     * 改变UI的相关方法，显示部分需要子类实现
     */

    protected abstract View inflateRefreshHeader();

    protected abstract View inflateLoadFooter();

    protected abstract void showPulling(float pullDistance);

    protected abstract void showPullingEnough();

    protected abstract void showPullingNotEnough();

    protected abstract void showRefreshing();

    protected abstract void showRefreshFail();

    protected abstract void showLoadingPage();

    protected abstract void showLoadPageFail();

    protected abstract void showNoMore();

    protected void hideRefreshHeader() {
        modifyHeaderHeight(0);
    }

    protected void hideLoadFooter() {
        if (footer != null) {
            footer.setVisibility(View.GONE);
        }
    }

    /**
     * 内部状态改变的相关方法
     */

    protected void onPulling(float pullDistance) {
        log(pullDistance);
        modifyHeaderHeight((int) pullDistance);
        showPulling(pullDistance);
    }

    protected void onPullingEnough() {
        log("onPullingEnough");
        status = STATUS_PULLING_ENOUGH;
        showPullingEnough();
    }

    protected void onPullingNotEnough() {
        log("onPullingNotEnough");
        status = STATUS_PULLING_NOT_ENOUGH;
        showPullingNotEnough();
    }

    protected void onRefreshCancel() {
        log("onRefreshCancel");
        status = STATUS_NORMAL;
        hideRefreshHeader();
    }

    protected void onRefreshing() {
        log("onRefreshing");
        status = STATUS_REFRESHING;
        modifyHeaderHeight(originalHeaderHeight);
        showRefreshing();
        if (onRefreshListener != null) {
            onRefreshListener.onRefreshing();
        }
    }

    protected void onRefreshSuccess() {
        log("onRefreshSuccess");
        status = STATUS_NORMAL;
        isNoMore = false;
        hideRefreshHeader();
        page = 0;
    }

    protected void onRefreshFail() {
        log("onRefreshFail");
        status = STATUS_REFRESH_FAIL;
        modifyHeaderHeight(originalHeaderHeight);
        showRefreshFail();
    }

    protected void onLoadCancel() {
        log("onLoadCancel");
        status = STATUS_NORMAL;
        hideLoadFooter();
    }

    protected void onLoadingPage() {
        log("onLoadingPage");
        status = STATUS_LOADING;
        if (footer != null) {
            footer.setVisibility(View.VISIBLE);
        }
        showLoadingPage();
        if (onLoadPageListener != null) {
            onLoadPageListener.onLoadingPage(page);
        }
    }

    protected void onLoadPageSuccess() {
        log("onLoadPageSuccess");
        status = STATUS_NORMAL;
        hideLoadFooter();
        page++;
    }

    protected void onLoadPageFail() {
        log("onLoadPageFail");
        status = STATUS_LOAD_FAIL;
        if (footer != null) {
            footer.setVisibility(View.VISIBLE);
        }
        showLoadPageFail();
    }

    protected void onNoMore() {
        log("onNoMore");
        isNoMore = true;
        if (footer != null) {
            footer.setVisibility(View.VISIBLE);
        }
        showNoMore();
    }

    /**
     * 外部调用改变状态的相关方法
     */
    public void refreshing() {
        onRefreshing();
    }

    public void refreshSuccess() {
        onRefreshSuccess();
    }

    public void refreshFail() {
        onRefreshFail();
    }

    public void loadingPage() {
        onLoadingPage();
    }

    public void loadPageSuccess() {
        onLoadPageSuccess();
    }

    public void loadPageFail() {
        onLoadPageFail();
    }

    public void noMore() {
        onNoMore();
    }

    @Override
    public void onClick(View v) {
        if (v == header) {
            if (status == STATUS_REFRESH_FAIL) {
                onRefreshing();
            }
        } else if (v == footer) {
            if (status == STATUS_LOAD_FAIL) {
                onLoadingPage();
            }
        }
    }

    /**
     * 通过touch事件控制头部状态
     */
    private float startY = -1;
    private int startHeaderHeight = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //            log("getAction(): " + event.getAction());
        //            log("startY: " + startY);
        //            log("event.getY(): " + event.getY());
        if (getFirstVisiblePosition() == 0 && status != STATUS_REFRESHING) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    if (startY < 0) {
                        startY = ev.getY();
                        if (header != null) {
                            startHeaderHeight = header.getHeight();
                        }
                    } else {
                        float pullDistance = ev.getY() - startY + startHeaderHeight;
                        if (pullDistance > 0) {
                            onPulling(pullDistance);
                            if (pullDistance < pullEnoughDistance) {
                                if (status != STATUS_PULLING_NOT_ENOUGH) {
                                    onPullingNotEnough();
                                }
                            } else {
                                if (status != STATUS_PULLING_ENOUGH) {
                                    onPullingEnough();
                                }
                            }
                            if (pullDistance > 10) {
                                ev.setAction(MotionEvent.ACTION_CANCEL);
                                super.onTouchEvent(ev);
                            }
                            return true;
                        } else {
                            onRefreshCancel();
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    float pullDistance = ev.getY() - startY + startHeaderHeight;
                    if (pullDistance < pullEnoughDistance) {
                        onRefreshCancel();
                    } else {
                        onRefreshing();
                    }
                    break;
                default:
                    break;
            }
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            startY = -1;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 通过滚动事件控制脚部状态
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (customScrollListener != null) {
            customScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int firstFooterItem = footer == null ? -1 : totalItemCount - getFooterViewsCount();
        int lastVisibleItem = firstVisibleItem + visibleItemCount - 1;

        if (footer != null) {
            if (lastVisibleItem >= firstFooterItem) {
                if (status == STATUS_NORMAL && !isNoMore) {
                    onLoadingPage();
                }
            } else {
                if (status == STATUS_LOAD_FAIL) {
                    onLoadCancel();
                }
            }
        }

        if (customScrollListener != null) {
            int visibleHeaderCount = getHeaderViewsCount() - firstVisibleItem;
            if (visibleHeaderCount < 0) visibleHeaderCount = 0;
            int visibleFooterCount = lastVisibleItem - firstFooterItem + 1;
            if (visibleFooterCount < 0) visibleFooterCount = 0;

            firstVisibleItem -= getHeaderViewsCount();
            if (firstVisibleItem < 0) firstVisibleItem = 0;
            visibleItemCount = visibleItemCount - visibleHeaderCount - visibleFooterCount;
            if (visibleItemCount < 0) visibleItemCount = 0;
            totalItemCount = totalItemCount - getHeaderViewsCount() - getFooterViewsCount();

            customScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (customItemClickListener != null && getAdapter() != null) {
            position -= getHeaderViewsCount();
            if (position >= 0 && position < getCount() - getHeaderViewsCount() - getFooterViewsCount())
                customItemClickListener.onItemClick(parent, view, position, getItemIdAtPosition(position));
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (customItemLongClickListener != null && getAdapter() != null) {
            position -= getHeaderViewsCount();
            if (position >= 0 && position < getCount() - getHeaderViewsCount() - getFooterViewsCount())
                return customItemLongClickListener.onItemLongClick(parent, view, position, getItemIdAtPosition
                        (position));
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (customItemSelectedListener != null && getAdapter() != null) {
            position -= getHeaderViewsCount();
            if (position >= 0 && position < getCount() - getHeaderViewsCount() - getFooterViewsCount())
                customItemSelectedListener.onItemSelected(parent, view, position, getItemIdAtPosition(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (customItemSelectedListener != null) {
            customItemSelectedListener.onNothingSelected(parent);
        }
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        this.customScrollListener = l;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(this);
        customItemClickListener = listener;
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        super.setOnItemLongClickListener(this);
        customItemLongClickListener = listener;
    }

    @Override
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(this);
        customItemSelectedListener = listener;
    }

    private void modifyHeaderHeight(int newHeight) {
        if (header == null) return;
        int paddingTop = newHeight - originalHeaderHeight + originalHeaderPaddingTop;
        header.setPadding(header.getPaddingLeft(), paddingTop, header.getPaddingRight(), header.getPaddingBottom());
    }

    public int getPullEnoughDistance() {
        return pullEnoughDistance;
    }

    public void setPullEnoughDistance(int pullEnoughDistance) {
        this.pullEnoughDistance = pullEnoughDistance;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStatus() {
        return status;
    }

    public boolean isNoMore() {
        return isNoMore;
    }

    public OnRefreshListener getOnRefreshListener() {
        return onRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public OnLoadPageListener getOnLoadPageListener() {
        return onLoadPageListener;
    }

    public void setOnLoadPageListener(OnLoadPageListener onLoadPageListener) {
        this.onLoadPageListener = onLoadPageListener;
    }

    private void log(Object obj) {
        if (DEBUG_MODE) {
            LogUtil.wtf(obj);
        }
    }
}
