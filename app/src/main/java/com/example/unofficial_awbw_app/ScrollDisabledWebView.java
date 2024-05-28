package com.example.unofficial_awbw_app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;

public class ScrollDisabledWebView extends WebView {

    private boolean scrollEnabled = true;

    public ScrollDisabledWebView(Context context) {
        super(context);
        initView(context);
    }

    public ScrollDisabledWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }
    // this is important. Otherwise it throws Binary Inflate Exception.
    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {
        if (scrollEnabled) {
            return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                    scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        }
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (scrollEnabled) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (scrollEnabled) {
            super.computeScroll();
        }
    }

    public void setScrollEnabled(boolean b){
        scrollEnabled = b;
    }

    public boolean getScrollEnabled(){
        return scrollEnabled;
    }
}