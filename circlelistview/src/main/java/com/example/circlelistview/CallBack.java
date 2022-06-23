package com.example.circlelistview;

public class CallBack extends ListCallBack {

    CircleListView view;

    public CallBack(CircleListView view) {
        this.view = view;
    }

    @Override
    protected void refreshList() {
        view.removeAllViews();
        for (int i = 0; i < view.adapter.getCount(); i++) {
            if (i == 0 && view.angel < -view.intervalAngel * (view.adapter.getCount() - 1)) {
                view.angel = -view.intervalAngel * (view.adapter.getCount() - 1);
            }
            view.addView(view.adapter.getView(i));
            if (view.adapter.getCount() == 1) {
                setPosition(0);
            }
        }
        view.invalidate();
    }

    @Override
    protected void setPosition(int position) {
        view.angel = -position * view.intervalAngel;
    }
}
