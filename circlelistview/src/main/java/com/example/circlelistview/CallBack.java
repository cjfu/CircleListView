package com.example.circlelistview;

import static com.example.circlelistview.CircleListView.intervalAngel;

public class CallBack extends ListCallBack {

    CircleListView view;

    public CallBack(CircleListView view) {
        this.view = view;
    }

    @Override
    protected void refreshList() {
        view.removeAllViews();
        for (int i = 0; i < view.adapter.getCount(); i++) {
            if (i == 0 && view.angel < -CircleListView.intervalAngel * (view.adapter.getCount() - 1)) {
                view.angel = -CircleListView.intervalAngel * (view.adapter.getCount() - 1);
            }
//            view.addView(view.adapter.getView(i));
            if (view.adapter.getCount() == 1) {
                setPosition(0);
            }
        }

        for (int i = Math.max(0, (int) Math.ceil(-180- view.angel / intervalAngel)); i < Math.min(view.adapter.getCount(), (int) Math.floor((540 - view.angel) / intervalAngel)); i++) {
            view.adapter.getView(view.viewHolder, i);
        }
        view.invalidate();
    }

    @Override
    protected void setPosition(int position) {
        view.angel = -position * view.intervalAngel;
    }
}
