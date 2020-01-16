package com.example.circlelistview;

import android.view.View;

public abstract class Adapter {

    private CircleListView circleListView;

    public Adapter(CircleListView circleListView) {
        this.circleListView = circleListView;
    }

    public int getCount() {
        return 0;
    }

    public abstract View getView(int position);

    public void notifyDataChanged() {
        circleListView.refreshList();
    }

    public void setPosition(int position) {
        if (position > getCount() - 1) {
            position = getCount() - 1;
        } else if (position < 0) {
            position = 0;
        }
        circleListView.setPosition(position);
    }
}