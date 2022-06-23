package com.example.circlelistview;

import android.view.View;

public abstract class Adapter {

    private ListCallBack callBack;

    public int getCount() {
        return 0;
    }

    public abstract View getView(ViewHolder holder,int position);

    public void notifyDataChanged() {
        callBack.refreshList();
    }

    public void setPosition(int position) {
        if (position > getCount() - 1) {
            position = getCount() - 1;
        } else if (position < 0) {
            position = 0;
        }
        callBack.setPosition(position);
    }

    protected void setCallBack(ListCallBack callBack) {
        this.callBack = callBack;
    }
}