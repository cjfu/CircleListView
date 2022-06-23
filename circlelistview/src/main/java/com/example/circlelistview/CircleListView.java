package com.example.circlelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CircleListView extends ViewGroup {

    protected static final double intervalAngel = 22.5;//子view之间的间隔角

    int circleR;//圆的半径
    int ccx;//圆心的x轴坐标
    int ccy;//圆心的y轴坐标
    double angel = 0;//偏移角度
    private float oldTouchY;//上一次触摸的y轴位置
    private boolean isScrolling = false;//是否在滑动状态
    private Bitmap circleBitmap = null;
    private Rect src;
    private Rect dst;
    Paint paint;

    CallBack callBack;


    Adapter adapter = new Adapter() {
        @Override
        public View getView(int position) {
            return new View(getContext());
        }
    };

    public CircleListView(Context context) {
        super(context);
        setWillNotDraw(false);
        paint = new Paint();
    }

    public CircleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        paint = new Paint();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleListView);
        setCircleBitMap(ta.getResourceId(R.styleable.CircleListView_circleDrawable, 0));
        ta.recycle();
    }

    private void setCircleBitMap(int drawableId) {
        if (drawableId != 0) {
            circleBitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        } else {
            circleBitmap = null;
        }
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        callBack = new CallBack(this);
        this.adapter.setCallBack(callBack);
        callBack.refreshList();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (circleBitmap != null) {
            if (src == null) {
                src = new Rect();
            }
            src.left = 0;
            src.top = 0;
            src.right = circleBitmap.getWidth();
            src.bottom = circleBitmap.getHeight();
            if (dst == null) {
                dst = new Rect();
            }
            dst.left = ccx - circleR;
            dst.top = ccy - circleR;
            dst.right = ccx + circleR;
            dst.bottom = ccy + circleR;
            canvas.drawBitmap(circleBitmap, src, dst, paint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        circleR = (getRight() - getLeft()) / 10 * 9;
        ccy = (int) (getHeight() * 0.45);
        ccx = -getWidth() / 5;
        for (int i = 0; i < adapter.getCount(); i++) {
            View childView = getChildAt(i);
            double childViewAngel = i * intervalAngel + angel + 90;
            if (childViewAngel > 270 || childViewAngel < -90) {
                continue;
            }
            int x = ccx + (int) (Math.sin(Math.toRadians(childViewAngel)) * circleR);
            int y = ccy - (int) (Math.cos(Math.toRadians(childViewAngel)) * circleR);
            int vl = x - childView.getMeasuredWidth() / 2;
            int vt = y - childView.getMeasuredHeight() / 2;
            int vr = x + childView.getMeasuredWidth() / 2;
            int vb = y + childView.getMeasuredHeight() / 2;
            childView.layout(vl, vt, vr + 100, vb + 100);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldTouchY = ev.getY();
                super.dispatchTouchEvent(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isScrolling && Math.abs(oldTouchY - ev.getY()) > 50) {
                    isScrolling = true;
                    float offSetY = 0;
                    oldTouchY = ev.getY();
                    angel += offSetY / 20;
                    requestLayout();
                    return true;
                } else if (isScrolling) {
                    float offSetY = ev.getY() - oldTouchY;
                    oldTouchY = ev.getY();
                    if ((angel + offSetY / 20) < ((adapter.getCount() - 1) * -intervalAngel)) {
                        angel = (adapter.getCount() - 1) * -intervalAngel;
                    } else if ((angel + offSetY / 20) > 0) {
                        angel = 0;
                    } else {
                        angel += offSetY / 20;
                    }
                    requestLayout();
                    return true;
                }
                return super.dispatchTouchEvent(ev);
            case MotionEvent.ACTION_UP:
                boolean notDispatch = isScrolling;
                isScrolling = false;
                if (notDispatch) {
                    return false;
                } else {
                    performClick();
                    return super.dispatchTouchEvent(ev);
                }
            default:
                isScrolling = false;
                return super.dispatchTouchEvent(ev);
        }
    }

//    protected void refreshList() {
//        removeAllViews();
//        for (int i = 0; i < adapter.getCount(); i++) {
//            if (i == 0 && angel < -intervalAngel * (adapter.getCount() - 1)) {
//                angel = -intervalAngel * (adapter.getCount() - 1);
//            }
//            addView(adapter.getView(i));
//            if (adapter.getCount() == 1) {
//                setPosition(0);
//            }
//        }
//        invalidate();
//    }
//
//    protected void setPosition(int position) {
//        angel = -position * intervalAngel;
//    }


}
