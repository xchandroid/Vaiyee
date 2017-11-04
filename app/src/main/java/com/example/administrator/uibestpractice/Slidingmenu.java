package com.example.administrator.uibestpractice;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Slidingmenu extends HorizontalScrollView {
    private LinearLayout mLayout = null;
    private ViewGroup mMenu = null;
    private ViewGroup mContent = null;
    private int mMenuWidth ;

    private int mScreenWidth ;
    private int mMenuMarginRight ;
    public Slidingmenu(Context context, AttributeSet attributeSet ){
        super(context,attributeSet);
        WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth=metrics.widthPixels;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLayout = (LinearLayout) getChildAt(0);
        mMenu = (ViewGroup) mLayout.getChildAt(0);
        mContent = (ViewGroup) mLayout.getChildAt(1);

        mMenuMarginRight = mScreenWidth / 100 * 25;
        mMenuWidth = mScreenWidth - mMenuMarginRight;
        mMenu.getLayoutParams().width = mMenuWidth;
        mContent.getLayoutParams().width = mScreenWidth;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            //隐藏菜单
            this.scrollTo(mMenuWidth,0);

        }
    }
   boolean isopen;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action)
        {
            // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
           float scrollX = getScrollX();
                if (scrollX > mMenuWidth/2) {
                    this.smoothScrollTo(mMenuWidth,0);
                    isopen = true;
                }
                else{
                    this.smoothScrollTo(0,0);
                    isopen=false;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l *1.0f/ mMenuWidth;
        float leftScale = 1 - 0.2f * scale;//0.8
        float rightScale = 0.8f + scale * 0.2f;


        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, 0.2f + 0.8f * (1 - scale));

        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale);

       /*
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() /2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);*/


        /*//菜单显示的动态比例范围
        float scale=l/mMenuWidth;//其实值就是0-1的值
        //菜单显示占屏幕70%
        float leftscale = 1 - 0.3f*scale;
        float rightscale=1-0.2f*scale;
        ViewHelper.setScaleX(mMenu,leftscale);
        ViewHelper.setScaleY(mMenu,leftscale);
        ViewHelper.setAlpha(mMenu,1.0f*(1-scale));
        ViewHelper.setTranslationX(mMenu,mMenuWidth*scale*0.7f);

        ViewHelper.setScaleX(mContent,rightscale);
        ViewHelper.setScaleX(mContent,rightscale);
        ViewHelper.setPivotY(mContent,mContent.getHeight()/2);
        ViewHelper.setPivotX(mContent,0);*/
    }
    public void openMenu()
    {
            this.smoothScrollTo(mMenuWidth,0);
            isopen = true;

    }

    /**
     * 关闭菜单
     */
    public void closeMenu()
    {
            this.smoothScrollTo(0,0);
            isopen = false;

    }

    /**
     * 切换菜单状态
     */
    public void toggle()
    {
        if (isopen)
        {
            closeMenu();
        } else
        {
            openMenu();
        }
    }
}